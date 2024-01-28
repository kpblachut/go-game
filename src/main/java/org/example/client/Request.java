package org.example.client;

import java.io.Serializable;

public class Request implements Serializable {
    Integer size;
    Integer x;
    Integer y;
    Integer lobbyId;
    String playerId;
    Integer gameMode;
    Integer randomColor;

    public Request(int x, int y, int lobbyId, String playerId) { // Sending move request;
        this.x = x;
        this.y = y;
        this.lobbyId = lobbyId;
        this.playerId = playerId;
    }

    public Request(int size, String playerId, int gameMode, int randomColor) {
        this.size = size;
        this.playerId = playerId;
        this.gameMode = gameMode;
        this.randomColor = randomColor;
    }

    public Request(int lobbyId, String playerId) {
        this.lobbyId = lobbyId;
        this.playerId = playerId;
    }
}
