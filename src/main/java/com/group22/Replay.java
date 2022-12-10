package com.group22;

import java.time.LocalDateTime;
import java.util.ArrayList;

/**
 * 
 */
public class Replay {
    private String levelName;
    private String username;
    private LocalDateTime timeOfSave;
    private ArrayList<ReplayFrame> frames;
    private int score;

    /**
     * 
     * @param levelName
     * @param username
     * @param timeOfSave
     * @param frames
     * @param score
     */
    public Replay(String levelName, String username, LocalDateTime timeOfSave, ArrayList<ReplayFrame> frames, int score) {
        this.levelName = levelName;
        this.username = username;
        this.timeOfSave = timeOfSave;
        this.frames = frames;
        this.score = score;
    }

    /**
     * 
     * @param levelName
     * @param username
     */
    public Replay(String levelName, String username) {
        this.levelName = levelName;
        this.username = username;
        this.frames = new ArrayList<ReplayFrame>();
    }
    

    /** 
     * @return int
     */
    public int getScore() {
        return score;
    }
    

    /** 
     * @param score
     */
    public void setScore(int score) {
        this.score = score;
    }
    

    /** 
     * @return String
     */
    public String getUsername() {
        return this.username;
    }
    

    /** 
     * @return LocalDateTime
     */
    public LocalDateTime getTimeOfSave() {
        return this.timeOfSave;
    }
    

    /** 
     * @return String
     */
    public String getLevelName() {
        return this.levelName;
    }
    

    /** 
     * @return {@link ArrayList}{@link ReplayFrame}
     */
    public ArrayList<ReplayFrame> getFrames() {
        return this.frames;
    }
    
    
    /** 
     * @param frame
     */
    public void storeFrame(ReplayFrame frame) {
        this.frames.add(frame);
    }
    
}
