package com.group22;

import java.io.File;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;

/**
 * A music player that can set tracks and play tracks and loop audio.
 * @author Lewis Meekings
 * @version 1.1
 */
public class MusicManager {
    /**
     * The games media player and track.
     */
    private static MediaPlayer audio;
    private static Media track;

    /**
     * Creates the media track from file.
     * @param file The file
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

    /**
     * Sets the audio to loop
     */
    public static void playOnRepeat() {
        if (audio != null) {
            audio.stop();
        }

        audio = new MediaPlayer(track);
        audio.setOnEndOfMedia(new Runnable() {
            public void run() {
                audio.seek(Duration.ZERO);
            }
        });
        audio.play();
    }
}
