package com.group22;

/**
 * 
 * The class {@code FlyingAssassin} impliments an entity which moves across the board in a straight line regardless of the tiles. 
 * Turns 180 degrees if it hits a wall.
 * If it occupies same space as player then the player loses
 * 
 * @author Ezana Tareke
 * @version 1.0
 */
public class FlyingAssassin extends Entity {

    public FlyingAssassin(int x, int y, Sprite flyingAssassinSprite) {
        super(x, y, flyingAssassinSprite);

    }

    /** 
     * Gives the class the path to follow
     * 
     * @param vector
     *      The direction which the class will move along
     * 
     * @param x
     *      The starting x coordinate
     *
     * @param y
     *       The starting y coordinate
     *
     */

     public void move(int x, int y, int[][] vector) {

            // starts at X,Y
            // moves along vector in straight line


     }

    /**
    * Reverse the direction the FlyingAssassin is moving
    *
    * @param vector
    *      The new direction which the class will move along
     */ 

    public void reverseVector(int[][] vector) {

        // if flyingAssassin reaches end of map
        // then direction will change 180 degrees

        // when flyingAssassin is not in bounds
        //    flyingAssassin rotates 180 degrees

    }



    @Override
    protected void updateMovement() {
        // TODO Auto-generated method stub
        
    }

    @Override
    protected void update() {
        // TODO Auto-generated method stub
        
    }}