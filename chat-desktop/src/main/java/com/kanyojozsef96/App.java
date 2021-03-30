package com.kanyojozsef96;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;


/**
 * JavaFX App
 */
public class App extends Application {
    private static Stage mainStage;

    @Override
    public void start(Stage stage) {
        App.mainStage = stage;
        loadFXML("/fxml/window_main.fxml", null, "Admin Application");
        stage.show();
    }

    public static FXMLLoader loadFXML(String fxmlURL, Stage optionalWindow, String title) {
        FXMLLoader loader = new FXMLLoader(App.class.getResource(fxmlURL));
        Scene scene = null;

        try {
            Parent root = loader.load();
            scene = new Scene(root);
        } catch (IOException e) {
            System.out.println("Cannot load fxml file, printing stacktrace...");
            e.printStackTrace();
        }

        if(Objects.nonNull(optionalWindow)) {
            optionalWindow.setScene(scene);
            optionalWindow.setTitle(title);
        } else {
            App.mainStage.setScene(scene);
            App.mainStage.setTitle(title);
        }

        return loader;
    }

    public static void main(String[] args) {
        launch();
    }

}