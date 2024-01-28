package org.example.client;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import org.example.NewController;

import java.io.IOException;

public class CurrentGame {

    String myName, opName;
    int lobbyId;

    private boolean myTurn;
    private boolean sng;
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
        goban = client.getGoban();
        sng = true;
    }

    public void handleInput(Object input) {
        /*TODO

         */
        Response ip = (Response) input;
        if(ip.player != myName){
            myTurn = false;
            opName = ip.player;
        }
        if(!sng && ip.board.length == goban.size) {
            goban.updateBoard(ip.board);
        } else if (!sng && ip.board.length != goban.size) {
            System.out.println("This shouldn't happen :(");
        } else if (sng) {
            newBoard(ip.board.length);
            goban.updateBoard(ip.board);
            lobbyId = ip.lobbyId;
            client.setLobbyId(lobbyId);
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
            popControl.getCGButton().setOnAction(e -> NewGame(e,popControl.getSizeChoice().getValue(), popek));
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
            /*TODO
            Tutaj dodac obsluga fileExplorator i przekazywanie pliku do ładowania
            + jeszcze zeby popup sie zamykał kiedy sie juz wlaczy nowa gre
            */
            popControl.getLoadButton().setOnAction(e -> LoadGame(e, popek));

            Platform.runLater(popek::show);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void passTurn(ActionEvent event) {
        System.out.println("Passing my turn...");
        /*TODO

         */
    }

    private void giveUp(ActionEvent event) {
        System.out.println("Giving up...");
        /*TODO

         */
    }

    private void saveGame(ActionEvent event) {
        System.out.println("Saving game...");
        /*TODO

         */
    }

    private void quit(ActionEvent event) {
        System.out.println("Quitting game...");
        /*TODO

         */
    }

    private void NewGame(ActionEvent e, int size, Stage popek) {
        sng = true;
        System.out.println("Starting new game, size: " + size);
        sendOutput(new Request(size, myName,0, 0));
        Platform.runLater(popek::close);
        /*TODO

         */
    }

    private void JoinGame(ActionEvent e, String code, Stage popek) {
        sng = true;
        System.out.println("Joining game with code: " + code);
        sendOutput(new Request(Integer.parseInt(code), myName));
        Platform.runLater(popek::close);
        /*TODO

         */
    }

    private void NewGameWB(ActionEvent e, int size, Stage popek){
        sng = true;
        System.out.println("Starting new game with bot, size: " + size);
        sendOutput(new Request(size, myName,1, 0));
        Platform.runLater(popek::close);
        /*TODO

         */
    }

    private void LoadGame(ActionEvent event,/* cos jeszcze, pewnie plik ze stackiem*/ Stage popek){
        sng = true;
        System.out.println("Loading game...");
        Platform.runLater(popek::close);
        //TODO
    }

    private void newBoard(int size){
        goban = null;
        goban = new GameBoard(size);
        AddEventHandlers(goban.getIsecs());
        sng = false;
    }
    private void AddEventHandlers(Intersection[][] intersections) { // Tu podmienie potem na gb.getCrossings() powinno dzialac
        for (int i = 0; i < intersections.length; i++) {
            for(int j = 0; j < intersections[0].length; j++) {
                intersections[i][j].setOnMouseClicked(this::handleSpotMouseClick);
            }
        }
    }
    private void handleSpotMouseClick(MouseEvent event) {
        if (event.getSource() instanceof Intersection clickedSpot && myTurn) { //tutaj nie jestem dumny z tego instanceof, mozna go podmienic na try catcha
            if(clickedSpot.getStone() == null) {
                sendOutput(new Request(clickedSpot.getX(),clickedSpot.getY(),lobbyId,myName));
            } else {
                System.out.println("zajete pole");
            }
        } else {
            System.out.println("Czekaj na swoja kolej");
        }
    }
}
