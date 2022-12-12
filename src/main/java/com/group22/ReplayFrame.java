package com.group22;

/**
 * The {@code ReplayFrame} class represents a point in time for
 * the replay with the x,y coordinates of the player and the time
 * of the frame in relation to the game time.
 * 
 * @author Sam Austin
 * @version 1.1
 */
public class ReplayFrame {
    private int x;
    private int y;
    private double keyTime;

    /**
     * Creates a ReplayFrame with the given parameters.
     * @param x x coordinate of the player
     * @param y y coordinate of the player
     * @param keyTime the time of the frame in relation to the game time
     */
    public ReplayFrame(int x, int y, double keyTime) {
        this.x = x;
        this.y = y;
        this.keyTime = keyTime;
    }


    /**
     * Getter for the time in relation to the game time of the frame
     * @return double
     */
    public double getKeyTime() {
        return this.keyTime;
    }


    /**
     * Getter for the x coordinate of the player
     * @return
     */
    public int getX() {
        return this.x;
    }

    
    /**
     * Getter for the y coordinate of the player
     * @return
     */
    public int getY() {
        return this.y;
    }

}
