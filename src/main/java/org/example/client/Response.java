package org.example.client;

import java.io.Serializable;

public class Response implements Serializable {
    public String player;
    private String[][] board;
    public Integer lobbyId;

    public Response(String[][] board, Integer lobbyId) {
        this.board = board;
        this.lobbyId = lobbyId;
    }
    public Response(){}

    public void setPlayer(String player) {
        this.player = player;
    }

    public void setBoard(String[][] board) {
        this.board = board;
    }

    public String[][] getBoard(){
        return board;
    }
}
