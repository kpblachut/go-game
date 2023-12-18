package org.example.client;

import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Ellipse;
import javafx.scene.shape.ObservableFaceArray;
import javafx.scene.shape.Rectangle;

public class Spot extends StackPane {
    private Stone stone;

    Ellipse shadow;
    ObservableList<Node> nodes;

    public boolean hasStone(){
        return stone != null;
    }

    public Spot(){
        super();
        // rozmiar pola dla kamyczka
        this.setWidth(50);
        this.setHeight(50);


        // Jak na razie debilny sposób ustawiania tła dla pola
        // do podmianki są te rozmiary, na jakiś getWidth(), getHeight()
        // i przemnożenie tego przez odpowiedni procent
        // ale aktualnie jak to startuje to gridpane(GameBoard) ucina całą pustą część
        // i lepi przez to przeciecia
        this.getChildren().add(new Rectangle(50,50, Color.valueOf("#524631"))); // #524631

        this.getChildren().add(new Rectangle(22,22, Color.valueOf("#ded9b6"))); // #ded9b6
        this.getChildren().add(new Rectangle(22,22, Color.valueOf("#ded9b6")));
        this.getChildren().add(new Rectangle(22,22, Color.valueOf("#ded9b6")));
        this.getChildren().add(new Rectangle(22,22, Color.valueOf("#ded9b6")));

        this.setAlignment(this.getChildren().get(0), javafx.geometry.Pos.CENTER);
        this.setAlignment(this.getChildren().get(1), Pos.TOP_LEFT);
        this.setAlignment(this.getChildren().get(2), Pos.TOP_RIGHT);
        this.setAlignment(this.getChildren().get(3), Pos.BOTTOM_LEFT);
        this.setAlignment(this.getChildren().get(4), Pos.BOTTOM_RIGHT);

        shadow = new Ellipse( 15,10);
        shadow.setFill(Color.valueOf("#000000"));
        shadow.setVisible(false);
        this.getChildren().add(shadow);
        //TEST

        this.addEventFilter(MouseEvent.MOUSE_ENTERED, eventHandler);
        this.addEventFilter(MouseEvent.MOUSE_EXITED, eventHandler2);
    }

    public void PlaceStone(Stone stone){
        this.stone = stone;
        this.getChildren().add(stone);
        stone.setCenterX(25);
        stone.setCenterY(25);
        //shadow.setRadiusX(13); // Dla efektu 3D chciałem zmniejszyc cien, ale wygladało kiepsko
        setMargin(shadow, new Insets(4,2,0,0));
    }

    EventHandler<MouseEvent> eventHandler = new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent e) {
            if(stone == null) {
                /*
                Ellipse shadow = new Ellipse( 15,10);
                shadow.setFill(Color.valueOf("#000000"));
                nodes.add(shadow);
                setMargin(shadow, new Insets(4,2,0,0));
                 */
                shadow.setVisible(true);
            }
        }
    };

    EventHandler<MouseEvent> eventHandler2 = new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent e) {
            if(stone==null) {
                shadow.setVisible(false);
            }
        }
    };
}