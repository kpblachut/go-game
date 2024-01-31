package org.example;

import java.io.Serializable;

public class Response implements Serializable {
    private Integer player;
    private Integer[][] board;
    private Integer lobbyId;
    private boolean passed;
    private int[] scores;
    private String save;

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

    public void setLobbyId(Integer lobbyId) {
        this.lobbyId = lobbyId;
    }

    public void setPassed(boolean passed) {
        this.passed = passed;
    }

    public void setScores(int[] scores) {
        this.scores = scores;
    }

    public int[] getScores() {
        return scores;
    }

    public boolean getPassed() {
        return passed;
    }
    public Integer[][] getBoard(){
        return board;
    }

    public Integer getLobbyId() {
        return lobbyId;
    }

    public Integer getPlayer() {
        return player;
    }

    public void setSave(String save) {
        this.save = save;
    }

    public String getSave() {
        return save;
    }
}
