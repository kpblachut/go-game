package org.example.client;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.example.NewController;

import java.io.*;
import java.net.Socket;
import java.util.zip.GZIPOutputStream;

public class Client {
    private static final String SERVER_IP = "localhost";
    private static final int SERVER_PORT = 12345;

    ObjectOutputStream outputWriter;

    NewController controller;
    private GameBoard goban;
    private Scene scene;
    static Client instance;

    boolean gexit = false;
    Socket socket;

    CurrentGame cg;

    /*
    public static void main(String[] args) {
        new Client().startClient();
    }
     */

    public Client(NewController controller) {
        this.instance = this;
        this.controller = controller;
        goban = new GameBoard(19);

        Platform.runLater(()->{
            controller.getGamePlace().getChildren().add(goban); //Fake board, without listeners to place stones
        });

        cg = new CurrentGame(this, controller);

        System.out.println("Client initialised");
    }

    public void startClient() {
        try {
            System.out.println("starting client");
            socket = new Socket(SERVER_IP, SERVER_PORT);

            outputWriter = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());

            new Thread(new ClientListener(ois, instance)).start();

        } catch (IOException e) {
            quit();
            //e.printStackTrace();
        }
    }

    static class ClientListener implements Runnable {

        Client instance;
        private BufferedReader reader;
        private ObjectInputStream ois;

        public ClientListener(/*BufferedReader reader*/ ObjectInputStream ois, Client instance) {
            //this.reader = reader;
            this.ois = ois;
            this.instance = instance;
            System.out.println("Client listener initialised");
        }

        @Override
        public void run() {
            try {
                //String serverMessage;
                System.out.println("listening for objects");
                Object serverObject;
                System.out.println("sth");
                while ((serverObject = ois.readObject()) != null && !instance.gexit) {
                    if(serverObject instanceof Response){
                        Response so = (Response) serverObject;
                        //System.out.println(so.getBoard());
                        //System.out.println("getting object from server");
                        instance.handle(so);
                    } else {continue;}
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public GameBoard getGoban() {
        return goban;
    }

    public void handle(Response serverObject) {
        System.out.println("handling object from server");
        cg.handleInput(serverObject);
    }

    public void setScene(Scene scene) {
        this.scene = scene;
    }

    public Scene getScene(){
        return scene;
    }

    public void send(Object o) {
        if(outputWriter != null) {
            try {
                System.out.println("sending object to server");
                outputWriter.writeObject((Object) o);
                outputWriter.reset();
            } catch (IOException ioe) {
                ioe.printStackTrace();
            }
        }
    }

    public void setLobbyId(int lobbyId) {
        System.out.println("Changing name...");
        Platform.runLater(()->{((Stage) scene.getWindow()).setTitle("Go Game - Lobby: " + lobbyId);});
    }

    public void quit(){
        System.out.println("Quitting");
        gexit = true;
        if(socket!=null && socket.isConnected()){
            try{socket.close();}catch(IOException e){e.printStackTrace();}
        }
    }
}
