package com.group22;

import javafx.application.Application;
import javafx.stage.Stage;

import java.io.IOException;

public class App extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        stage.setScene(Game.getInstance().createScene());
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }

}