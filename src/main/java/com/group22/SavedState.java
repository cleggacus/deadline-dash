package com.group22;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class SavedState {
    private String levelTitle;
    private ArrayList<Entity> entities;
    private LocalDateTime timeOfSave;
    private int score;
    private double time;
    private int levelIndex;

    public SavedState(String levelTitle, ArrayList<Entity> entities, int score, double time, LocalDateTime timeOfSave, int levelIndex){
        this.levelTitle = levelTitle;
        this.entities = entities;
        this.score = score;
        this.time = time;
        this.timeOfSave = timeOfSave;
        this.levelIndex = levelIndex;
    }

    public LocalDateTime getTimeOfSave(){
        return timeOfSave;
    }

    public String getLevelTitle(){
        return levelTitle;
    }

    public int getLevelIndex(){
        return levelIndex;
    }

    public ArrayList<Entity> getEntities(){
        return entities;
    }

    public int getScore(){
        return score;
    }

    public double getTime(){
        return time;
    }


}
