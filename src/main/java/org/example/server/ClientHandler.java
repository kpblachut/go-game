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



    @Override
    public void run() {
        try {
            Object clientMessage;
            try {
                while ((clientMessage = inputReader.readObject()) != null) {

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

}
