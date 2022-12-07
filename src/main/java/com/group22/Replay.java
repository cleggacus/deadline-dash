package com.group22;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class Replay {
    private String levelName;
    private String username;
    private LocalDateTime timeOfSave;
    private ArrayList<ReplayFrame> frames;
    private int score;

    public Replay(String levelName, String username, LocalDateTime timeOfSave, ArrayList<ReplayFrame> frames, int score){
        this.levelName = levelName;
        this.username = username;
        this.timeOfSave = timeOfSave;
        this.frames = frames;
        this.score = score;
    }

    public Replay(String levelName, String username){
        this.levelName = levelName;
        this.username = username;
        this.frames = new ArrayList<ReplayFrame>();
    }

    public int getScore(){
        return score;
    }

    public void setScore(int score){
        this.score = score;
    }

    public String getUsername(){
        return this.username;
    }

    public LocalDateTime getTimeOfSave(){
        return this.timeOfSave;
    }

    public String getLevelName(){
        return this.levelName;
    }

    public ArrayList<ReplayFrame> getFrames(){
        return this.frames;
    }
    
    public void storeFrame(ReplayFrame frame){
        this.frames.add(frame);
    }

    
}
