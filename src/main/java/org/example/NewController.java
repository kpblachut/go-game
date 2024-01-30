package org.example;

import javafx.fxml.FXML;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.AnchorPane;

public class NewController {

    @FXML
    private MenuItem AboutItem;

    @FXML
    private AnchorPane GamePlace;

    @FXML
    private MenuItem GiveUpItem;

    @FXML
    private MenuItem LoadGameItem;

    @FXML
    private MenuItem JoinGameItem;

    @FXML
    private MenuItem NewGameItem;

    @FXML
    private MenuItem PassTurnItem;

    @FXML
    private MenuItem QuitItem;

    @FXML
    private MenuItem SaveGameItem;

    @FXML
    private MenuItem PlayWBItem;

    @FXML
    private MenuItem UndoItem;

    @FXML
    private MenuItem RedoItem;

    public AnchorPane getGamePlace() {
        return GamePlace;
    }

    public MenuItem getAboutItem() {
        return AboutItem;
    }

    public MenuItem getGiveUpItem() {
        return GiveUpItem;
    }

    public MenuItem getLoadGameItem() {
        return LoadGameItem;
    }

    public MenuItem getNewGameItem() {
        return NewGameItem;
    }

    public MenuItem getPassTurnItem() {
        return PassTurnItem;
    }

    public MenuItem getQuitItem() {
        return QuitItem;
    }

    public MenuItem getSaveGameItem() {
        return SaveGameItem;
    }

    public MenuItem getJoinGameItem() {
        return JoinGameItem;
    }

    public MenuItem getPlayWBItem() {
        return PlayWBItem;
    }

    public MenuItem getRedoItem() {
        return RedoItem;
    }

    public MenuItem getUndoItem() {
        return UndoItem;
    }
}
