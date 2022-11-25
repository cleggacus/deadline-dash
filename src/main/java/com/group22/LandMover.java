package com.group22;

/**
 * 
 * The class {@code LandMover} impliments movement that follows the game tile colors.
 * 
 * @author Liam Clegg
 * @version 1.0
 */
public abstract class LandMover extends Entity {
    
    /** 
     * Moves entity by (x, y) according to the tile colors
     * 
     * @param x
     * @param y
     */
    protected void move(int x, int y) {
        while(x > 0) {
            this.x = nextRight();
            x--;
        }

        while(x < 0) {
            this.x = nextLeft();
            x++;
        }

        while(y > 0) {
            this.y = nextDown();
            y--;
        }

        while(y < 0) {
            this.y = nextUp();
            y++;
        }
    }

    /**
     * Returns weather move (x, y) is legal according to tile colors.
     * 
     * @param x
     * @param y
     * @return
     */
    protected boolean isMoveLegal(int x, int y) {
        return Game.getInstance().colorMatch(this.x, this.y, this.x + x, this.y + y);
    }

    private int nextUp() {
        boolean found = false;
        int i = this.y;

        while(i > 0 && !found) {
            i--;
            found = isMoveLegal(0, i - this.y);
        }

        if(!found)
            i = this.y;
        
        return i;
    }

    private int nextDown() {
        boolean found = false;
        int i = this.y;
        int height = Game.getInstance().getViewHeight();

        while(i < height-1 && !found) {
            i++;
            found = isMoveLegal(0, i - this.y);
        }

        if(!found)
            i = this.y;
        
        return i;
    }

    private int nextLeft() {
        boolean found = false;
        int i = this.x;

        while(i > 0 && !found) {
            i--;
            found = isMoveLegal(i - this.x, 0);
        }

        if(!found)
            i = this.x;
        
        return i;
    }

    private int nextRight() {
        boolean found = false;
        int i = this.x;
        int width = Game.getInstance().getViewWidth();

        while(i < width-1 && !found) {
            i++;
            found = isMoveLegal(i - this.x, 0);
        }

        if(!found)
            i = this.x;
        
        return i;
    }

}
