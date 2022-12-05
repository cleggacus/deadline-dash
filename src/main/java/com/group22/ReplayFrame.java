package com.group22;

import javafx.scene.input.KeyCode;

public class ReplayFrame {
    private double keyTime;
    private boolean keyDown;
    private KeyCode key;

    public ReplayFrame(KeyCode key, double keyTime, boolean keyDown){
        this.key = key;
        this.keyDown = keyDown;
        this.keyTime = keyTime;
    }

    public double getKeyTime(){
        return this.keyTime;
    }

    public KeyCode getKey(){
        return this.key;
    }

    public Boolean getKeyDown(){
        return this.keyDown;
    }

}
