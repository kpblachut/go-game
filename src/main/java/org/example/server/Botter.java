package org.example.server;

import org.example.Request;

import java.util.ArrayList;
import java.util.Random;
public class Botter {
    Random random;
    int color;
    int mx, my;
    private class field {
        int x;
        int y;
        field(int x,int y){
            this.x=x;
            this.y=y;
        }
        public int getX(){return x;}
        public int  getY(){return y;}
    }
    ClientHandler mock;
    String side;
    Botter(int color){this.color = color;
    mock = new ClientHandler();
    mock.setSide(String.valueOf(color));}

    public ClientHandler getMock() {
        return mock;
    }

    public String getSide() {
        return side;
    }

    public void makeMove(Integer[][] board) {//(y,x)
        int bolor = this.color;
        ArrayList<field> myFields = botFields(board, bolor);
        ArrayList<field> legalFields = legalFields(board,bolor);
        field wolne=null;
        boolean legal = false;
        while(legal!=true){
            if (legalFields.isEmpty()) {
                mx = -1;
                my = -1;
            } else if (myFields.isEmpty()) {
                wolne = legalFields.get(random.nextInt(legalFields.size()));
                mx = wolne.getX();
                my = wolne.getY();
            } else if (random.nextInt(100)<15) {
                wolne = legalFields.get(random.nextInt(legalFields.size()));
                mx = wolne.getX();
                my = wolne.getY();
            } else {
                field moje;
                for (int i = 0; i < 5; i++) {
                    moje = myFields.get(random.nextInt(myFields.size()));
                    wolne = nearEField(moje, board,bolor);
                    if (wolne != null) {
                        break;
                    }
                }
                if (wolne == null) {
                    wolne = legalFields.get(random.nextInt(legalFields.size()));
                }
                mx = wolne.getX();
                my = wolne.getY();
            }
            if(mx!=-1){legal= isLegal(board,mx,my);} else{legal = true;}
        }
    }
    public Request makeMoveOnBoard(Integer[][] board) {//(y,x)
        int bolor = this.color;
        random = new Random();
        ArrayList<field> myFields = botFields(board,bolor);
        ArrayList<field> legalFields = legalFields(board,bolor);
        field wolne=null;
        boolean legal = false;
        while(legal!=true){
            if (legalFields.isEmpty()) {
                mx = -1;
                my = -1;
            } else if (myFields.isEmpty()) {
                wolne = legalFields.get(random.nextInt(legalFields.size()));
                mx = wolne.getX();
                my = wolne.getY();
            } else if (random.nextInt(100)<15) {
                wolne = legalFields.get(random.nextInt(legalFields.size()));
                mx = wolne.getX();
                my = wolne.getY();
            } else {
                field moje;
                for (int i = 0; i < 5; i++) {
                    moje = myFields.get(random.nextInt(myFields.size()));
                    wolne = nearEField(moje, board, bolor);
                    if (wolne != null) {
                        break;
                    }
                }
                if (wolne == null) {
                    wolne = legalFields.get(random.nextInt(legalFields.size()));
                }
                mx = wolne.getX();
                my = wolne.getY();
            }
            if(mx!=-1){legal= isLegal(board,mx,my);} else{legal = true;}
        }
        Request rq=new Request();
        rq.setX(my);
        rq.setY(mx);
        return rq;
    }

    private boolean isBoardempty(Integer[][] board){
        for(int i=0;i<board.length;i++){
            for(int j=0;j<board.length;j++){
                if(board[i][j] != 0){
                    return false;
                }
            }
        }
        return true;
    }

    private boolean isLegal(Integer[][] board, int x, int y) {
        int countem=0;
        if(board[y][x]!=0){countem+=10;}
        try{if(board[y-1][x]!=0 && y!=0){countem++;}}catch(Exception e){}
        try{if(board[y+1][x]!=0 && y!=board.length-1){countem++;}}catch(Exception e){}
        try{if(board[y][x-1]!=0 && x!=0){countem++;}}catch(Exception e){}
        try{if(board[y][x+1]!=0 && x!=board.length-1){countem++;}}catch(Exception e){}
        if(y==0 || x==0 || y==board.length-1 || x==board.length-1) {
            countem += 2;
        }
        if(countem>2){
            return false;
        } else return true;
    }

    private boolean koPos(Integer[][] board, int x, int y, int bColor) {
        int counten = 0;
        if(board[y][x]!=0){counten+=10;}
        try{if(board[y-1][x]!=bColor &&board[y-1][x]!=0 && y!=0){counten++;}}catch(Exception e){}
        try{if(board[y-1][x]!=bColor && board[y+1][x]!=0 && y!=board.length-1){counten++;}}catch(Exception e){}
        try{if(board[y-1][x]!=bColor &&board[y][x-1]!=0 && x!=0){counten++;}}catch(Exception e){}
        try{if(board[y][x+1]!=bColor && x!=board.length-1){counten++;}}catch(Exception e){}
        if(counten > 2) {
            return true;
        } else return false;
    }

    private ArrayList<field> emptyFields(Integer board[][]){
        ArrayList<field> fields = new ArrayList<>();
        for(int i=0;i<board.length;i++){
            for(int j=0;j<board.length;j++){
                if(board[i][j] == 0){
                    fields.add(new field(j,i));
                }
            }
        }
        return fields;
    }

    private ArrayList<field> botFields(Integer board[][], int bColor){
        ArrayList<field> fields = new ArrayList<>();
        for(int i=0;i<board.length;i++){
            for(int j=0;j<board.length;j++){
                if(board[i][j] == bColor){
                    fields.add(new field(j,i));
                }
            }
        }
        return fields;
    }

    private ArrayList<field> legalFields(Integer board[][],int bColor){
        ArrayList<field> fields = new ArrayList<>();
        for(int i=0;i<board.length;i++){
            for(int j=0;j<board.length;j++){
                if(board[i][j] == 0 && isLegal(board,i,j) && !koPos(board,i,j,bColor)){
                    fields.add(new field(j,i));
                }
            }
        }
        return fields;
    }

    private field nearEField(field f, Integer board[][], int bColor){
        field ret=null;
        for(int r=1; r<=5;r++) {
            for (int i = f.getY() - r; i <=f.getY()+r; i++){
                for (int j = f.getX() - r; j <=f.getX()+r; j++){
                    if(aCiB(i,j, board.length, board.length)){
                        if(isLegal(board,j,i) && !koPos(board, j, i,bColor)){
                            return new field(j,i);
                        }
                    } else{
                        ret = null;
                    }
                }
            }
        }
        return ret;
    }

    private static boolean aCiB(int x, int y, int rows, int columns) {
        return x >= 0 && x < columns && y >= 0 && y < rows;
    }
    public int getX(){return mx;}
    public int  getY(){return my;}
    public int getColor(){return color;}
}
