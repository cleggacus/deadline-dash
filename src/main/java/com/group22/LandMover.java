package com.group22;

import java.util.ArrayList;

/**
 * 
 * The class {@code LandMover} impliments 
 * movement that follows the game tile colors.
 * 
 * @author Liam Clegg
 * @version 1.1
 */
public abstract class LandMover extends Entity {
    @SuppressWarnings("unchecked")
    private static final Class<? extends Entity>[] COLLIDERS = new Class[] {
        LandMover.class,  
        Bomb.class,
        Gate.class
    };


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
        int newX = this.getMovedX(x);
        int newY = this.getMovedY(y);

        if (!isBlocked(newX, newY)) {
            int moveX = newX - this.getX();
            int moveY = newY - this.getY();


            if (Math.abs(moveY) > 1 || Math.abs(moveX) > 1) {
                super.move(moveX, moveY, TransitionType.SCALE);
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

    protected boolean isBlocked(int x, int y){
        for (Class<? extends Entity> entityClass : COLLIDERS) {
            ArrayList<? extends Entity> entities = 
                Game.getInstance().getEntities(entityClass);

            for (Entity entity : entities) {
                if (entity instanceof Gate && ((Gate) entity).getGateIsOpen()) {
                } else if (x ==  entity.getX() && y == entity.getY()) {
                    return true;
                }
            }
        }
        return false;
    }


    
    /** 
     * Checks if a move of x, y is a tile the entity can move to.
     * 
     * @param x check if you move by x
     * @param y check if you move by y
     * @return returns true if the move is legal
     */
    protected boolean isMoveLegal(int x, int y) {
        if (!Game.getInstance().isInBounds(this.getX() + x, this.getY() + y)) {
            return false;
        }

        Tile tile1 = Game.getInstance()
            .getTile(this.getX(), this.getY());

        Tile tile2 = Game.getInstance()
            .getTile(this.getX() + x, this.getY() + y);

        return tile1.colorMatch(tile2);
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

        while (i > 0 && !found) {
            i--;
            found = isMoveLegal(0, i - this.getY());
        }

        if (!found) {
            i = this.getY();
        }

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

        while (i < height - 1 && !found) {
            i++;
            found = isMoveLegal(0, i - this.getY());
        }

        if (!found) {
            i = this.getY();
        }
        
        return i;
    }

    /**
     * Gets the next tile with the correct 
     * color to the left of the current tile.
     * 
     * @return
     *      The x position of the tile found.
     *      Returns current x position if not found.
     */
    protected int nextLeft() {
        boolean found = false;
        int i = this.getX();

        while (i > 0 && !found) {
            i--;
            found = isMoveLegal(i - this.getX(), 0);
        }

        if (!found) {
            i = this.getX();
        }

        return i;
    }


    /**
     * Gets the next tile with the correct 
     * color to the right of the current tile.
     * 
     * @return
     *      The x position of the tile found.
     *      Returns current x position if not found.
     */
    protected int nextRight() {
        boolean found = false;
        int i = this.getX();
        int width = Game.getInstance().getViewWidth();

        while (i < width - 1 && !found) {
            i++;
            found = isMoveLegal(i - this.getX(), 0);
        }

        if (!found) {
            i = this.getX();
        }

        return i;
    }

    
    /** 
     * Returns the x postion if the entity move by a given x value.
     * @param x postiion x value to move.
     * @return the result x position
     */
    private int getMovedX(int x) {
        int newX = this.getX();

        while (x > 0) {
            newX = nextRight();
            x--;
        }

        while (x < 0) {
            newX = nextLeft();
            x++;
        }

        return newX;
    }

    
    /** 
     * Returns the y postion if the entity move by a given y value.
     * @param y postiion y value to move.
     * @return the result y position
     */
    private int getMovedY(int y) {
        int newY = this.getY();

        while (y > 0) {
            newY = nextDown();
            y--;
        }

        while (y < 0) {
            newY = nextUp();
            y++;
        }

        return newY;
    }
}
