package org.example.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Server {
    private static final int PORT = 12345;
    private List<ClientHandler> clients = new ArrayList<>();
    private Map<String, List<ClientHandler>> lobbies = new HashMap<>();

    public static void main(String[] args) {
        new Server().startServer();
    }

    public void startServer() {
        try {
            ServerSocket serverSocket = new ServerSocket(PORT);
            System.out.println("Server is running on port " + PORT);

            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("New client connected: " + clientSocket);

                ClientHandler clientHandler = new ClientHandler(clientSocket, this, lobbies);
                clients.add(clientHandler);
                new Thread(clientHandler).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void broadcastMessage(String message, ClientHandler sender) {
        for (ClientHandler client : clients) {
            if (client != sender) {
                client.sendMessage(message);
            }
        }
    }

    public void broadcastMessageInLobby(String message, String lobbyName, ClientHandler sender) {
        if (lobbies.containsKey(lobbyName)) {
            List<ClientHandler> lobbyClients = lobbies.get(lobbyName);
            for (ClientHandler client : lobbyClients) {
                if (client != sender) {
                    client.sendMessage(message);
                }
            }
        }
    }

    public void removeClient(ClientHandler clientHandler) {
        clients.remove(clientHandler);
        System.out.println("Client disconnected: " + clientHandler.getClientName());
    }

    public void removeClientFromLobby(ClientHandler clientHandler, String lobbyName) {
        if (lobbies.containsKey(lobbyName)) {
            List<ClientHandler> lobbyClients = lobbies.get(lobbyName);
            lobbyClients.remove(clientHandler);
            // Remove the lobby if there are no clients left
            if (lobbyClients.isEmpty()) {
                lobbies.remove(lobbyName);
            }
        }
    }

    public String generateLobbyCode() {
        // Generate a simple lobby code for demonstration purposes
        return String.valueOf((Math.random() * 10000));
    }
}
