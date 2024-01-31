package org.example.server;

import java.io.*;
import java.util.EmptyStackException;
import java.util.Stack;

public class GameRecord {
    private final Stack<GameTurn> preceding;
    private final Stack<GameTurn> following;

    public GameRecord(int width, int height) {
        preceding = new Stack<GameTurn>();
        following = new Stack<GameTurn>();

        GameTurn first = new GameTurn(width, height);

        apply(first);
    }

    public GameRecord(GameRecord record) {
        this(record.preceding, record.following);
    }

    private GameRecord(Stack<GameTurn> preceding, Stack<GameTurn> following) {
        this.preceding = new Stack<GameTurn>();
        this.following = new Stack<GameTurn>();
        this.preceding.addAll(preceding);
        this.following.addAll(following);
    }

    public void apply(GameTurn turn) {
        preceding.push(turn);
        following.clear();
    }

    public boolean hasPreceding() {
        return preceding.size() > 1;
    }

    public int numOfPreceding() { return preceding.size() - 1; }

    public boolean hasFollowing() {
        return following.size() > 0;
    }

    public Iterable<GameTurn> getTurns() {
        return preceding;
    }

    public GameTurn getLastTurn() {
        return preceding.peek();
    }

    @Override
    public boolean equals(Object obj) {
        if(this == obj) return true;
        if(obj == null || obj.getClass() != this.getClass()) return false;

        GameRecord castedObj = (GameRecord) obj;

        if (preceding.size() != castedObj.preceding.size()) return false;

        for (int i = 0; i < preceding.size(); i++) {
            if (!preceding.get(i).equals(castedObj.preceding.get(i))) return false;
        }
        for (int i = 0; i < following.size(); i++) {
            if (!following.get(i).equals(castedObj.following.get(i))) return false;
        }

        return true;
    }

    public void undo() throws EmptyStackException {
        if (preceding.size() > 1) {
            following.push(preceding.pop());
        } else {
            throw new EmptyStackException();
        }
    }

    public void redo() throws EmptyStackException {
        preceding.push(following.pop());
    }

    public String save() {
        StringWriter stringWriter = new StringWriter();
        BufferedWriter writer = new BufferedWriter(stringWriter);
        try {
            writer.write("{");
            writer.newLine();
            writer.write("  \"width\": " + preceding.peek().getBoardState().length + ",");
            writer.newLine();
            writer.write("  \"height\": " + preceding.peek().getBoardState()[0].length + ",");
            writer.newLine();

            writer.write("  \"preceding\": [");
            writer.newLine();

            for (GameTurn turn : preceding) {
                writer.write("            [ " + turn.getX() + " , " + turn.getY() + " ],");
                writer.newLine();
            }
            writer.write("            null");
            writer.newLine();
            writer.write("         ],");
            writer.newLine();

            Stack<GameTurn> followForWrite = new Stack<GameTurn>();
            for (int i = following.size() - 1; i >= 0; i--) {
                followForWrite.push(following.get(i));
            }

            writer.write("  \"following\": [");
            writer.newLine();
            for (GameTurn turn : followForWrite) {
                writer.write("            [ " + turn.getX() + " , " + turn.getY() + " ],");
                writer.newLine();
            }
            writer.write("            null");
            writer.newLine();
            writer.write("         ]");
            writer.newLine();

            writer.write("}");
            writer.newLine();

            writer.close();
            return stringWriter.toString();
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }


    public static GameRecord load(String data) {
        StringReader stringReader = new StringReader(data);
        BufferedReader reader = new BufferedReader(stringReader);
        GameRecord record = null;
        try {
            String delim = "\\s*[\\[\\]\\{\\},:]\\s*|\\s+|\\s*null\\s*";

            reader.readLine();

            int width = Integer.parseInt(reader.readLine().split(delim)[2]);
            int height = Integer.parseInt(reader.readLine().split(delim)[2]);

            GameBoard gameBoard = new GameBoard(width, height);
            Player one = new Player(1);
            Player two = new Player(2);
            Player actualPlayer = one;

            int x, y;
            String[] line;

            reader.readLine(); // Skipping first virtual move;
            reader.readLine();

            int i = 0;
            int precedingCount = 0;
            boolean precedingCompleted = false;
            while(true) {
                line = reader.readLine().split(delim);
                if(line.length == 0) {
                    if (precedingCompleted) {
                        break;
                    } else {
                        precedingCompleted = true;
                        precedingCount = i;
                        reader.readLine();
                        reader.readLine();
                        continue;
                    }
                }

                x = Integer.parseInt(line[1]);
                y = Integer.parseInt(line[2]);

                actualPlayer.play(gameBoard, x, y);

                if (actualPlayer == one) {
                    actualPlayer = two;
                } else {
                    actualPlayer = one;
                }

                i++;
            }

            reader.close();

            record = gameBoard.getGameRecord();
            for (int j = i; j > precedingCount; j--) {
                record.undo();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }

        return record;
    }


}
