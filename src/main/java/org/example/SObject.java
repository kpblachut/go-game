package org.example;

import java.io.Serializable;

public class SObject implements Serializable {
    public boolean yourTurn;
    public char [][] board;

    public SObject(char [][] board, boolean yourTurn){
        this.board = board;
        this.yourTurn = yourTurn;
    }
}
