package org.example.client;

import javafx.collections.FXCollections;
import javafx.collections.ObservableArray;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;

public class NGPController { // New Game PopUp

    ObservableList<Integer> choices = FXCollections.observableArrayList(9,13,19);
    ObservableList<String> colors = FXCollections.observableArrayList("BLK", "WHT", "RND");

    @FXML
    private Button CGButton;

    @FXML
    private ChoiceBox<Integer> SizeChoice;

    @FXML
    private ChoiceBox<String> ColorCBox;


    public Button getCGButton() {
        return CGButton;
    }

    public ChoiceBox<Integer> getSizeChoice() {
        return SizeChoice;
    }
    public ChoiceBox<String> getColorCBox() {return ColorCBox;}
    public int getCVal(){
        int ret;
        if(ColorCBox.getValue().equals("WHITE")) {
            ret = 1;
        } else if (ColorCBox.getValue().equals("BLACK")) {
            ret = 2;
        } else {
            ret = 0;
        }
        return ret;
    }
    @FXML
    void initialize() {
        SizeChoice.setItems(choices);
        SizeChoice.setValue(19);
        ColorCBox.setItems(colors);
        ColorCBox.setValue("BLK");
    }

}
