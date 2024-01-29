package org.example.client;

import java.io.Serializable;

public class Response implements Serializable {
    public Integer player;
    private Integer[][] board;
    public Integer lobbyId;

    public Response(Integer[][] board, Integer lobbyId) {
        this.board = board;
        this.lobbyId = lobbyId;
    }
    public Response(){}

    public void setPlayer(Integer player) {
        this.player = player;
    }

    public void setBoard(Integer[][] board) {
        this.board = board;
    }

    public Integer[][] getBoard(){
        return board;
    }
}
