package org.example;

import java.io.Serializable;

public class Request implements Serializable {
    private Integer size;
    private Integer x;
    private Integer y;
    private Integer lobbyId;
    private Integer playerId;
    private Integer gameMode;
    private Integer randomColor;
    private String save;

    public Request() { // Sending move request;
        //this.x = x;
        ///this.y = y;
        //this.lobbyId = lobbyId;
        //this.playerId = playerId;
    }
    /*
    public Request(int size, String playerId, int gameMode, int randomColor) {
        //this.size = size;
        //this.playerId = playerId;
        //this.gameMode = gameMode;
        //this.randomColor = randomColor;
    }

    public Request(int lobbyId, String playerId) {
        //this.lobbyId = lobbyId;
        //this.playerId = playerId;
    }
     */

    public void setGameMode(Integer gameMode) {
        this.gameMode = gameMode;
    }

    public void setLobbyId(Integer lobbyId) {
        this.lobbyId = lobbyId;
    }

    public void setPlayerId(Integer playerId) {
        this.playerId = playerId;
    }

    public void setRandomColor(Integer randomColor) {
        this.randomColor = randomColor;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public void setX(Integer x) {
        this.x = x;
    }

    public void setY(Integer y) {
        this.y = y;
    }

    public void setSave(String save) {
        this.save = save;
    }

    public Integer getRandomColor() {
        return randomColor;
    }

    public Integer getY() {
        return y;
    }

    public Integer getPlayerId() {
        return playerId;
    }

    public Integer getLobbyId() {
        return lobbyId;
    }

    public Integer getGameMode() {
        return gameMode;
    }

    public Integer getX() {
        return x;
    }

    public Integer getSize() {
        return size;
    }

    public String getSave() {
        return save;
    }
}
