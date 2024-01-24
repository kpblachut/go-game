package org.example;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Hello world!
 *
 */
public class App extends Application
{
    public static void main( String[] args )
    {
        launch();
    }

    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("/FirstScreen.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Go Game");
        stage.sizeToScene();
        stage.setScene(scene);
        stage.setOnCloseRequest(e -> {
            Platform.exit();
        });
        stage.show();
    }
}
