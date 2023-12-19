package org.example.client;

import javafx.application.Application;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Client{
    private static final String SERVER_IP = "localhost";
    private static final int SERVER_PORT = 12345;

    public static void main(String[] args) {
        new Client().startClient();
    }

    public void startClient() {
        try {
            Socket socket = new Socket(SERVER_IP, SERVER_PORT);

            BufferedReader inputReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter outputWriter = new PrintWriter(socket.getOutputStream(), true);

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
            new Thread(new ClientListener(inputReader)).start();

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
        private BufferedReader reader;

        public ClientListener(BufferedReader reader) {
            this.reader = reader;
        }

        @Override
        public void run() {
            try {
                String serverMessage;
                while ((serverMessage = reader.readLine()) != null) {
                    System.out.println(serverMessage);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
