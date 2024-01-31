package org.example.server;

import org.example.server.exceptions.OutOfGameBoardException;
import org.example.server.exceptions.InvalidGameTurnEncounteredException;

import java.security.InvalidParameterException;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class GameBoard {
    private final int width, height;
    private final Intersection[][] intersections;
    private final GameRecord gameRecord;
    private Set<Intersection> lastCaptured;
    private Player P1, P2, currentPlayer;
    private int passCount;

    public GameBoard(int width, int height) {
        this.width = width;
        this.height = height;
        this.passCount = 0;
        this.intersections = new Intersection[width][height];
        this.gameRecord = new GameRecord(width, height);

        initGameBoard();
    }

    public GameBoard(GameRecord gameRecord) {
        this.gameRecord = gameRecord;
        this.width = gameRecord.getLastTurn().getBoardState().length;
        this.height = gameRecord.getLastTurn().getBoardState()[0].length;

        intersections = new Intersection[width][height];
        initGameBoard();

        try {
            takeGameTurn(this.gameRecord.getLastTurn(), P1, P2);
        } catch (Exception e) {
            System.out.println("Exception in GameBoard: takeGameTurn: " + e);
        }
    }

    private void initGameBoard() {
        lastCaptured = new HashSet<Intersection>();

        P1 = new Player(1);
        P2 = new Player(2);
        currentPlayer = P1;

        for (int x = 0; x < this.width; x++) {
            for (int y = 0; y < this.height; y++) {
                intersections[x][y] = new Intersection(this, x, y);
            }
        }
    }

    public int getPassCount() {
        return passCount;
    }

    public GameRecord getGameRecord() {
        return gameRecord;
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }

    public Intersection[][] getIntersections() {
        return intersections;
    }

    public void updatePassCount(boolean pass) {
        if (pass) {
            passCount ++;
        } else {
            passCount = 0;
        }
    }

    public void pass(Player player) {
        gameRecord.apply(gameRecord.getLastTurn().toNext(-1,-1, player.getId(),Collections.<Intersection>emptySet()));
        nextPlayer();
        updatePassCount(true);
    }

    public boolean isInBoard(int x, int y) {
        return (x >= 0 && x < width && y >= 0 && y < height);
    }

    public boolean isInBoard(Intersection intersection) {
        int x = intersection.getX();
        int y = intersection.getY();
        return (x >= 0 && x < width && y >= 0 && y < height);
    }

    public Intersection getIntersection(int x, int y) {
        if (isInBoard(x, y)) {
            return intersections[x][y];
        } else {
            return null;
        }
    }


    public boolean play(Intersection intersection, Player player, boolean handleKo) {
        boolean ko = false;
        GameTurn currentTurn = null;

        if (!isInBoard(intersection)) return false;

        if (intersection.getChain() != null) return false;

        if (player.getId() == currentPlayer.getId()) return false;

        Set<Intersection> capturedStones = null;
        Set<Chain> capturedChains = null;

        if (handleKo) {
            capturedStones = new HashSet<Intersection>();
            capturedChains = new HashSet<Chain>();
        }

        Set<Chain> neighbouringChains = intersection.getNeighbours();
        Chain newChain = new Chain(intersection, player);
        intersection.setChain(newChain);
        for (Chain chain : neighbouringChains) {
            if (chain.getOwner() == player) {
                newChain.add(chain, intersection);
            } else {
                chain.removeLiberty(intersection);
                if (chain.getLiberties().size() == 0) {
                    if(handleKo) {
                        capturedStones.addAll(chain.getStones());
                        capturedChains.add(new Chain(chain));
                    }
                    chain.die();
                }
            }
        }
        if (handleKo) {
            currentTurn = gameRecord.getLastTurn().toNext(intersection.getX(),intersection.getY(),player.getId(),capturedStones);
            for (GameTurn turn : gameRecord.getTurns()) {
                if (turn.equals(currentTurn)) {
                    ko = true;
                    break;
                }
            }
            if (ko) {
                for (Chain chain : capturedChains) {
                    chain.getOwner().removeCapturedStones(chain.getStones().size());
                    for (Intersection stone : chain.getStones()) {
                        stone.setChain(chain);
                    }
                }
            }
        }

        // Preventing suicide or ko and re-adding liberty
        if (newChain.getLiberties().size() == 0 || ko) {
            for (Chain chain : intersection.getNeighbours()) {
                chain.getLiberties().add(intersection);
            }
            intersection.setChain(null);
            return false;
        }

        // Move is valid, applying changes
        for (Intersection stone : newChain.getStones()) {
            stone.setChain(newChain);
        }
        if (handleKo) {
            gameRecord.apply(currentTurn);
        }

        lastCaptured = capturedStones;
        updatePassCount(false);
        return true;
    }

    public boolean play(Intersection intersection, Player player) {
        return play(intersection,player,true);
    }

    public boolean play(int x, int y, Player player) throws OutOfGameBoardException {
        Intersection intersection = getIntersection(x, y);
        if (intersection == null) {
            throw new OutOfGameBoardException("Intersection is out of range: x=" + x + " y=" + y);
        }
        return play(intersection, player);
    }

    public void freeIntersections() {
        for(Intersection[] intersectionColumn : intersections) {
            for(Intersection intersection : intersectionColumn) {
                intersection.setChain(null);
            }
        }
    }

    public void takeGameTurn(GameTurn gameTurn,Player P1, Player P2) throws InvalidGameTurnEncounteredException, InvalidParameterException {
        this.freeIntersections();
        if(gameTurn == null || P1 == null || P2 == null) throw new InvalidParameterException("None of the Parameters should not be null.");
        if(P1.getId() != 1 || P2.getId() != 2) throw new InvalidParameterException("Incorrect Players entered. One should have an id == 1 and P2 an id == 2, here P1.id = "+P1.getId()+" and P2.id = "+P2.getId());
        if(gameTurn.getBoardState().length != width || gameTurn.getBoardState()[0].length != height ) throw new InvalidGameTurnEncounteredException("Incompatible board dimensions between GameBoard and given GameTurn");

        Integer[][] boardState = gameTurn.getBoardState();
        for (int x = 0; x < width ; x++) {
            for (int y = 0; y < height ; y++) {
                switch (boardState[x][y]) {
                    case 2:
                        play(getIntersection(x,y),P2,false);
                        break;
                    case 1:
                        play(getIntersection(x,y),P1,false);
                        break;
                    case 0:
                        //DO NOTHING
                        break;
                    default:
                        throw new InvalidGameTurnEncounteredException("Unexpected intersection state encountered: "+boardState[x][y]);
                }
            }
        }

    }

    public Player getPlayer() {
        return currentPlayer;
    }

    public Player getPlayer(int p) {
        return switch (p) {
            case 1 -> P1;
            case 2 -> P2;
            default -> null;
        };
    }

    public boolean nextPlayer() {
        return changePlayer(false);
    }


    public boolean changePlayer(boolean undo) {
        if (currentPlayer == P1) {
            currentPlayer = P2;
            System.out.println("Changing player for P2");
        } else {
            currentPlayer = P1;
            System.out.println("Changing player for P1");
        }
        return true;
        }

}

