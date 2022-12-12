package com.group22;

import java.time.LocalDateTime;
import java.util.ArrayList;

/**
 * The {@code Replay} class represents an instance of a replay.
 * 
 * @author Sam Austin
 * @version 1.1
 */
public class Replay {
    private String levelName;
    private String username;
    private LocalDateTime timeOfSave;
    private ArrayList<ReplayFrame> frames;
    private int score;

    /**
     * Create a replay with the given data
     * @param levelName the level name as a String
     * @param username the logged in user's username as a String
     * @param timeOfSave the time the replay was finished as a LocalDateTime
     * @param frames    the frames of the replay as an ArrayList
     * @param score     the score the user achieved as an int
     */
    public Replay(
        String levelName,
        String username,
        LocalDateTime timeOfSave,
        ArrayList<ReplayFrame> frames,
        int score
     ) {
        this.levelName = levelName;
        this.username = username;
        this.timeOfSave = timeOfSave;
        this.frames = frames;
        this.score = score;
    }

    /**
     * Create a replay from a level name and user's username
     * @param levelName the level  name as a String
     * @param username the logged in user's username as a String
     */
    public Replay(String levelName, String username) {
        this.levelName = levelName;
        this.username = username;
        this.frames = new ArrayList<ReplayFrame>();
    }
    

    /** 
     * Getter for the score of the replay
     * @return int
     */
    public int getScore() {
        return score;
    }
    

    /** 
     * Setter for the score of the replay
     * @param score integer value of the score
     */
    public void setScore(int score) {
        this.score = score;
    }
    

    /** 
     * Getter for the user who created the replay
     * @return String
     */
    public String getUsername() {
        return this.username;
    }
    

    /** 
     * Getter for the time the replay was made
     * @return LocalDateTime
     */
    public LocalDateTime getTimeOfSave() {
        return this.timeOfSave;
    }
    

    /** 
     * Getter for the level name the replay was made on
     * @return String
     */
    public String getLevelName() {
        return this.levelName;
    }
    

    /** 
     * Getter which returns an {@link ArrayList} of all {@link ReplayFrame}'s
     * @return {@link ArrayList}{@link ReplayFrame}
     */
    public ArrayList<ReplayFrame> getFrames() {
        return this.frames;
    }
    
    
    /** 
     * Saves a {@link ReplayFrame} to the Replay
     * @param frame
     */
    public void storeFrame(ReplayFrame frame) {
        this.frames.add(frame);
    }
    
}
