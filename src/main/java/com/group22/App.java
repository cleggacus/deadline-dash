package com.group22;

import javafx.application.Application;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.stage.Stage;
import java.io.File;
import java.io.IOException;

/**
 * The {@code App} class extends the JavaFX application and is the 
 * entry point for the game containing main method.
 * 
 * @author Liam Clegg
 * @version 1.0
 */
public class App extends Application {

    /** 
     * Is run when the application start and takes the primary stage.
     * 
     * @param stage The primary stage for the application.
     * @throws IOException if something fails.
     */
    @Override
    public void start(Stage stage) throws IOException {
        // Initialized game scene
        stage.setScene(Game.getInstance().createScene());

        // Sets window title and icon
        stage.setTitle("Deadline Dash");
        stage.getIcons().add(new Image(getClass().getResource(
            "/com/group22/icon.png").toExternalForm()));

        // Handles fullscreen
        stage.setFullScreenExitHint("Press F to exit fullscreen.");;
        stage.setFullScreenExitKeyCombination(
            new KeyCodeCombination(KeyCode.F));

        stage.show();
    }

    
    /** 
     * Main method launches app.
     * 
     * @param args not used.
     */
    public static void main(String[] args) {
        launch();
    }

}