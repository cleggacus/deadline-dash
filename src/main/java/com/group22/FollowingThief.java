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
        createPath();
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

    public int[][] createPath(){

        addNextStepToPath(path, pathStart);

        int currentX = pathStart[0];
        int currentY = pathStart[1];
        int[] nextStep = new int[2];

        while (path[0] != path [path.length-1]) {
            if (nextUp() != currentY) {
                nextStep[0] = currentX; 
                nextStep[1] = nextUp();
                currentY = nextUp();
                addNextStepToPath(path,nextStep);
            } else if (nextLeft() != currentX) {
                nextStep[0] = nextLeft(); 
                nextStep[1] = currentY;
                currentX = nextLeft();
                addNextStepToPath(path,nextStep);
            } else if (nextDown() != currentY) {
                nextStep[0] = currentX; 
                nextStep[1] = nextDown();
                currentY = nextDown();
                addNextStepToPath(path,nextStep);
            } else if (nextRight() != currentX) {
                nextStep[0] = nextRight(); 
                nextStep[1] = currentY;
                currentX = nextRight();
                addNextStepToPath(path,nextStep);
            }
        }

        return path;

    }
    
    public static int[][] addNextStepToPath(int[][] path, int[] coords) {
        
        int[] nextStep = coords;
        int last = path.length;
        int[][] newPath = new int[last + 1][];

        for (int i=0; i<last; i++) {
            newPath[i] = path[i];
        }

        newPath[last] = nextStep;
        
        return newPath;

    }

    public void setNextMove(int[] currentPos, int[] nextPos){
        this.currentPos = currentPos;
        this.nextPos = nextPos;
        currentX = currentPos[0];
        currentY = currentPos[0]; 
        nextX = nextPos[0];
        nextY = nextPos[1];

        if (nextX == currentX) {
            moveX = 0;
        } else if (nextX < currentX) {
            moveX = -1;
        } else {
            moveX = 1;
        }

        if (nextY == currentY) {
            moveY = 0;
        } else if (nextY < currentY) {
            moveY = -1;
        } else {
            moveY = 1;
        }
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
