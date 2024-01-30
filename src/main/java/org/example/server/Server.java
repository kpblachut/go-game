package org.example.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;

public class Server {
    private static final int PORT = 12345;
    private List<ClientHandler> clients = new ArrayList<>();
    private Map<String, Lobby> lobbies = new HashMap<>();


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
                int clientId = new Random().nextInt(9000) + 1000;

                ClientHandler clientHandler = new ClientHandler(clientSocket, this, clientId);
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


    public void removeClient(ClientHandler clientHandler) {
        clients.remove(clientHandler);
        System.out.println("Client disconnected: " + clientHandler.getClientName());
    }

    public Lobby createLobby(int boardSize) {
        Lobby lobby = new Lobby(boardSize);
        lobbies.put(Integer.toString(lobby.getLobbyCode()), lobby);
        return lobby;
    }

    public Lobby fetchLobby(String lobbyCode) {
        return lobbies.get(lobbyCode);
    }



}
