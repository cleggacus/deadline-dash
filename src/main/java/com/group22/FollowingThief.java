package com.group22;
/** 
 * The class FollowingThief extends the Landmover class.
 * FollowingThief follows a set path around the game, interacting with 
 * any other entities in its path.
 */

public class FollowingThief extends LandMover {
    public TileColor pathColor;
    private String movingDirection = "down";
    private Boolean clockwise;

    /**
     * The constructor for the FollowingThief entity class.
     * @param posX The horizontal position of this entity
     * @param posY The vertical position of this entity
     * @param color The color of the tiles the entity follows
     * @param clockwise The direction that the entity moves arount the game
     * 
     */
    public FollowingThief(int posX, int posY, TileColor color, Boolean clockwise) {
        super(posX, posY);
        this.clockwise = clockwise;
        this.pathColor = color;        
        this.getSprite().setImage("NPC/FollowingThief.png");
        this.moveEvery = 0.3;
    }

    
    /** 
     * @param color
     */
    public void setPathColor(TileColor color){
        pathColor = color;
    }

    
    /** 
     * @return TileColor
     */
    public TileColor getPathColor() {
        return pathColor;
    }

     
    public void goingRight() {
        if ((hasNextUp() && clockwise) || ((!hasNextRight() && hasNextUp() && !clockwise))) {
            this.move(0,-1, TransitionType.Bob);
            if(!clockwise){
                movingDirection = "up";
            }
        } else if ((hasNextDown() && !clockwise) || (!hasNextRight() && hasNextDown() && clockwise)) {
            this.move(0,1, TransitionType.Bob);
            if(clockwise){
                movingDirection = "down";
            }
        } else if (hasNextRight()) {
            move(1,0, TransitionType.Bob);
            movingDirection = "right";
        } else {
            clockwise = !clockwise;
            move(-1,0, TransitionType.Bob);
            movingDirection = "left";
        }
    } 


    public void goingLeft() {
        if ((hasNextDown() && clockwise) || ((!hasNextLeft() && hasNextDown() && !clockwise))) {
            this.move(0,1, TransitionType.Bob);
            if(!clockwise){
                movingDirection = "down";
            }
        } else if ((hasNextUp() && !clockwise) || (!hasNextLeft() && hasNextUp() && clockwise)) {
            this.move(0,-1, TransitionType.Bob);
            if(clockwise){
                movingDirection = "up";
            }
        } else if (hasNextLeft()) {
            this.move(-1,0, TransitionType.Bob);
            movingDirection = "left";
        } else {
            clockwise = !clockwise;
            this.move(1,0, TransitionType.Bob);
            movingDirection = "right";
        }
    }

    public void goingUp() {
        if ((hasNextLeft() && clockwise) || ((!hasNextUp() && hasNextLeft() && !clockwise))) {
            this.move(-1,0, TransitionType.Bob);
            if(!clockwise){
                movingDirection = "left";
            }
        } else if ((hasNextRight() && !clockwise) || ((!hasNextUp() && hasNextRight() && clockwise))) {
            this.move(1,0, TransitionType.Bob);
            if(clockwise){
                movingDirection = "right";
            }
        } else if (hasNextUp()) {
            this.move(0,-1, TransitionType.Bob);
            movingDirection = "up";
        } else {
            clockwise = !clockwise;
            this.move(0,1, TransitionType.Bob);
            movingDirection = "down";
        }
    }

    public void goingDown() {
        if ((hasNextRight() && clockwise) || (!hasNextDown() && hasNextRight() && !clockwise)) {
            this.move(1,0, TransitionType.Bob);
            if(!clockwise){
                movingDirection = "right";
            }
        } else if ((hasNextLeft() && !clockwise) || (!hasNextDown() && hasNextLeft() && clockwise)) {
            this.move(-1,0, TransitionType.Bob);
            if(clockwise){
                movingDirection = "left";
            }
        } else if (hasNextDown()) {
            this.move(0,1, TransitionType.Bob);
            movingDirection = "down";
        } else {
            clockwise = !clockwise;
            this.move(0,-1, TransitionType.Bob);
            movingDirection = "up";
        }
    }

    
    /** 
     * A method to check if the FollowingThief can move to the next tile on its path,
     * if the tile is in the upwards direction
     * @return Boolean
     */
    public Boolean hasNextUp(){
        return nextUp() < this.getY() && !isBlocked(this.getX(), nextUp()) &&
            Game.getInstance().tileHasColor(this.getX(), nextUp(), pathColor);
    }

    
    /** 
     * A method to check if the FollowingThief can move to the next tile on its path,
     * if the tile is in the downwards direction
     * @return Boolean
     */
    public Boolean hasNextDown(){
        return nextDown() > this.getY() && !isBlocked(this.getX(), nextDown()) &&
            Game.getInstance().tileHasColor(this.getX(), nextDown(), pathColor);

    }

    
    /** 
     * A method to check if the FollowingThief can move to the next tile on its path,
     * if the tile is in the leftwards direction
     * @return Boolean
     */
    public Boolean hasNextLeft(){
        return nextLeft() < this.getX() && !isBlocked(nextLeft(), this.getY()) &&
            Game.getInstance().tileHasColor(nextLeft(), this.getY(), pathColor);
    }

    
    /** 
     * 
     * A method to check if the FollowingThief can move to the next tile on its path,
     * if the tile is in the rightwards direction
     * @return Boolean
     */
    public Boolean hasNextRight(){
        return nextRight() > this.getX() && !isBlocked(nextRight(), this.getY()) &&
            Game.getInstance().tileHasColor(nextRight(), this.getY(), pathColor);
    }
    
    @Override
    public String toString(){
        return ("followingthief " + getX() + " " + getY() + " " + getPathColor().label + " " + String.valueOf(clockwise));
    }

    @Override
    protected void updateMovement(){
        switch (movingDirection){
            case "right":
                goingRight();
            break;
            case "left":
                goingLeft();
            break;
            case "up":
                goingUp();
            break;
            case "down":
                goingDown();
            break;
        }
    }

    @Override
    protected void update() {}
    
}
