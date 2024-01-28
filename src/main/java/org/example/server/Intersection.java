package org.example.server;

import java.util.HashSet;
import java.util.Set;

public class Intersection {

    /**
     * Game board that contains Intersection
      */
    private final GameBoard gameBoard;

    /**
     * Coordinates of intersection
     */
    private final int x, y;

    /**
     * Chain that intersection is a part of (if applicable)
     */
    private Chain chain;

    public Intersection(GameBoard gameBoard, int x, int y) {
        this.gameBoard = gameBoard;
        this.x = x;
        this.y = y;
        this.chain = null;
    }

    public int getY() {
        return y;
    }

    public int getX() {
        return x;
    }

    public Chain getChain() {
        return chain;
    }

    public void setChain(Chain chain) {
        this.chain = chain;
    }

    public boolean isEmpty() {
        return chain == null;
    }

    public Set<Chain> getNeighbours() {
        Set<Chain> neighbours = new HashSet<Chain>();

        int[] dx = {-1, 0, 1, 0}, dy = {0, -1, 0, 1};
        for (int i = 0; i < dx.length; i++) {
            int newX = x + dx[i];
            int newY = y + dy[i];

            if (gameBoard.isInBoard(newX, newY)) {
                Intersection neighbour = gameBoard.getIntersection(newX, newY);
                if (neighbour.chain != null) {
                    neighbours.add(neighbour.chain);
                }
            }
        }
        return  neighbours;
    }

    public Set<Intersection> getEmptyNeighbors() {
        Set<Intersection> neighbours = new HashSet<Intersection>();

        int[] dx = {-1, 0, 1, 0}, dy = {0, -1, 0, 1};
        for (int i = 0; i < dx.length; i++) {
            int newX = x + dx[i];
            int newY = y + dy[i];

            if (gameBoard.isInBoard(newX, newY)) {
                Intersection neighbour = gameBoard.getIntersection(newX, newY);
                if (neighbour.isEmpty()) {
                    neighbours.add(neighbour);
                }
            }
        }
        return  neighbours;
    }

    public Set<Intersection> getEmptyOrDeadNeighbours(Set<Intersection> deadChains) {
        Set<Intersection> neighbours = new HashSet<Intersection>();

        int[] dx = {-1, 0, 1, 0}, dy = {0, -1, 0, 1};
        for (int i = 0; i < dx.length; i++) {
            int newX = x + dx[i];
            int newY = y + dy[i];

            if (gameBoard.isInBoard(newX, newY)) {
                Intersection neighbour = gameBoard.getIntersection(newX, newY);
                if (neighbour.isEmpty() || deadChains.contains(neighbour.getChain())) {
                    neighbours.add(neighbour);
                }
            }
        }
        return  neighbours;
    }
}
