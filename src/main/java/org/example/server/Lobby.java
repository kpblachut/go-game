package org.example.server;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class Lobby {
    private Set<ClientHandler> players; // Gracze
    private int lobbyCode;  // Kod wejsciowy
    private GameBoard gameBoard;


    int size;

    public Lobby(int size) {
        gameBoard = new GameBoard(size, size);
        this.size = size;
        this.lobbyCode = new Random().nextInt(9000) + 1000;
        players = new HashSet<ClientHandler>();
    }

    public void addPlayer(ClientHandler clientHandler) {
        players.add(clientHandler);
    }

    public int getLobbyCode() {
        return lobbyCode;
    }

    public GameBoard getGameBoard() {
        return gameBoard;
    }
}
