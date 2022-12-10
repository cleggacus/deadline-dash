package com.group22;

import java.time.LocalDateTime;
import java.util.ArrayList;

/**
 * 
 */
public class SavedState {
    private String levelTitle;
    private ArrayList<Entity> entities;
    private LocalDateTime timeOfSave;
    private int score;
    private double time;
    private int levelIndex;

    /**
     * 
     * @param levelTitle
     * @param entities
     * @param score
     * @param time
     * @param timeOfSave
     * @param levelIndex
     */
    public SavedState(String levelTitle, ArrayList<Entity> entities, 
        int score, double time, LocalDateTime timeOfSave, int levelIndex) {
        this.levelTitle = levelTitle;
        this.entities = entities;
        this.score = score;
        this.time = time;
        this.timeOfSave = timeOfSave;
        this.levelIndex = levelIndex;
    }

    
    /** 
     * @return LocalDateTime
     */
    public LocalDateTime getTimeOfSave() {
        return timeOfSave;
    }

    
    /** 
     * @return String
     */
    public String getLevelTitle() {
        return levelTitle;
    }

    
    /** 
     * @return int
     */
    public int getLevelIndex() {
        return levelIndex;
    }

    
    /** 
     * @return {@link ArrayList}{@link Entity}
     */
    public ArrayList<Entity> getEntities() {
        return entities;
    }

    
    /** 
     * @return int
     */
    public int getScore() {
        return score;
    }

    
    /** 
     * @return double
     */
    public double getTime() {
        return time;
    }
}
