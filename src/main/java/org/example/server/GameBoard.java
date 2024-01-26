package org.example.server;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class GameBoard {
    private final int width, height;
    private final Intersection[][] intersections;
    private final GameRecord gameRecord;
    private Set<Intersection> lastCaptured;
    private Player P1, P2, currentPlayer;
    private int passCount;

    public GameBoard(int width, int height) {
        this.width = width;
        this.height = height;
        this.passCount = 0;
        this.intersections = new Intersection[width][height];
        this.gameRecord = new GameRecord(width, height);

        initGameBoard();
    }

    public GameBoard(GameRecord gameRecord) {
        this.gameRecord = gameRecord;
        this.width = gameRecord.getLastTurn().getBoardState().length;
        this.height = gameRecord.getLastTurn().getBoardState()[0].length;

        intersections = new Intersection[width][height];
        initGameBoard();

        try {
            takeGameTurn(this.gameRecord.getLastTurn(), P1, P2);
        } catch (Exception e) {
            System.out.println("Exception in GameBoard: takeGameTurn: " + e);
        }
    }

    private void initGameBoard() {
        lastCaptured = new HashSet<Intersection>();

        P1 = new Player(1);
        P2 = new Player(2);
        currentPlayer = P1;

        for (int x = 0; x < this.width; x++) {
            for (int y = 0; y < this.height; y++) {
                intersections[x][y] = new Intersection(this, x, y);
            }
        }
    }

    public void takeGameTurn(GameTurn gameTurn, Player P1, Player P2) {

    }

    public int getPassCount() {
        return passCount;
    }

    public GameRecord getGameRecord() {
        return gameRecord;
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }

    public Intersection[][] getIntersections() {
        return intersections;
    }

    public void updatePassCount(boolean pass) {
        if (pass) {
            passCount ++;
        } else {
            passCount = 0;
        }
    }

    public void pass(Player player) {
        gameRecord.apply(gameRecord.getLastTurn().toNext(-1,-1, player.getId(),Collections.<Intersection>emptySet()));
        nextPlayer();
        updatePassCount(true);
    }

    public boolean play(Intersection intersection, Player player)

    public void nextPlayer() {

    }

}

