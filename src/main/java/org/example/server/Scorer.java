package org.example.server;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class Scorer {
    private Set<Chain> aliveStones;
    private Set<Chain> deadStones;
    private Set<Island> islands;
    private GameBoard gameBoard;

    public Scorer(GameBoard gameBoard) {
        this.gameBoard = gameBoard;
    }

    public void init() {
        this.deadStones = new HashSet<Chain>();
        this.aliveStones = new HashSet<Chain>();
        for (int i = 0; i < gameBoard.getWidth(); i++) {
            for (int j = 0; j < gameBoard.getHeight(); j++) {
                this.aliveStones.add(gameBoard.getIntersection(i,j).getChain());
            }
        }
        this.aliveStones.remove(null);
        this.islands = Island.islandsBuilder(gameBoard, Collections.<Chain>emptySet());
    }

    public int[] outputScore() {
        int[] score = new int[2];
        for (Island island : islands) {
            if(island.getOwner()==null) continue;
            score[island.getOwner().getId()-1] += island.getIntersections().size();
        }
        for (Chain chain : deadStones) {
            score[chain.getOwner().getId()-1]--;
        }
        score[0] -= gameBoard.getPlayer(1).getCapturedStones();
        score[1] -= gameBoard.getPlayer(2).getCapturedStones();
        return score;
    }

}
