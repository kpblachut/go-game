package org.example.client;

import java.io.Serializable;

public class Response implements Serializable {
    public String player;
    public int[][] board;
    public int lobbyId;
}
