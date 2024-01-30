package org.example.server;

import java.util.Arrays;
import java.util.Set;

public class GameTurn {
    private final Integer[][] boardState;
    private final int x, y;
    private final int hashcode;
    private final int capturedStones;
    private int passCount;

    public GameTurn(GameTurn source) {
        int width = source.boardState.length;
        int height = source.boardState[0].length;
        x = source.x;
        y = source.y;
        hashcode = source.hashcode;
        capturedStones = source.capturedStones;
        passCount = source.passCount;
        boardState = new Integer[width][height];
        for (int i = 0; i < width ; i++) {
            boardState[i] = source.boardState[i].clone();
        }
    }

    public GameTurn(int width, int height) {
        boardState = new Integer[width][height];
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                boardState[i][j] = 0;
            }
        }
        capturedStones = 0;
        passCount = 0;

        // Move is virtual, x and y are set to -1
        x = -1;
        y = -1;

        hashcode = Arrays.deepHashCode(boardState);
    }

    /**
     * Constructor which uses the previous GameTurn to be able to record the new state based on the previous one,
     * And applying the potential modifications:  adding the played stone, and removing and counting captured stones.
     * Cam also make a passing move by setting the coordinates of the played stone to (-1,-1).
     * @param prev The previous GameTurn.
     * @param X The x coordinate of the played stone, this game turn, -1 if the player passes.
     * @param Y The y coordinate of the played stone, this game turn, -1 if the player passes.
     * @param playerId The id of the player making the given game turn.
     * @param freedIntersections A set of Intersections which may have been freed, due to being captured.
     */
    private GameTurn(GameTurn prev, int X, int Y, int playerId, Set<Intersection> freedIntersections ) {
        int width = prev.boardState.length;
        int height = prev.boardState[0].length;

        boardState = new Integer[width][height];
        for (int i = 0; i < width ; i++) {
            boardState[i] = prev.boardState[i].clone();
        }
        x = X;
        y = Y;

        // Applying the played stone change, if is not a pass move
        if ( x >= 0 && y >= 0 ) {
            boardState[x][y] = playerId;
            passCount = 0;
        } else {
            passCount = prev.passCount + 1;
        }

        // Setting all the freed intersections to 0, and counting the number of captured stones
        for(Intersection freedIntersection : freedIntersections) {
            boardState[freedIntersection.getX()][freedIntersection.getY()] = 0;
        }
        capturedStones = freedIntersections.size();;

        // Using Java Tools to make a pertinent hash on the goban state
        hashcode = Arrays.deepHashCode(boardState);
    }

    /**
     * Wrapper for the private constructor used to build a new game turn based on a previous one.
     * @param X The x coordinate of the played stone, this game turn, -1 if the player passes.
     * @param Y The y coordinate of the played stone, this game turn, -1 if the player passes.
     * @param playerId The Id of the player making the given game turn.
     * @param freedIntersections A set of Intersections which may have been freed, due to being captured.
     */
    public GameTurn toNext(int X, int Y, int playerId, Set<Intersection> freedIntersections) {
        return new GameTurn(this,X,Y,playerId, freedIntersections);
    }

    public int getCapturedStones() {
        return capturedStones;
    }

    public int getHashcode() {
        return hashcode;
    }

    public int getPassCount() {
        return passCount;
    }

    public int getX() {
        return x;
    }

    public Integer[][] getBoardState() {
        return boardState;
    }

    public int getY() {
        return y;
    }

    /**
     * Overriding the equals function, first check against hash codes of the goban states for speed,
     * Then if the hashCodes are the same makes a deep comparison between the goban states.
     * @param obj The object to be compared to this.
     * @return {@code true} if the two objects have the same GobanState,
     * {@code false} otherwise.
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;

        GameTurn castedObj = (GameTurn) obj;

        return hashcode == castedObj.hashcode && Arrays.deepEquals(this.boardState, castedObj.boardState);
    }
}
