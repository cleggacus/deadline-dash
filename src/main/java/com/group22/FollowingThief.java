package com.group22;


/** 
 * 
 * The class FollowingThief extrends the Landmover class.
 * FollowingThief follows a set path around the game, interacting with 
 * any other entities in its path.
 */


public class FollowingThief extends LandMover {

    public int startX;
    public int startY;
    public TileColor pathColour;
    public int[] pathStart = {startX, startY};
    public int[][] path = {pathStart};
    //public int[][] path = {{5,1},{5,2},{5,3},{5,2},{5,1}};
    private int[] currentPos;
    private int[] nextPos;
    private int currentX;
    private int currentY;
    private int nextX;
    private int nextY;
    private int moveX;
    private int moveY;

    public FollowingThief(int posX, int posY, TileColor colour) {
        super(posX, posY);
        startX = posX;
        startY = posY;
        this.getSprite().setImage("NPC/FollowingThief.png");
        this.getSprite().setAnimationSpeed(0.1);
        this.setSpriteOffset(0, -0.3);
        this.moveEvery = 0.3;
    }

    public void setPathColour(TileColor colour){
        pathColour = colour;
    }

    public TileColor getPathColour() {
        return pathColour;
    }

   
    // /** 
    //  * @param x
    //  * @param y
    //  * @param type
    //  */
    // @Override 
    // protected void move(int x, int y, AnimationType type) {
        
    // }

    


    @Override
    protected void updateMovement() {
        this.move((int)Math.floor(Math.random()*3-1), 0, AnimationType.Bob);
    }

    @Override
    protected void update() {
        // TODO Auto-generated method stub
        
    }
    
}
