package org.example.server;

import java.io.*;
import java.net.Socket;
import java.util.Arrays;
import org.example.Request;
import org.example.Response;

public class ClientHandler implements Runnable {
    private Socket clientSocket;
    private Server server;
    private ObjectInputStream inputReader;
    private ObjectOutputStream outputWriter;
    private Lobby lobby;
    private String clientName;
    private String currentLobby;
    private String lobbyCode;
    Lobby MyLobby;

    public ClientHandler(Socket clientSocket, Server server) {
        this.clientSocket = clientSocket;
        this.server = server;
        try {
            inputReader = new ObjectInputStream(clientSocket.getInputStream());
            outputWriter = new ObjectOutputStream(clientSocket.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getClientName() {
        return clientName;
    }

    public void sendMessage(Object message) {
        try {
            outputWriter.writeObject(message);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    @Override
    public void run() {
        try {
            Request clientMessage;
            try {
                while ((clientMessage = (Request) inputReader.readObject()) != null) {
                    if(clientMessage.getSize() != null) {
                        // Create new game
                        lobby = server.createLobby(clientMessage.getSize());
                        lobby.addPlayer(this);
                        Response response = new Response();
                        response.setBoard(lobby.getGameBoard().getGameRecord().getLastTurn().getBoardState());
                        response.setPlayer(123);
                        response.setLobbyId(lobby.getLobbyCode());
                        System.out.println(Arrays.deepToString(response.getBoard()));

                        outputWriter.writeObject(response);
                        outputWriter.reset();
                    }
                }
            } catch (ClassNotFoundException e) {}
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            // Remove the client from the lobby and the server when the connection is closed
            if (currentLobby != null) {
                server.broadcastMessage(clientName + " has left lobby: " + currentLobby, this);
            }
            server.removeClient(this);
            server.broadcastMessage(clientName + " has left the chat.", this);
        }
    }

}
