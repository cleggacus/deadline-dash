package com.group22;

import java.util.ArrayList;

/**
 * 
 * The class {@code LandMover} impliments movement that follows the game tile colors.
 * 
 * @author Liam Clegg
 * @version 1.0
 */
public abstract class LandMover extends Entity {

    /**
     * Creates LandMover at 0, 0
     */
    public LandMover() {
        super();
    }

    /**
     * Creates a LandMover at position x, y.
     * 
     * @param x
     *      X position to initialize entity at.
     * @param y
     *      Y position to initialize entity at.
     */
    public LandMover(int x, int y) {
        super(x, y);
    }
    
    /** 
     * Moves entity by (x, y) according to the tile colors
     * 
     * @param x
     *      The x next tile in the given direction.
     * 
     * @param y
     *      The y next tile in the given direction.
     */
    @Override
    protected void move(int x, int y, TransitionType type) {
        int newX = this.getX();
        int newY = this.getY();

        while(x > 0) {
            newX = nextRight();
            x--;
        }

        while(x < 0) {
            newX = nextLeft();
            x++;
        }

        if(newX == this.getX()) {
            while(y > 0) {
                newY = nextDown();
                y--;
            }

            while(y < 0) {
                newY = nextUp();
                y++;
            }
        }

        ArrayList<LandMover> landMovers = Game.getInstance().getEntities(LandMover.class);
        ArrayList<Loot> loots = Game.getInstance().getEntities(Loot.class);
        boolean willUpdate = true;



        for(LandMover landMover : landMovers) {
            if(newX == landMover.getX() && newY == landMover.getY()) {
                willUpdate = false;
            }
        }

        if(willUpdate) {
            int moveX = newX - this.getX();
            int moveY = newY - this.getY();


            if(Math.abs(moveY) > 1 || Math.abs(moveX) > 1) {
                super.move(moveX, moveY, TransitionType.Scale);
            } else {
                super.move(moveX, moveY, type);
            }
        }

    }

    /**
     * Returns whether move (x, y) is legal according to tile colors.
     * 
     * @param x
     *      Change is x from current position to check.
     * 
     * @param y
     *      Change is y from current position to check.
     * 
     * @return
     *      If the x and y added to the current position is a valid colour.
     */
    protected boolean isMoveLegal(int x, int y) {
        if(!Game.getInstance().isInBounds(this.getX() + x, this.getY() + y))
            return false;

        return Game.getInstance().colorMatch(this.getX(), this.getY(), this.getX() + x, this.getY() + y);
    }

    /**
     * Gets the next tile with the correct color above the current tile.
     * 
     * @return
     *      The y position of the tile found.
     *      Returns current y position if not found.
     */
    protected int nextUp() {
        boolean found = false;
        int i = this.getY();

        while(i > 0 && !found) {
            i--;
            found = isMoveLegal(0, i - this.getY());
        }

        if(!found)
            i = this.getY();
        
        return i;
    }

    /**
     * Gets the next tile with the correct color below the current tile.
     * 
     * @return
     *      The y position of the tile found.
     *      Returns current y position if not found.
     */
    protected int nextDown() {
        boolean found = false;
        int i = this.getY();
        int height = Game.getInstance().getViewHeight();

        while(i < height-1 && !found) {
            i++;
            found = isMoveLegal(0, i - this.getY());
        }

        if(!found)
            i = this.getY();
        
        return i;
    }

    /**
     * Gets the next tile with the correct color to the left of the current tile.
     * 
     * @return
     *      The x position of the tile found.
     *      Returns current x position if not found.
     */
    protected int nextLeft() {
        boolean found = false;
        int i = this.getX();

        while(i > 0 && !found) {
            i--;
            found = isMoveLegal(i - this.getX(), 0);
        }

        if(!found)
            i = this.getX();
        
        return i;
    }


    /**
     * Gets the next tile with the correct color to the right of the current tile.
     * 
     * @return
     *      The x position of the tile found.
     *      Returns current x position if not found.
     */
    protected int nextRight() {
        boolean found = false;
        int i = this.getX();
        int width = Game.getInstance().getViewWidth();

        while(i < width-1 && !found) {
            i++;
            found = isMoveLegal(i - this.getX(), 0);
        }

        if(!found)
            i = this.getX();
        
        return i;
    }

}
