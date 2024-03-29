package org.example.client;

import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Border;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.geometry.Pos;

import static javafx.scene.layout.StackPane.setAlignment;

public class Intersection extends StackPane {

    private static final int BW = 608; // BOARD WIDTH
    final GameBoard gb;
    final int x;
    final int y;

    Stone stone;

    //StackPane field;
    Circle shadow;
    boolean hs;

    public Intersection(GameBoard gb, int x, int y) {
        super();
        hs = false;
        this.gb = gb;
        this.x = x;
        this.y = y;

        BuildField(this, gb.getSize());
    }

    /*
    public StackPane getField(){
        return field;
    }
    */

    public void placeStone(Stone stone){
        hs=true;
        double size = 0.4 * ((double) BW / gb.getSize());
        if(this.stone == null) {
            this.stone = stone;
        }
        stone.setScaleY(size);
        stone.setScaleX(size);
        stone.setStrokeWidth(0.1);

            stone.setVisible(true);
            this.getChildren().add(stone);
            stone.setCenterX(size / 2);
            stone.setCenterY(size / 2);
            shadow.setVisible(false);
    }

    public void rmStone(){
        hs=false;
        if(stone != null) {
            //Platform.runLater(() -> {
                stone.setVisible(false);
                this.getChildren().remove(stone);
            //});
            this.stone = null;
        }
    }

    public void updateIC(Stone st){
        double size = 0.4 * ((double) BW / gb.getSize());
        boolean change=false;
        if(st==null){if(hs){change = true;}
            hs=false;}
        else {
            if(!hs){change=true;}
            if(hs && this.getStone()!=null){
                if(this.getStone().getType()==st.getType())
                    change=false;
            }
            hs=true;
        }
        if(change){
            Platform.runLater(() -> {
                if (st == null) {
                    if(this.stone!=null) {
                        stone.setVisible(false);
                        this.getChildren().remove(this.stone);
                    }
                    shadow.setVisible(false);
                } else {
                    st.setScaleY(size);
                    st.setScaleX(size);
                    st.setStrokeWidth(0.1);
                    this.stone = st;
                    this.getChildren().add(st);
                }
            });
        }
    }
    public int getX(){
        return x;
    }

    public int getY(){
        return y;
    }

    public Stone getStone(){
        return stone;
    }

    // Functions for building appearence only
    private void BuildField(StackPane f, int border) {
        double size = (double) BW /border;
        f.setPrefSize(size, size);

        f.setBorder(Border.EMPTY);
        f.getChildren().add(new Rectangle(size,size, Color.valueOf("#524631"))); // #524631
        setAlignment(f.getChildren().get(0), javafx.geometry.Pos.CENTER);

        //Squares for board look
        Rectangle luc = new Rectangle(0.483*size,0.483*size, Color.valueOf("#ded9b6"));
        Rectangle ruc = new Rectangle(0.483*size,0.483*size, Color.valueOf("#ded9b6"));
        Rectangle rlc = new Rectangle(0.483*size,0.483*size, Color.valueOf("#ded9b6"));
        Rectangle llc = new Rectangle(0.483*size,0.483*size, Color.valueOf("#ded9b6"));

        //Shadow for stones
        shadow = new Circle(0.4*size);
        shadow.setOpacity(0.9);
        shadow.setVisible(false);
        setShadowColor("#888888"); //Grey

        if(x == 0){llc.setHeight(0.517*size);}
        if(x == border - 1){ruc.setHeight(0.517*size);}
        if(y == 0){rlc.setWidth(0.517*size);}
        if(y == border - 1){luc.setWidth(0.517*size);}

        f.getChildren().add(luc);
        f.getChildren().add(ruc);
        f.getChildren().add(rlc);
        f.getChildren().add(llc);
        f.getChildren().add(shadow);

        f.addEventFilter(MouseEvent.MOUSE_ENTERED, eventHandler);
        f.addEventFilter(MouseEvent.MOUSE_EXITED, eventHandler2);

        setAlignment(luc, Pos.TOP_LEFT);
        setAlignment(ruc, Pos.TOP_RIGHT);
        setAlignment(llc, Pos.BOTTOM_LEFT);
        setAlignment(rlc, Pos.BOTTOM_RIGHT);
    }

    public void setShadowColor(String color){
        Platform.runLater(()->{shadow.setFill(Color.valueOf(color));});
    }

    EventHandler<MouseEvent> eventHandler = new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent e) {
            if(!hs) {
                shadow.setVisible(true);
            }
        }
    };

    EventHandler<MouseEvent> eventHandler2 = new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent e) {
            if(!hs) {
                shadow.setVisible(false);
            }
        }
    };

    public boolean hasStone(){
        return hs;
    }
}
