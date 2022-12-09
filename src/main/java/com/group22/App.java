package com.group22;

import javafx.application.Application;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;

public class App extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        File musicFile = new File("src/main/resources/com/group22/music/ramranch.mp3");
        Media track = new Media(musicFile.toURI().toString());
        MediaPlayer mediaPlayer = new MediaPlayer(track);
        stage.setScene(Game.getInstance().createScene());
        stage.setTitle("Deadline Dash");
        stage.getIcons().add(new Image(getClass().getResource("/com/group22/icon.png").toExternalForm()));
        stage.setFullScreenExitHint("Press F to exit fullscreen.");;
        stage.setFullScreenExitKeyCombination(new KeyCodeCombination(KeyCode.F));

        stage.show();
        MusicManager musicPlayer = new MusicManager(mediaPlayer);
        MusicManager.setTrack("src/main/resources/com/group22/music/ramranch.mp3");
        MusicManager.playTrack();
    }

    public static void main(String[] args) {
        launch();
    }

}