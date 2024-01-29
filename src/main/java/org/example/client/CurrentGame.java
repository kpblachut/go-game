package org.example.client;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import org.example.NewController;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;


import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Objects;

public class CurrentGame {

    Integer myName, opName; //1234 - test
    Integer whitePlayer, blackPlayer; //Moim zdaniem niekonieczne, możemy oznaczać white i black jako 1 i 2 w tablicy
    int lobbyId;
    File lfile;

    private boolean myTurn;
    private boolean sng; // Starting new Game
    private boolean log; // Loading old Game
    Client client;
    NewController controller;

    GameBoard goban;

    public CurrentGame(Client client, NewController controller){
        this.client = client;
        this.controller = controller;
        controller.getNewGameItem().setOnAction(this::showNGPopUp);
        controller.getJoinGameItem().setOnAction(this::showJGPopUp);
        controller.getPlayWBItem().setOnAction(this::showPWBPopUp);
        controller.getPassTurnItem().setOnAction(this::passTurn);
        controller.getGiveUpItem().setOnAction(this::giveUp);
        controller.getSaveGameItem().setOnAction(this::saveGame);
        controller.getQuitItem().setOnAction(this::quit);
        controller.getLoadGameItem().setOnAction(this::ShowLGPopUp);
        controller.getRedoItem().setOnAction(this::redo);
        controller.getRedoItem().setOnAction(this::undo);
        goban = client.getGoban();
        sng = true;
        log = false;
        lfile = null;
    }

    public void handleInput(Response input) {
        /*TODO

         */
        Response ip = (Response) input;
        if(!Objects.equals(myName,ip.player)){
            myTurn = false;
            opName = ip.player;
        } else {myTurn = true;}
        if(sng && (Objects.equals(myName,ip.player))){
            whitePlayer = myName;
        } else if(sng) {
            blackPlayer = myName;
        }

        if(!sng && ip.getBoard().length == goban.size) {
            goban.updateBoard(ip.getBoard());
        } else if (!sng && ip.getBoard().length != goban.size) {
            System.out.println("This shouldn't happen :(");
        } else if (sng) {
            newBoard(ip.getBoard().length);
            goban.updateBoard(ip.getBoard());
            lobbyId = ip.lobbyId;
            client.setLobbyId(lobbyId);
        } else {
            System.out.println("This totally shouldn't happen >:(");
        }
    }

    private void sendOutput(Object request){
        client.send(request);
    }

    private void showNGPopUp(ActionEvent event) {
        try {
            Stage popek = new Stage();
            FXMLLoader loder = new FXMLLoader(getClass().getResource("/NGPopup.fxml"));
            AnchorPane popupLayout = loder.load();
            NGPController popControl = loder.getController();
            Scene dialogScene = new Scene(popupLayout);
            popek.setTitle("Start new game");
            popek.setScene(dialogScene);
            popControl.getCGButton().setOnAction(e -> NewGame(e,popControl.getSizeChoice().getValue(),popControl.getColorCBox().getValue(), popek));
            /*TODO
            Tutaj dodac obsluge wysylania nowej gry z popupa
            + jeszcze zeby popup sie zamykał kiedy sie juz wlaczy nowa gre
            */
            Platform.runLater(popek::show);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void showJGPopUp(ActionEvent event) {
        try {
            Stage popek = new Stage();
            FXMLLoader loder = new FXMLLoader(getClass().getResource("/JGPopup.fxml"));
            AnchorPane popupLayout = loder.load();
            JGPController popControl = loder.getController();
            Scene dialogScene = new Scene(popupLayout);
            popek.setTitle("Join game");
            popek.setScene(dialogScene);

            popControl.getJoinButton().setOnAction(e -> JoinGame(e,popControl.getCodeTF().getText(), popek));
            /*TODO
            Tutaj dodac obsluge dolaczania gry z popupa
            + jeszcze zeby popup sie zamykał kiedy sie juz wlaczy nowa gre
            */
            Platform.runLater(popek::show);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void showPWBPopUp(ActionEvent event) {
        try {
            Stage popek = new Stage();
            FXMLLoader loder = new FXMLLoader(getClass().getResource("/NGPopup.fxml"));
            AnchorPane popupLayout = loder.load();
            NGPController popControl = loder.getController();
            Scene dialogScene = new Scene(popupLayout);
            popek.setTitle("Play with bot");
            popek.setScene(dialogScene);
            popControl.getCGButton().setOnAction(e -> NewGameWB(e,popControl.getSizeChoice().getValue(), popControl.getColorCBox().getValue(), popek));
            /*TODO
            Tutaj dodac obsluge wysylania nowej gry z popupa
            + jeszcze zeby popup sie zamykał kiedy sie juz wlaczy nowa gre
            */
            Platform.runLater(popek::show);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void ShowLGPopUp(ActionEvent event) {
        try {
            Stage popek = new Stage();
            FXMLLoader loder = new FXMLLoader(getClass().getResource("/LGPopup.fxml"));
            AnchorPane popupLayout = loder.load();
            LGPController popControl = loder.getController();
            Scene dialogScene = new Scene(popupLayout);
            popek.setTitle("Play with bot");
            popek.setScene(dialogScene);

            popControl.getLoadButton().setOnAction(e -> LoadGame(e, popek, popControl));

            Platform.runLater(popek::show);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void passTurn(ActionEvent event) {
        System.out.println("Passing my turn...");
        Request pt = new Request();
        pt.setX(-1);
        pt.setY(-1);
        pt.setPlayerId(myName);
        pt.setLobbyId(lobbyId);
        sendOutput(pt);
        /*TODO

         */
    }

    private void giveUp(ActionEvent event) {
        System.out.println("Giving up...");
        Request pt = new Request();
        pt.setX(-1);
        pt.setY(-1);
        pt.setPlayerId(myName);
        pt.setLobbyId(lobbyId);
        sendOutput(pt);
        /*TODO

         */
    }

    private void saveGame(ActionEvent event) {
        System.out.println("Saving game...");
        Request pt = new Request();
        pt.setX(-1);
        pt.setY(-1);
        pt.setPlayerId(myName);
        pt.setLobbyId(lobbyId);
        sendOutput(pt);
        /*TODO

         */
    }

    private void quit(ActionEvent event) {
        System.out.println("Quitting game...");
        Request pt = new Request();
        pt.setX(-1);
        pt.setY(-1);
        pt.setPlayerId(myName);
        pt.setLobbyId(lobbyId);
        sendOutput(pt);
        client.quit();
        /*TODO

         */
    }

    private void redo(ActionEvent event){
        //TODO
    }
    private void undo(ActionEvent event){
        //TODO
    }

    private void NewGame(ActionEvent e, int size, String color, Stage popek) {
        sng = true;
        System.out.println("Starting new game, size: " + size+" color: " + color);
        //new Request(size, myName,0, 0)
        Request op = new Request();
        op.setSize(size);
        op.setPlayerId(myName);
        op.setGameMode(0);
        int k;
        if(color.equals("BLK")){k=2;}else if(color.equals("WHT")){k=1;}else{k=0;}
        op.setRandomColor(k);
        sendOutput(op);
        Platform.runLater(popek::close);
        /*TODO

         */
    }

    private void JoinGame(ActionEvent e, String code, Stage popek) {
        sng = true;
        System.out.println("Joining game with code: " + code);
        Request op = new Request();
        op.setLobbyId(Integer.parseInt(code));
        op.setPlayerId(myName);
        sendOutput(op);
        Platform.runLater(popek::close);
        /*TODO

         */
    }

    private void NewGameWB(ActionEvent e, int size,String color, Stage popek){
        sng = true;
        System.out.println("Starting new game with bot, size: " + size);
        Request op = new Request();
        op.setSize(size);
        op.setPlayerId(myName);
        op.setGameMode(1);
        int k;
        if(color.equals("BLK")){k=2;}else if(color.equals("WHT")){k=1;}else{k=0;}
        op.setRandomColor(k);
        sendOutput(op);
        Platform.runLater(popek::close);
        /*TODO

         */
    }

    private void LoadGame(ActionEvent event,/* cos jeszcze, pewnie plik ze stackiem*/ Stage popek, LGPController popControl){
        sng = true;
        log = true;
        System.out.println("Loading game...");
        try{
            lfile = popControl.getFile();
            System.out.println(lfile.getName());
            try {
                String content = readFileToString(lfile.getPath());
                sendOutput(content);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }catch(NullPointerException e){lfile = null;}
        Platform.runLater(popek::close);
        //TODO
    }

    private void newBoard(int size){
        Platform.runLater(()->{
            controller.getGamePlace().getChildren().remove(goban);});
        goban = null;
        goban = new GameBoard(size);
        AddEventHandlers(goban.getIsecs());
        Platform.runLater(()->{controller.getGamePlace().getChildren().add(goban);});
        sng = false;
    }
    private void AddEventHandlers(Intersection[][] intersections) { // Tu podmienie potem na gb.getCrossings() powinno dzialac
        if(!log) {
            for (int i = 0; i < intersections.length; i++) {
                for (int j = 0; j < intersections[0].length; j++) {
                    intersections[i][j].setOnMouseClicked(this::handleSpotMouseClick);
                }
            }
        }
        log = false;
    }
    private void handleSpotMouseClick(MouseEvent event) {
        if (event.getSource() instanceof Intersection clickedSpot && myTurn) { //tutaj nie jestem dumny z tego instanceof, mozna go podmienic na try catcha
            if(clickedSpot.getStone() == null) {
                System.out.println(clickedSpot.getX() + " " + clickedSpot.getY());
                Request op = new Request();
                op.setX(clickedSpot.getX()); op.setY(clickedSpot.getY()); op.setLobbyId(lobbyId); op.setPlayerId(myName);
                sendOutput(op);
            } else {
                System.out.println("zajete pole");
            }
        } else {
            System.out.println("Czekaj na swoja kolej");
        }
    }


    //Testing new thing
    private static String readFileToString(String filePath) throws IOException {
        Path path = Paths.get(filePath);
        byte[] encoded = Files.readAllBytes(path);
        return new String(encoded);
    }
}
