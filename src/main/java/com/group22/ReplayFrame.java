package com.group22;

import javafx.scene.input.KeyCode;

public class ReplayFrame {
    private double timeOfFrame;
    private KeyCode playerInput;

    public ReplayFrame(double timeOfFrame, KeyCode playerInput){
        this.timeOfFrame = timeOfFrame;
        this.playerInput = playerInput;
    }

    public double getTimeOfFrame(){
        return this.timeOfFrame;
    }

    public KeyCode getPlayerInput(){
        return this.playerInput;
    }
}
