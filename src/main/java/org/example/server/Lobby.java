package org.example.server;

import org.example.server.exceptions.OutOfGameBoardException;

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
            players.put(Integer.toString(clientHandler.getClientId()), player);
            clientHandler.setSide(Integer.toString(player.getId()));
        } else {
            String[] sidesArr = sides.toArray(new String[sides.size()]);
            int randomNum = new Random().nextInt(sides.size());
            Player player = new Player(Integer.parseInt(sidesArr[randomNum]));
            players.put(Integer.toString(clientHandler.getClientId()), player);
            clientHandler.setSide(Integer.toString(player.getId()));
        }
    }

    public void makeMove(int x, int y, int clientId) throws OutOfGameBoardException {
        Player player = getPlayerByClientId(Integer.toString(clientId));
        if (gameBoard.getPassCount() == 2) {

            return;
        }
        if (x == -1 && y == -1) {
            gameBoard.pass(player);
            return;
        }
        if (gameBoard.play(x, y, player)) {
            System.out.println("Played ["+x+"-"+y+"] by player "+player.getId());
            System.out.println("Captured stones "+player.getCapturedStones());
            gameBoard.nextPlayer();
        }
    }

    public int getPlayerSide(String playerId) {
        return players.get(playerId).getId();
    }

    public Player getPlayerByClientId(String clientId) {
        return players.get(clientId);
    }

    public Set<String> getPlayerIds() {
        return players.keySet();
    }
}
