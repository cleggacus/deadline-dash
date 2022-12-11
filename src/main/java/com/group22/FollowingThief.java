package com.group22;
/** 
 * The class {@code FollowingThief} extends the {@link Landmover} class.
 * {@code FollowingThief} follows a set path around the game, interacting with 
 * all other {@link entity} objects in its path.
 * 
 * @author Rhys McGuire
 * @version 1.0
 */
public class FollowingThief extends LandMover {
    private TileColor pathColor;
    private String movingDirection = "right";
    private Boolean clockwise;

    /**
     * Creates a new {@code followingThief} {@link entity}, using 
     * information from the {@code levels.txt} file.
     * @param posX The horizontal starting position.
     * @param posY The vertical starting position.
     * @param color The {@link TileColor} the {@code FollowingThief} follows.
     * @param clockwise The direction that the {@code FollowingThief} 
     *                  moves around the game.
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
     * Sets the color of the path from the {@code levels.txt} file info
     * @param color the colour of the path to follow.
     */
    public void setPathColor(TileColor color) {
        pathColor = color;
    }

    
    /** 
     * Gets the colour of the path to follow
     * @return The {@link TileColor} to follow
     */
    public TileColor getPathColor() {
        return pathColor;
    }

    /**
     * Calculates the next tile to move to, if the last move direction
     * was rightwards. Depends on if the {@code FollowingThief} is
     * moving round an anti/clockwise loop, and if there is a tile 
     * matching the required color in the required direction. 
     * If no valid move, the {@code FollowingThief} will reverse 
     * its loop direction.
     */
    public void goingRight() {
        if ((hasNextUp() && clockwise) || 
            ((!hasNextRight() && hasNextUp() && !clockwise))) {

            this.move(0,-1, TransitionType.BOB);
            movingDirection = "up";
        } else if ((hasNextDown() && !clockwise) || 
            (!hasNextRight() && hasNextDown() && clockwise)) {

            this.move(0,1, TransitionType.BOB);
            movingDirection = "down";
        } else if (hasNextRight()) {
            move(1,0, TransitionType.BOB);
            movingDirection = "right";
        } else {
            clockwise = !clockwise;
            move(-1,0, TransitionType.BOB);
            movingDirection = "left";
        }
    } 

    /**
     * Calculates the next tile to move to, if the last move direction
     * was leftwards. Depends on if the {@code FollowingThief} is
     * moving round an anti/clockwise loop, and if there is a tile 
     * matching the required color in the required direction. 
     * If no valid move, the {@code FollowingThief} will reverse 
     * its loop direction.
     */
    public void goingLeft() {
        if ((hasNextDown() && clockwise) || 
            ((!hasNextLeft() && hasNextDown() && !clockwise))) {

            this.move(0,1, TransitionType.BOB);
            movingDirection = "down";
        } else if ((hasNextUp() && !clockwise) || 
            (!hasNextLeft() && hasNextUp() && clockwise)) {

            this.move(0,-1, TransitionType.BOB);
            movingDirection = "up";
        } else if (hasNextLeft()) {
            this.move(-1,0, TransitionType.BOB);
            movingDirection = "left";
        } else {
            clockwise = !clockwise;
            this.move(1,0, TransitionType.BOB);
            movingDirection = "right";
        }
    }

    /**
     * Calculates the next tile to move to, if the last move direction
     * was upwards. Depends on if the {@code FollowingThief} is
     * moving round an anti/clockwise loop, and if there is a tile 
     * matching the required color in the required direction. 
     * If no valid move, the {@code FollowingThief} will reverse 
     * its loop direction.
     */
    public void goingUp() {
        if ((hasNextLeft() && clockwise) || 
            ((!hasNextUp() && hasNextLeft() && !clockwise))) {

            this.move(-1,0, TransitionType.BOB);
            movingDirection = "left";
        } else if ((hasNextRight() && !clockwise) || 
            ((!hasNextUp() && hasNextRight() && clockwise))) {

            this.move(1,0, TransitionType.BOB);
            movingDirection = "right";
        } else if (hasNextUp()) {
            this.move(0,-1, TransitionType.BOB);
            movingDirection = "up";
        } else {
            clockwise = !clockwise;
            this.move(0,1, TransitionType.BOB);
            movingDirection = "down";
        }
    }

    /**
     * Calculates the next tile to move to, if the last move direction
     * was downwards. Depends on if the {@code FollowingThief} is
     * moving round an anti/clockwise loop, and if there is a tile 
     * matching the required color in the required direction. 
     * If no valid move, the {@code FollowingThief} will reverse 
     * its loop direction.
     */
    public void goingDown() {
        if ((hasNextRight() && clockwise) || 
            (!hasNextDown() && hasNextRight() && !clockwise)) {

            this.move(1,0, TransitionType.BOB);
            movingDirection = "right";
        } else if ((hasNextLeft() && !clockwise) || 
            (!hasNextDown() && hasNextLeft() && clockwise)) {

            this.move(-1,0, TransitionType.BOB);
            movingDirection = "left";
        } else if (hasNextDown()) {
            this.move(0,1, TransitionType.BOB);
            movingDirection = "down";
        } else {
            clockwise = !clockwise;
            this.move(0,-1, TransitionType.BOB);
            movingDirection = "up";
        }
    }

    
    /** 
     * A method to check if the FollowingThief 
     * can move to the next tile on its path,
     * if the tile is in the upwards direction
     * @return Boolean
     */
    public Boolean hasNextUp() {
        return nextUp() < this.getY() && !isBlocked(this.getX(), nextUp()) &&
            Game.getInstance().getTile(this.getX(), nextUp())
                .hasColor(pathColor);
    }

    
    /** 
     * A method to check if the FollowingThief can 
     * move to the next tile on its path,
     * if the tile is in the downwards direction
     * @return Boolean
     */
    public Boolean hasNextDown() {
        return nextDown() > this.getY() && 
            !isBlocked(this.getX(), nextDown()) &&
            Game.getInstance().getTile(this.getX(), nextDown())
                .hasColor(pathColor);
    }

    
    /** 
     * A method to check if the FollowingThief 
     * can move to the next tile on its path,
     * if the tile is in the leftwards direction
     * @return Boolean
     */
    public Boolean hasNextLeft() {
        return nextLeft() < this.getX() && 
            !isBlocked(nextLeft(), this.getY()) &&
            Game.getInstance().getTile(nextLeft(), this.getY())
                .hasColor(pathColor);
    }

    
    /** 
     * 
     * A method to check if the FollowingThief 
     * can move to the next tile on its path,
     * if the tile is in the rightwards direction
     * @return Boolean
     */
    public Boolean hasNextRight() {
        return nextRight() > this.getX() && 
            !isBlocked(nextRight(), this.getY()) &&
            Game.getInstance().getTile(nextRight(), this.getY())
                .hasColor(pathColor);
    }
    
    
    /** 
     * returns a {@link String} containing the {@code FollowingThief}'s 
     * position, path color and loop direction.
     * @return {@link String}
     */
    @Override
    public String toString() {
        return ("followingthief " + getX() + " " + getY() + " " + 
            getPathColor().label + " " + String.valueOf(clockwise));
    }

    /**
     * Updates the movement of the {@code FollowingThief}.
     * An overridden method inherited from {@link Entity}.
     */
    @Override
    protected void updateMovement() {
        if (!(Game.getInstance().getTile(this.getX(), this.getY())
.hasColor(pathColor))){
            Game.getInstance().removeEntity(this);
        }
        if (!hasNextDown() && !hasNextLeft()
        && !hasNextRight() && !hasNextUp()){
        } else {
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
    }

    /**
     * Unused method, inherited from {@link Entity}.
     */
    @Override
    protected void update() {}
    
}
