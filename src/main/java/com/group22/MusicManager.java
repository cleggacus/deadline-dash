package com.group22;

import java.io.File;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

/**
 * A music player that can set tracks and play tracks, maybe it will have more
 * complicated features like looping audio.
 * @author Lewis Meekings
 * @version 1.0
 */
public class MusicManager {
    /**
     * The games media player and track.
     */
    private static MediaPlayer audio;
    private static Media track;

    /**
     * Constructor for music manager takes a media player and then turns it into
     * the games media player
     * @param audio
     */
    public MusicManager(MediaPlayer audio){
        MusicManager.audio = audio;
    }

    /**
     * Creates the media track by extracting the file path from string.
     * @param s The path name of the file
     */
    public static void setTrack(String s){
        File musicFile = new File(s);
        MusicManager.track = new Media(musicFile.toURI().toString());
    }

    /**
     * Plays a selected music track.
     */
    public static void playTrack(){
        audio.stop();
        audio = new MediaPlayer(track);
        audio.play();
    }
}
