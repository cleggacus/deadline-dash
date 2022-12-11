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
     * Creates the media track by extracting the file path from string.
     * @param s The path name of the file
     */
    public static void setTrack(File file) {
        MusicManager.track = new Media(file.toURI().toString());
    }

    /**
     * Plays a selected music track.
     */
    public static void playTrack() {
        if (audio != null) {
            audio.stop();
        }

        audio = new MediaPlayer(track);
        audio.play();
    }
}
