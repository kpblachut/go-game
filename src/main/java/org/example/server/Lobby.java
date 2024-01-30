package org.example.server;

import java.util.*;

public class Lobby {
    private Map<String, Player> players; // Gracze
    private Set<String> sides;
    private int lobbyCode;  // Kod wejsciowy
    private GameBoard gameBoard;


    int size;

    public Lobby(int size) {
        gameBoard = new GameBoard(size, size);
        this.size = size;
        this.lobbyCode = new Random().nextInt(9000) + 1000;
        players = new HashMap<String, Player>();
        sides = new HashSet<>(Arrays.asList("1", "2"));
    }

    public void addPlayer(Player player) {
        players.put(Integer.toString(player.getId()), player);
    }

    public int getLobbyCode() {
        return lobbyCode;
    }

    public GameBoard getGameBoard() {
        return gameBoard;
    }

    public void joinLobby(ClientHandler clientHandler) {
        joinLobby(clientHandler, "0");
    }
    public void joinLobby(ClientHandler clientHandler, String side) {
        if (sides.contains(side)) {
            Player player = new Player(Integer.parseInt(side));
            sides.remove(side);
            players.put()
            clientHandler.setSide(Integer.toString(player.getId()));
        } else {
            String[] sidesArr = sides.toArray(new String[sides.size()]);
            int randomNum = new Random().nextInt(sides.size());
            Player player = new Player(Integer.parseInt(sidesArr[randomNum]));
        }
    }
}
