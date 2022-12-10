package com.group22;

/**
 * 
 */
public class ReplayFrame {
    private int x;
    private int y;
    private double keyTime;

    /**
     * 
     * @param x
     * @param y
     * @param keyTime
     */
    public ReplayFrame(int x, int y, double keyTime) {
        this.x = x;
        this.y = y;
        this.keyTime = keyTime;
    }


    /**
     * 
     * @return
     */
    public double getKeyTime() {
        return this.keyTime;
    }


    /**
     * 
     * @return
     */
    public int getX() {
        return this.x;
    }

    
    /**
     * 
     * @return
     */
    public int getY() {
        return this.y;
    }

}
