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

    int[][] board;
    // W artykule z Byte, Jonathan K. Millen
    // Zrobił coś takiego, że plansza ma o 2 pola w każdą stronę wiecej
    // i krawędzie to 7
    // 1 - czarny kamien, 2 - biały kamien
    // 4 - zaznaczenie grupy, 8 - zaznaczenie oddechow
    // w przypadku grupy, po prostu sumowanie 4 z numerem koloru

    public Lobby(String name, String code, int size) {
        this.board = new int[size+2][size+2]; // dla krawedzi
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
            whitePlayer.sendMessage("Y_TURN");
            // wyslanie do czarnego ze niejego tura
            blackPlayer.sendMessage("NY_TURN");
        } else {
            // wyslanie do czarnego ze jego tura
            blackPlayer.sendMessage("Y_TURN");
            // wyslanie do bialego ze niejego tura
            whitePlayer.sendMessage("NY_TURN");
        }
    }

    public void sendUpdates() {
        // wyslanie do obu klientów informacji gdzie jest nowy pionek, u klienta powinien byc
        // wykonywany refresh tuz po otrzymaniu tej informacji
        whitePlayer.sendMessage("");
        blackPlayer.sendMessage("");
    }

    public boolean isMoveLegal(/*tu moze ruch?*/) {
        boolean move = false;
        // Sprawdzanie ruchu
        return move;   // Do zmiany
    }

    // Trzeba dodać, jeszcze jakieś rzeczy, ale na razie nie wyobrażam sobie jakie
}
