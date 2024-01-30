package org.example.server;

import java.util.ArrayList;
import java.util.Random;
public class Botter {
    Random random;
    int bColor;
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
    Botter(int bColor) {
        this.bColor = bColor;
        random = new Random();
    }

    public void makeMove(Integer[][] board) {//(y,x)
        ArrayList<field> myFields = botFields(board);
        ArrayList<field> legalFields = legalFields(board);
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
            } else if (random.nextBoolean()) {
                wolne = legalFields.get(random.nextInt(legalFields.size()));
                mx = wolne.getX();
                my = wolne.getY();
            } else {
                field moje;
                for (int i = 0; i < 5; i++) {
                    moje = myFields.get(random.nextInt(myFields.size()));
                    wolne = nearEField(moje, board);
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

    private boolean koPos(Integer[][] board, int x, int y) {
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

    private ArrayList<field> botFields(Integer board[][]){
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

    private ArrayList<field> legalFields(Integer board[][]){
        ArrayList<field> fields = new ArrayList<>();
        for(int i=0;i<board.length;i++){
            for(int j=0;j<board.length;j++){
                if(board[i][j] == 0 && isLegal(board,i,j) && !koPos(board,i,j)){
                    fields.add(new field(j,i));
                }
            }
        }
        return fields;
    }

    private field nearEField(field f, Integer board[][]){
        field ret=null;
        for(int r=1; r<=5;r++) {
            for (int i = f.getY() - r; i <=f.getY()+r; i++){
                for (int j = f.getX() - r; j <=f.getX()+r; j++){
                    if(aCiB(i,j, board.length, board.length)){
                        if(isLegal(board,j,i) && !koPos(board, j, i)){
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
}
