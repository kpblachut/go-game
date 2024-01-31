package org.example.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;

public class Server {
    private static final int PORT = 12345;
    private Map<String, ClientHandler> clients = new HashMap<>();
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
                clients.put(Integer.toString(clientId), clientHandler);
                new Thread(clientHandler).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
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

    public Lobby createLobby(GameRecord gameRecord) {
        Lobby lobby = new Lobby(gameRecord);
        lobbies.put(Integer.toString(lobby.getLobbyCode()), lobby);
        return lobby;
    }

    public GameRecord loadFromString(String data) {
        return GameRecord.load(data);
    }

    public Lobby fetchLobby(String lobbyCode) {
        return lobbies.get(lobbyCode);
    }

    public ClientHandler getClient(String clientId) {
        return clients.get(clientId);
    }
}
