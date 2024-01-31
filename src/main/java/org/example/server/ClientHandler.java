package org.example.server;

import java.io.*;
import java.net.Socket;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

import org.example.Request;
import org.example.Response;
import org.example.server.exceptions.OutOfGameBoardException;

public class ClientHandler implements Runnable {
    private Socket clientSocket;
    private Server server;
    private ObjectInputStream inputReader;
    private ObjectOutputStream outputWriter;
    private int clientId;
    private Lobby lobby;
    private boolean inLobby;
    private boolean passed;
    private boolean sendScore;
    private String side;
    private String save;
    private String clientName;
    private String currentLobby;
    private String lobbyCode;
    Lobby MyLobby;

    public ClientHandler(Socket clientSocket, Server server, int clientId) {
        this.clientSocket = clientSocket;
        this.server = server;
        this.inLobby = false;
        this.clientId = clientId;
        try {
            inputReader = new ObjectInputStream(clientSocket.getInputStream());
            outputWriter = new ObjectOutputStream(clientSocket.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getClientName() {
        return clientName;
    }

    public void sendMessage(Object message) {
        try {
            outputWriter.writeObject(message);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    @Override
    public void run() {
        try {
            Request clientMessage;
            try {
                while ((clientMessage = (Request) inputReader.readObject()) != null) {
                    if (inLobby) {
                        lobby.makeMove(clientMessage.getX(), clientMessage.getY(), this);

                        sendResponseToAll();
                        continue;
                    }
                    if(clientMessage.getSize() != null) {
                        // Create new game
                        lobby = server.createLobby(clientMessage.getSize());
                        lobby.joinLobby(this, Integer.toString(clientMessage.getRandomColor()));
                        inLobby = true;

                        sendResponse();
                    }
                    if (clientMessage.getLobbyId() != null) {
                        lobby = server.fetchLobby(Integer.toString(clientMessage.getLobbyId()));
                        lobby.joinLobby(this);
                        inLobby = true;

                        sendResponse();
                    }
                    if (clientMessage.getSave() != null) {
                        lobby = server.createLobby(server.loadFromString(clientMessage.getSave()));
                        lobby.joinLobby(this);
                        inLobby = true;

                        sendResponse();
                    }
                }
            } catch (ClassNotFoundException e) {} catch (OutOfGameBoardException e) {
                throw new RuntimeException(e);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            // Remove the client from the lobby and the server when the connection is closed
            server.removeClient(this);
        }
    }

    public void setSide(String side) {
        this.side = side;
    }

    public int getClientId() {
        return clientId;
    }

    public void setPassed(boolean passed) {
        this.passed = passed;
    }

    public void setSendScore(boolean sendScore) {
        this.sendScore = sendScore;
    }

    public void setSave(String save) {
        this.save = save;
    }

    public String getSave() {
        return save;
    }

    public Response prepareResponse() {
        Response response = new Response();
        if (passed) {
            response.setPassed(true);
            setPassed(false);
        }
        if (sendScore) {
            response.setScores(lobby.getScores());
            setSendScore(false);
        }
        if (save != null) {
            response.setSave(save);
            save = null;
        }
        response.setBoard(lobby.getGameBoard().getGameRecord().getLastTurn().getBoardState());
        response.setPlayer(lobby.getPlayerSide(Integer.toString(clientId)));
        response.setLobbyId(lobby.getLobbyCode());
        for (Integer[] row: response.getBoard()) {
            System.out.println(Arrays.toString(row));
        }
        System.out.println();

        return response;
    }
    public void sendResponse() throws IOException {
        Response response = prepareResponse();
        outputWriter.writeObject(response);
        outputWriter.reset();
    }

    public void sendResponseToAll() throws IOException {
        Set<String> players = lobby.getPlayerIds();
        for (String playerId : players) {
            ClientHandler clientHandler = server.getClient(playerId);
            clientHandler.sendResponse();
        }
    }
}
