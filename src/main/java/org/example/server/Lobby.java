package org.example.server;

import org.example.server.exceptions.OutOfGameBoardException;

import java.util.*;

public class Lobby {
    private Map<String, Player> players; // Gracze
    private Set<String> sides;
    private int lobbyCode;  // Kod wejsciowy
    private GameBoard gameBoard;
    private int[] scores;


    int size;

    public Lobby(int size) {
        gameBoard = new GameBoard(size, size);
        this.size = size;
        this.lobbyCode = new Random().nextInt(9000) + 1000;
        players = new HashMap<String, Player>();
        sides = new HashSet<>(Arrays.asList("1", "2"));
    }

    public Lobby(GameRecord gameRecord) {
        gameBoard = new GameBoard(gameRecord);
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

    public void makeMove(int x, int y, ClientHandler clientHandler) throws OutOfGameBoardException {
        Player player = getPlayerByClientId(Integer.toString(clientHandler.getClientId()));
        if (gameBoard.getPassCount() == 2) {
            clientHandler.setSendScore(true);
            Scorer scorer = new Scorer(gameBoard);
            scorer.init();
            scores = scorer.outputScore();
            return;
        }
        if (x == -1 && y == -1) {
            gameBoard.pass(player);
            clientHandler.setPassed(true);
            return;
        }
        if (x == -2 && y == -2) {
            gameBoard.pass(player);
            clientHandler.setPassed(true);
            return;
        }
        if (x == -3 && y == -3) {
            String save = gameBoard.getGameRecord().save();
            clientHandler.setSave(save);
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

    public int[] getScores() {
        return scores;
    }
}
