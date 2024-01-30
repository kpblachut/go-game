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

    public boolean isEmpty(){
        boolean empty = true;
        for(int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                if(board[i][j] == 2 || board[i][j] == 1) {
                    empty = false;
                    break;
                }
                if(!empty){break;}
            }
        }
        return empty;
    }
}
