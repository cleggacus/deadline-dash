package com.group22;

import java.time.LocalDateTime;
import java.util.ArrayList;

/**
 * The {@code SavedState} class represents an instance of a saved game.
 * 
 * @author Sam Austin
 * @version 1.1
 */
public class SavedState {
    private Level level;
    private ArrayList<Entity> entities;
    private LocalDateTime timeOfSave;
    private int score;
    private double time;

    /**
     * Creates a SavedState using the following parameters
     * @param level The {@link Level}
     * @param entities  The {@link Entity} data in an {@link ArrayList}
     * @param score The score at save
     * @param time The time remaining at save
     * @param timeOfSave The time the save was made
     */
    public SavedState(Level level, ArrayList<Entity> entities, 
        int score, double time, LocalDateTime timeOfSave) {
        this.level = level;
        this.entities = entities;
        this.score = score;
        this.time = time;
        this.timeOfSave = timeOfSave;
    }

    
    /** 
     * Getter for the time the save was made
     * @return LocalDateTime
     */
    public LocalDateTime getTimeOfSave() {
        return timeOfSave;
    }

    
    /** 
     * Getter for the level title
     * @return String
     */
    public String getLevelTitle() {
        return this.level.getTitle();
    }

    
    /** 
     * Getter for the level index
     * @return int
     */
    public int getLevelIndex() {
        return this.level.getIndex();
    }

    
    /** 
     * Getter for the entities at the time of save
     * @return {@link ArrayList}{@link Entity}
     */
    public ArrayList<Entity> getEntities() {
        return entities;
    }

    
    /** 
     * Getter for the score at the time of save
     * @return int
     */
    public int getScore() {
        return score;
    }

    
    /** 
     * Getter for the time remaining at the time of save
     * @return double
     */
    public double getTime() {
        return time;
    }
}
