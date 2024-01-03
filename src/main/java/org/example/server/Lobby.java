package org.example.server;

import org.example.client.Client;

/*
* Robie ta klase bo nie wiem jak dziala juz istniejace lobby
* */
public class Lobby {
    ClientHandler whitePlayer, blackPlayer; // Gracze
    private String lobbyName;
    private String lobbyCode;  // Kod wejsciowy

    int[][] board;

    public Lobby(String name, String code, int size) {
        this.board = new int[size][size];
        this.lobbyName = name;
        this.lobbyCode = code;
    }

    public void setBlackPlayer(ClientHandler blackPlayer) {
        this.blackPlayer = blackPlayer;
    }

    public void setWhitePlayer(ClientHandler whitePlayer) {
        this.whitePlayer = whitePlayer;
    }
    //Cos tu trzeba bedzie jeszcze wcisnac, zmiany tur itd...
}
