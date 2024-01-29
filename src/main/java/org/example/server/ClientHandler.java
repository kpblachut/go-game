package org.example.server;

import org.example.client.Response;

import java.io.*;
import java.net.Socket;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class ClientHandler implements Runnable {
    private Socket clientSocket;
    private Server server;
    //private BufferedReader inputReader;
    private ObjectInputStream inputReader;
    //private PrintWriter outputWriter;
    private ObjectOutputStream outputWriter;
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

    public void joinLobby(String lobbyName, String lobbyCode) {
        if (!lobbies.containsKey(lobbyName)) {
            sendMessage("Invalid lobby name: " + lobbyName);
            return;
        }

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
            Object clientMessage;
            try {
                while ((clientMessage = inputReader.readObject()) != null) {
                    //TODO
                    //OG sending

                    //Testing sending
                    System.out.println("Recieved packet from client");
                    System.out.println("Sending trial board");
                    Integer[][] board = new Integer[13][13];
                    for(int i = 0; i < 13; i++) {
                        for(int j = 0; j < 13; j++){
                            board[i][j]=0;
                        }
                    }
                    outputWriter.writeObject(new Response(board, 1111));
                    outputWriter.reset();

                    board[5][5] = 2;
                    board[6][6] = 1;

                    Response r = new Response();

                    r.setBoard(board);
                    r.setPlayer(1234);

                    outputWriter.writeObject(r);
                    outputWriter.reset();
                    Integer[][] board2 = new Integer[13][13];
                    board2[5][5] = 0;
                    board2[7][7] = 1;
                    r.setBoard(board2);
                    outputWriter.writeObject(r);
                    outputWriter.reset();
                }
            } catch (ClassNotFoundException e) {}
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
