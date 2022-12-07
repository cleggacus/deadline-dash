package com.group22;

public class ReplayFrame {
    private int x;
    private int y;
    private double keyTime;

    public ReplayFrame(int x, int y, double keyTime){
        this.x = x;
        this.y = y;
        this.keyTime = keyTime;
    }

    public double getKeyTime(){
        return this.keyTime;
    }

    public int getX(){
        return this.x;
    }

    public int getY(){
        return this.y;
    }

}
