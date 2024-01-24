package org.example.server;

import org.example.client.Client;
import java.util.Random;

/*
* Robie ta klase bo nie wiem jak dziala juz istniejace lobby
* */
public class Lobby {
    ClientHandler whitePlayer, blackPlayer; // Gracze
    private String lobbyName;
    private String lobbyCode;  // Kod wejsciowy

    private boolean whiteTurn;

    int size;
    int[][] board;
    // W artykule z Byte, Jonathan K. Millen
    // Zrobił coś takiego, że plansza ma o 2 pola w każdą stronę wiecej
    // i krawędzie to 7
    // 1 - czarny kamien, 2 - biały kamien
    // 4 - zaznaczenie grupy, 8 - zaznaczenie oddechow
    // w przypadku grupy, po prostu sumowanie 4 z numerem koloru

    public Lobby(String name, String code, int size) {
        this.size = size;
        this.board = new int[size+2][size+2]; // dla krawedzi
        this.lobbyName = name;
        this.lobbyCode = code;
        this.whiteTurn = false;
    }

    /**
     *  Funkcja do dodawania nowego gracza
     * losuje kolor, którym bedzie grać gracz
     * i wysyła mu kod do inicjalizacji
     * */
    public void add(ClientHandler player) {

        if(!isBlackPlayerSet() && !isWhitePlayerSet()) {
            int random = (new Random()).nextInt(2);

            if(random == 0){
                setBlackPlayer(player);
            } else {
                setWhitePlayer(player);
            }

        } else if (!isBlackPlayerSet() || !isWhitePlayerSet()) {
            if(isBlackPlayerSet()) {
                setWhitePlayer(player);
            } else {
                setBlackPlayer(player);
            }
            startGame();
        } else {
            player.sendMessage("LOBBY_FULL");
        }
    }

    /**
     * Funkcje do ustawiania graczy na konkretnym miejscu
     */
    public void setBlackPlayer(ClientHandler blackPlayer) {
        this.blackPlayer = blackPlayer;
        blackPlayer.sendMessage("INIT B " + size);
    }

    public void setWhitePlayer(ClientHandler whitePlayer) {
        this.whitePlayer = whitePlayer;
        whitePlayer.sendMessage("INIT W " + size);
    }

    /*
    Funkcje sprawdzajace dostepnosc miejsc
     */
    public boolean isBlackPlayerSet() {
        return blackPlayer != null;
    }

    public boolean isWhitePlayerSet() {
        return whitePlayer != null;
    }

    public boolean isFull(){
        boolean ret;
        if(isWhitePlayerSet() && isBlackPlayerSet()) {
            ret = true;
        } else {
            ret = false;
        }
        return ret;
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

    private void setPlayerTurn(ClientHandler player, boolean isTurn){
        String message = (isTurn) ? "Y_TURN" : "NY_TURN";
        player.sendMessage(message);
    }

    public void sendUpdates(String move) {
        // wyslanie do obu klientów informacji gdzie jest nowy pionek, u klienta powinien byc
        // wykonywany refresh tuz po otrzymaniu tej informacji
        whitePlayer.sendMessage(move);
        blackPlayer.sendMessage(move);
    }

    public boolean isMoveLegal(/*tu moze ruch?*/) {
        boolean move = true;
        // Sprawdzanie ruchu
        return move;   // Do zmiany
    }

    private void startGame(){
        setPlayerTurn(blackPlayer, true);
    }

    public void handleMessage(String message){

        String[] SplitCommand = message.split(" ");
        if(SplitCommand[0].equals("BLACK") || SplitCommand[0].equals("WHITE")){
            if(isMoveLegal(/*Tutaj ruch, pewnie SplitCommand mozna wcisnac*/)){
                int x = Integer.parseInt(SplitCommand[1]);
                int y = Integer.parseInt(SplitCommand[2]);
                int color = (SplitCommand[0] == "BLACK") ? 1 : 2;
                board[x+1][y+1] = color;
                sendUpdates(SplitCommand[0].charAt(0) + " " +x+" "+y);
                changeTurn();
            } else {
                if(SplitCommand[0].equals("BLACK")){
                    setPlayerTurn(blackPlayer, true);
                    setPlayerTurn(whitePlayer, false);
                } else {
                    setPlayerTurn(blackPlayer, false);
                    setPlayerTurn(whitePlayer, true);
                }
            }
        }
    }

    // Trzeba dodać, jeszcze jakieś rzeczy, ale na razie nie wyobrażam sobie jakie
}
