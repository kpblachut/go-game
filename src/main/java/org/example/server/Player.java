package org.example.server;

import java.util.Collections;

public class Player {
    private final int id;
    private int capturedStones;

    public Player(int id) {
        this.id = id;
        this.capturedStones = 0;
    }

    public int getId() {
        return id;
    }

    public int getCapturedStones() {
        return capturedStones;
    }

    public void addCapturedStones(int n) {
        capturedStones += n;
    }

    public void removeCapturedStones(int n) {
        capturedStones -= n;
    }

    public boolean play(GameBoard gameBoard, int x, int y) {
        if (x == -1 && y == -1) {
            GameRecord gameRecord = gameBoard.getGameRecord();
            gameRecord.apply(gameRecord.getLastTurn().toNext(-1, -1, this.getId(), Collections.<Intersection>emptySet()));
            gameBoard.updatePassCount(true);

            return true;
        } else {
            return gameBoard.play(gameBoard.getIntersection(x,y),this);
        }
    }
}
