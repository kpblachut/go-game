package org.example.server;

import org.example.client.Client;

/*
* Robie ta klase bo nie wiem jak dziala juz istniejace lobby
* */
public class Lobby {
    ClientHandler whitePlayer, blackPlayer; // Gracze
    private String lobbyName;
    private String lobbyCode;  // Kod wejsciowy

    private boolean whiteTurn;

    int[][] board;  // Tu może Stone[][]? Wtedy można by dodać pole name do każdego kamienia, i nazywać je
                    // po kolei nastepnymi liczbami, w ten sposób można by było zrobić sposób zapisywania
                    // kolejnosci ruchow

    public Lobby(String name, String code, int size) {
        this.board = new int[size][size];
        this.lobbyName = name;
        this.lobbyCode = code;
        this.whiteTurn = false;
    }

    public void setBlackPlayer(ClientHandler blackPlayer) {
        this.blackPlayer = blackPlayer;
    }

    public void setWhitePlayer(ClientHandler whitePlayer) {
        this.whitePlayer = whitePlayer;
    }

    public boolean isBlackPlayerSet() {
        return blackPlayer != null;
    }

    public boolean isWhitePlayerSet() {
        return whitePlayer != null;
    }
    //Cos tu trzeba bedzie jeszcze wcisnac, zmiany tur itd...
    public void changeTurn() {
        whiteTurn = !whiteTurn;
        if(whiteTurn) {
            // wyslanie do bialego ze jego tura
            whitePlayer.sendMessage("");
            // wyslanie do czarnego ze niejego tura
            blackPlayer.sendMessage("");
        } else {
            // wyslanie do czarnego ze jego tura
            blackPlayer.sendMessage("");
            // wyslanie do bialego ze niejego tura
            whitePlayer.sendMessage("");
        }
    }

    public void sendUpdates() {
        // wyslanie do obu klientów informacji gdzie jest nowy pionek, u klienta powinien byc
        // wykonywany refresh tuz po otrzymaniu tej informacji
        whitePlayer.sendMessage("");
        blackPlayer.sendMessage("");
    }

    public boolean isMoveLegal(/*tu moze ruch?*/) {
        boolean move;
        // Sprawdzanie ruchu
        return false;   // Do zmiany
    }

    // Trzeba dodać, jeszcze jakieś rzeczy, ale na razie nie wyobrażam sobie jakie
}
