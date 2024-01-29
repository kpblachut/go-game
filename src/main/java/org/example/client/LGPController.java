package org.example.client;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;

public class LGPController {

    FileChooser fileChooser;
    File selectedFile;

    @FXML
    private Label FNLabel;

    @FXML
    private Button LoadButton;

    @FXML
    private Button OPEButton;

    @FXML
    void OpenFiles(ActionEvent event) {

        selectedFile = fileChooser.showOpenDialog(new Stage());
        if(selectedFile != null) {
            Platform.runLater(() -> {
                FNLabel.setText(selectedFile.getName());
            });
        }
    }

    public Button getLoadButton() {
        return LoadButton;
    }

    public Button getOPEButton() {
        return OPEButton;
    }

    public Label getFNLabel() {
        return FNLabel;
    }

    @FXML
    void initialize(){
        fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Text Files", "*.txt")
                ,new FileChooser.ExtensionFilter("HTML Files", "*.htm")
                ,new FileChooser.ExtensionFilter("JSON Files", "*.json")
        );
    }

    public File getFile(){
        return selectedFile;
    }
}
