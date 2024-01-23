package org.example.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.List;
import java.util.Map;

public class ClientHandler implements Runnable {
    private Socket clientSocket;
    private Server server;
    private BufferedReader inputReader;
    private PrintWriter outputWriter;
    private String clientName;
    private String currentLobby;
    private String lobbyCode;
    private Map<String, Lobby> lobbies;
    Lobby MyLobby;

    public ClientHandler(Socket clientSocket, Server server, Map<String, Lobby> lobbies) {
        this.clientSocket = clientSocket;
        this.server = server;
        this.lobbies = lobbies;
        try {
            inputReader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            outputWriter = new PrintWriter(clientSocket.getOutputStream(), true);

            // Set up client's nickname
            this.clientName = inputReader.readLine();
            System.out.println("New client connected: " + clientName);

            // Notify all clients about the new connection
            server.broadcastMessage(clientName + " has joined the chat.", this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getClientName() {
        return clientName;
    }

    public void sendMessage(String message) {
        outputWriter.println(message);
    }

    public void joinLobby(String lobbyName, String lobbyCode) {
        if (!lobbies.containsKey(lobbyName)) {
            sendMessage("Invalid lobby name: " + lobbyName);
            return;
        }

        //List<ClientHandler> lobbyClients = lobbies.get(lobbyName);
        Lobby lobby = lobbies.get(lobbyName);

        if (lobbyCode.equals(lobbyCode) && !lobby.isFull()) {
            sendMessage("Successfully joined lobby: " + lobbyName);
            // Notify others about the new member
            server.broadcastMessageInLobby(clientName + " has joined the lobby.", lobbyName, this);

            lobby.add(this);
            setLobby(lobby);

        } else if(lobby.isFull()) {
            sendMessage("Lobby Full!");
        } else {
            sendMessage("Invalid lobby code for lobby: " + lobbyName);
        }
    }

    @Override
    public void run() {
        try {
            String clientMessage;
            while ((clientMessage = inputReader.readLine()) != null) {
                if(MyLobby == null) {
                    if (clientMessage.split(" ")[0].equalsIgnoreCase("CREATE_LOBBY")) { //Sprawdzanie
                        // Lobby Creation
                        String lobbyCode = server.generateLobbyCode();
                        sendMessage("Your lobby code is: " + lobbyCode);

                        server.createLobby(clientName, lobbyCode, Integer.parseInt(clientMessage.split(" ")[1]));

                        server.broadcastMessage(clientName + " has created a lobby. Lobby code: " + lobbyCode, this);

                        joinLobby(clientName, lobbyCode);

                    } else if (clientMessage.startsWith("JOIN_LOBBY ")) {
                        // Handle joining a lobby
                        String[] parts = clientMessage.split(" ");
                        if (parts.length == 3) {
                            String lobbyToJoin = parts[1];
                            String lobbyCode = parts[2];
                            joinLobby(lobbyToJoin, lobbyCode);
                        } else {
                            sendMessage("Invalid command format. Use JOIN_LOBBY lobbyName lobbyCode");
                        }
                    } else {
                        // Broadcast the message to all clients in the same lobby
                        server.broadcastMessageInLobby(clientName + ": " + clientMessage, currentLobby, this);
                    }
                } else {
                    MyLobby.handleMessage(clientMessage);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            // Remove the client from the lobby and the server when the connection is closed
            if (currentLobby != null) {
                server.removeClientFromLobby(this, currentLobby);
                server.broadcastMessage(clientName + " has left lobby: " + currentLobby, this);
            }
            server.removeClient(this);
            server.broadcastMessage(clientName + " has left the chat.", this);
        }
    }

    public void setLobby(Lobby lobby){
        MyLobby = lobby;
    }
}
