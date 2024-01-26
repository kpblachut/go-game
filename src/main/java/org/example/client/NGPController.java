package org.example.client;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;

public class NGPController { // New Game PopUp

    ObservableList<Integer> choices = FXCollections.observableArrayList(9,13,19);

    @FXML
    private Button CGButton;

    @FXML
    private ChoiceBox<Integer> SizeChoice;

    public Button getCGButton() {
        return CGButton;
    }

    public ChoiceBox<Integer> getSizeChoice() {
        return SizeChoice;
    }

    @FXML
    void initialize() {
        SizeChoice.setItems(choices);
        SizeChoice.setValue(19);
    }

}
