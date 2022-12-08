package com.group22;


/** 
 * 
 * The class FollowingThief extrends the Landmover class.
 * FollowingThief follows a set path around the game, interacting with 
 * any other entities in its path.
 */


public class FollowingThief extends LandMover {
    public TileColor pathColor;
    private String movingDirection = "down";
    private Boolean clockwise;

    public FollowingThief(int posX, int posY, TileColor color, Boolean clockwise) {
        super(posX, posY);
        this.clockwise = clockwise;
        this.pathColor = color;        
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
            if ((hasNextUp() && clockwise) || ((!hasNextRight() && hasNextUp() && !clockwise))) {
                this.move(0,-1, TransitionType.Bob);
                movingDirection = "up";
            } else if ((hasNextDown() && !clockwise) || (!hasNextRight() && hasNextDown() && clockwise)) {
                this.move(0,1, TransitionType.Bob);
                movingDirection = "down";
            } else if (hasNextRight()) {
                this.move(1,0, TransitionType.Bob);
                movingDirection = "right";
            } else {
                clockwise = !clockwise;
                this.move(-1,0, TransitionType.Bob);
                movingDirection = "left";
            }
        } 


        if (movingDirection.equals("left")) {
            if ((hasNextDown() && clockwise) || ((!hasNextLeft() && hasNextDown() && !clockwise))) {
                this.move(0,1, TransitionType.Bob);
                movingDirection = "down";
            } else if ((hasNextUp() && !clockwise) || (!hasNextLeft() && hasNextUp() && clockwise)) {
                this.move(0,-1, TransitionType.Bob);
                movingDirection = "up";
            } else if (hasNextLeft()) {
                this.move(-1,0, TransitionType.Bob);
                movingDirection = "left";
            } else {
                clockwise = !clockwise;
                this.move(1,0, TransitionType.Bob);
                movingDirection = "right";
            }
        }

        if (movingDirection.equals("up")) {
            if ((hasNextLeft() && clockwise) || ((!hasNextUp() && hasNextLeft() && !clockwise))) {
                this.move(-1,0, TransitionType.Bob);
                movingDirection = "left";
            } else if ((hasNextRight() && !clockwise) || ((!hasNextUp() && hasNextRight() && clockwise))) {
                this.move(1,0, TransitionType.Bob);
                movingDirection = "right";
            } else if (hasNextUp()) {
                this.move(0,-1, TransitionType.Bob);
                movingDirection = "up";
            } else {
                clockwise = !clockwise;
                this.move(0,1, TransitionType.Bob);
                movingDirection = "down";
            }
        }

        if (movingDirection.equals("down")) {
            if ((hasNextRight() && clockwise) || (!hasNextDown() && hasNextRight() && !clockwise)) {
                this.move(1,0, TransitionType.Bob);
                movingDirection = "right";
            } else if ((hasNextLeft() && !clockwise) || (!hasNextDown() && hasNextLeft()&& clockwise)) {
                this.move(-1,0, TransitionType.Bob);
                movingDirection = "left";
            } else if (hasNextDown()) {
                this.move(0,1, TransitionType.Bob);
                movingDirection = "down";
            } else {
                clockwise = !clockwise;
                this.move(0,-1, TransitionType.Bob);
                movingDirection = "up";
            }
        }  
          
    }

    public Boolean hasNextUp(){
        //System.out.println(Game.getInstance().tileHasColor(this.getX(), nextUp(), pathColor));
        return nextUp() < this.getY() && !isBlocked(this.getX(), nextUp()) &&
            Game.getInstance().tileHasColor(this.getX(), nextUp(), pathColor);
    }

    public Boolean hasNextDown(){
        return nextDown() > this.getY() && !isBlocked(this.getX(), nextDown()) &&
            Game.getInstance().tileHasColor(this.getX(), nextDown(), pathColor);

    }

    public Boolean hasNextLeft(){
        return nextLeft() < this.getX() && !isBlocked(nextLeft(), this.getY()) &&
            Game.getInstance().tileHasColor(nextLeft(), this.getY(), pathColor);
    }

    public Boolean hasNextRight(){
        return nextRight() > this.getX() && !isBlocked(nextRight(), this.getY()) &&
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
        System.out.println(movingDirection);
    }

    @Override
    protected void update() {
        // TODO Auto-generated method stub
        
    }
    
}
