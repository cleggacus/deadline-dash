package com.group22;

import javafx.application.Application;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.stage.Stage;

import java.io.IOException;

public class App extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        stage.setScene(Game.getInstance().createScene());
        stage.setTitle("Deadline Dash");
        stage.getIcons().add(new Image(getClass().getResource("/com/group22/icon.png").toExternalForm()));
        stage.setFullScreenExitHint("Press F to exit fullscreen.");;
        stage.setFullScreenExitKeyCombination(new KeyCodeCombination(KeyCode.F));
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }

}