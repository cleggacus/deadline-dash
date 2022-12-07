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
    public TileColor pathColor;
    private Object movingDirection = "right";
    private Boolean clockwise;

    

    public FollowingThief(int posX, int posY, TileColor color) { //, Boolean clockwise) {
        super(posX, posY);
        startX = posX;
        startY = posY;
        //this.clockwise = clockwise;
        
        //nextMove();
        this.getSprite().setImage("NPC/FollowingThief.png");
        this.getSprite().setAnimationSpeed(0.1);
        this.setSpriteOffset(0, -0.3);
        this.moveEvery = 0.3;
        
    }

    public void setPathColor(TileColor color){
        pathColor = color;
    }

    public TileColor getPathColor() {
        return pathColor;
    }

    public void nextMove(){
        if (movingDirection.equals("right")) {
            if (((nextUp() != this.getY()) && clockwise) || ((nextRight() == this.getX() && !clockwise))) {
                this.move(0,1, TransitionType.Bob);
                movingDirection = "up";
            } else if (((nextDown() != this.getY()) && !clockwise) || ((nextRight() == this.getX()) && clockwise)) {
                this.move(0,-1, TransitionType.Bob);
                movingDirection = "down";
            } else if (nextRight() != this.getX()) {
                this.move(1,0, TransitionType.Bob);
                movingDirection = "right";
            } else {
                clockwise = !clockwise;
                this.move(-1,0, TransitionType.Bob);
                movingDirection = "left";
            }
        }

        if (movingDirection.equals("left")) {
            if (((nextDown() != this.getY()) && clockwise) || ((nextLeft() == this.getX() && !clockwise))) {
                this.move(0,-1, TransitionType.Bob);
                movingDirection = "down";
            } else if (((nextUp() != this.getY()) && !clockwise) || ((nextLeft() == this.getX()) && clockwise)) {
                this.move(0,1, TransitionType.Bob);
                movingDirection = "up";
            } else if (nextLeft() != this.getX()) {
                this.move(-1,0, TransitionType.Bob);
                movingDirection = "left";
            } else {
                clockwise = !clockwise;
                this.move(-1,0, TransitionType.Bob);
                movingDirection = "right";
            }
        }

        if (movingDirection.equals("up")) {
            if (((nextLeft() != this.getX()) && clockwise) || ((nextUp() == this.getY() && !clockwise))) {
                this.move(-1,0, TransitionType.Bob);
                movingDirection = "left";
            } else if (((nextRight() != this.getX()) && !clockwise) || ((nextUp() == this.getY()) && clockwise)) {
                this.move(1,0, TransitionType.Bob);
                movingDirection = "right";
            } else if (nextUp() != this.getX()) {
                this.move(0,1, TransitionType.Bob);
                movingDirection = "up";
            } else {
                clockwise = !clockwise;
                this.move(0,-1, TransitionType.Bob);
                movingDirection = "down";
            }
        }

        if (movingDirection.equals("down")) {
            if (((nextRight() != this.getX()) && clockwise) || ((nextDown() == this.getY() && !clockwise))) {
                this.move(1,0, TransitionType.Bob);
                movingDirection = "right";
            } else if (((nextLeft() != this.getX()) && !clockwise) || ((nextDown() == this.getY()) && clockwise)) {
                this.move(-1,0, TransitionType.Bob);
                movingDirection = "left";
            } else if (nextDown() != this.getX()) {
                this.move(0,-1, TransitionType.Bob);
                movingDirection = "down";
            } else {
                clockwise = !clockwise;
                this.move(0,1, TransitionType.Bob);
                movingDirection = "up";
            }
        }
    
        
    }

    public Boolean hasNextUp(){
        return nextUp() != this.getY() &&
            Game.getInstance().tileHasColor(this.getX(), nextUp(), pathColor);
    }

    public Boolean hasNextDown(){
        return nextUp() != this.getY() &&
            Game.getInstance().tileHasColor(this.getX(), nextDown(), pathColor);

    }

    public Boolean hasNextLeft(){
        return nextUp() != this.getY() &&
            Game.getInstance().tileHasColor(nextLeft(), this.getY(), pathColor);
    }

    public Boolean hasNextRight(){
        return nextUp() != this.getY() &&
            Game.getInstance().tileHasColor(nextRight(), this.getY(), pathColor);
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
        nextMove();
    }

    @Override
    protected void update() {
        // TODO Auto-generated method stub
        
    }
    
}
