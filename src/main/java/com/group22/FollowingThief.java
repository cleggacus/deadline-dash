package com.group22;


/** 
 * 
 * The class { @code FollowingThief} extrends the Landmover class.
 * FollowingThief follows a set path around the game, interacting with 
 * any other entities in its path.
 */

import javafx.scene.paint.Color;

public class FollowingThief extends LandMover {


    public Color pathColour;
    public int[] pathStart;
    public int[][] path;

    public FollowingThief(int posX, int posY) {
        super(posX, posY);
    }

    public void setPath(int posX, int posY, Color colour){
        pathColour = colour;
        pathStart[0] = posX;
        pathStart[1] = posY;

        /**
         * create path by:
         * while spriteDirection == east {
         *      if nextUp == colour {
         *          nextUp.addToPath
         *      } else {
         *          turn Sprite 90 clockwise}
         * } 
         * 
         * while spriteDirection == south {
         *      if nextRight == colour {
         *          nextRight.addToPath
         *      } else {
         *          turn Sprite 90 clockwise}
         * }
         * 
         * while spriteDirection == west {
         *      if nextDown == colour {
         *          nextDown.addToPath
         *      } else {
         *          turn Sprite 90 clockwise}
         * } 
         * 
         * while spriteDirection == north {
         *      if nextLeft == colour {
         *          nextLeft.addToPath
         *      } else {
         *          turn Sprite 90 clockwise}
         * }
         */
    }
    
    /** 
     * @param x
     * @param y
     * @param type
     */
    @Override 
    protected void move(int x, int y, AnimationType type) {

    }

    


    @Override
    protected void updateMovement() {
        // TODO Auto-generated method stub
        
    }

    @Override
    protected void update() {
        // TODO Auto-generated method stub
        
    }
    
}
