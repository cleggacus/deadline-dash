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
    private String loopDirection = "clockwise";
    private String checkDirection;
    private int[] moveCoords = new int[2];

    

    public FollowingThief(int posX, int posY, TileColor color) {
        super(posX, posY);
        startX = posX;
        startY = posY;
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

// change tempX/Y to field not local. Work out why its breaking: "this.tiles is null"
    public void nextMove(){
        if (loopDirection.equals("clockwise")) {
            if (nextUp() != this.getY()) {
                int tempY = nextUp();
                int tempX = 0;
                if (isMoveLegal(tempX, tempY)) {
                    moveCoords[0] = tempX;
                    moveCoords[1] = tempY;
                    checkDirection = "left";
                }  
            } else if (nextRight() != this.getX()) {
                int tempY = 0;
                int tempX = nextRight();
                if (isMoveLegal(tempX, tempY)) {
                    moveCoords[0] = tempX;
                    moveCoords[1] = tempY;
                    checkDirection = "up";
                }
            } else if (nextDown() != this.getY()) {
                int tempY = nextDown();
                int tempX = 0;
                if (isMoveLegal(tempX, tempY)) {
                    moveCoords[0] = tempX;
                    moveCoords[1] = tempY;
                    checkDirection = "right";
                }
            } else if (nextRight() != this.getX()) {
                int tempY = 0;
                int tempX = nextRight();
                if (isMoveLegal(tempX, tempY)) {
                    moveCoords[0] = tempX;
                    moveCoords[1] = tempY;
                    checkDirection = "down";
                }
            }
        }
    }

    public Boolean hasNextUp(){
        return nextUp() != this.getY() &&
            Game.getInstance().tileHasColor(this.getX(), nextUp(), pathColor);
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
        this.move((int)Math.floor(Math.random()*3-1), 0, TransitionType.Bob);
    }

    @Override
    protected void update() {
        // TODO Auto-generated method stub
        
    }
    
}
