package com.group22;

import java.io.File;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

public class MusicManager {
    private static MediaPlayer audio;
    private static Media track;

    public MusicManager(MediaPlayer audio){
        MusicManager.audio = audio;
    }

    public static void setTrack(String s){
        File musicFile = new File(s);
        MusicManager.track = new Media(musicFile.toURI().toString());
    }

    public static void playTrack(){
        audio.stop();
        audio = new MediaPlayer(track);
        audio.play();
    }
}
