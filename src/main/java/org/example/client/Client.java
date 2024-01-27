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

    PrintWriter outputWriter;

    NewController controller;
    private GameBoard goban;
    private Scene scene;
    static Client instance;

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
    }

    public void startClient() {
        try {
            Socket socket = new Socket(SERVER_IP, SERVER_PORT);

            BufferedReader inputReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            outputWriter = new PrintWriter(socket.getOutputStream(), true);
            ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());

            // Set up client's nickname
            System.out.print("Enter your nickname: ");
            String nickname = new BufferedReader(new InputStreamReader(System.in)).readLine();
            outputWriter.println(nickname);

            // Menu to choose between creating and joining a lobby
            System.out.println("Choose an option:");
            System.out.println("1. Create a lobby");
            System.out.println("2. Join a lobby");

            int choice = Integer.parseInt(new BufferedReader(new InputStreamReader(System.in)).readLine());

            if (choice == 1) {
                outputWriter.println("CREATE_LOBBY");
            } else if (choice == 2) {
                System.out.print("Enter the lobby name to join: ");
                String lobbyToJoin = new BufferedReader(new InputStreamReader(System.in)).readLine();

                // For simplicity, the lobby code is assumed to be the same as the lobby name
                System.out.print("Enter the lobby code: ");
                String lobbyCode = new BufferedReader(new InputStreamReader(System.in)).readLine();

                outputWriter.println("JOIN_LOBBY " + lobbyToJoin + " " + lobbyCode);
            } else {
                System.out.println("Invalid choice. Exiting...");
                socket.close();
                return;
            }

            // Start a thread to listen for server messages
            new Thread(new ClientListener(ois, instance)).start();

            // Send messages to the server
            while (true) {
                System.out.print("Enter message (Type 'exit' to close): ");
                String message = new BufferedReader(new InputStreamReader(System.in)).readLine();
                outputWriter.println(message);

                if ("exit".equalsIgnoreCase(message)) {
                    break;
                }
            }

            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
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
        }

        @Override
        public void run() {
            try {
                //String serverMessage;
                Object serverObject;
                while ((serverObject = ois.readObject()) != null) {
                    instance.handle(serverObject);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public GameBoard getGoban() {
        return goban;
    }

    public void handle(Object serverObject) {
        cg.handleInput(serverObject);
    }

    public void setScene(Scene scene) {
        this.scene = scene;
    }

    public Scene getScene(){
        return scene;
    }

    public void send(String s) {
        if(outputWriter != null) {
            outputWriter.println(s);
        }
    }
}
