package com.group22;

/**
 * {@code Door} class is a subclass of {@code Entity} and is an entity which is drawn to screen
 *
 * Each instance has its own values for coordinates which are inherited
 *
 *
 * @author Cellan Lees
 * @version 1.0
 */
public class Door extends Entity {
    /** variable for animation timer*/
    private static final double OPEN_ANIMATION_DURATION = 0.1;
    private double openTimer = 0;
    /** variable for wether door is open or not */
    private boolean isOpen = false;

    /**
     * constructor calls super constructor
     * no need for a setter as can assume is open is false at start of game
     * @param doorX the horizontal position of a door on the map
     * @param doorY the vertical position of a door on the map
     */
    public Door(int doorX, int doorY) {
        super(doorX,doorY);
        this.setSpriteOffset(0, -0.3);
        this.getSprite().addImageSet("closed", new String[] {
                "item/door_closed.png"
        });

        this.getSprite().addImageSet("open", new String[] {
                "item/DoorOpen.png"
        });
    }

    
    /** 
     * Sets the open state of the door.
     * 
     * @param isOpen setter for isOpen
     */
    public void setIsOpen(boolean isOpen) {
        this.isOpen = isOpen;
    }
    
    /** 
     * Gets the string required to construct a saved Door.
     * 
     * @return String of door X position -and Y position to be saved/loaded.
     */
    @Override
    public String toString() {
        return ("door " + getX() + " " + getY());
    }

    
    /** 
     * This method is unused by door.
     */
    @Override
    protected void updateMovement() {}

    /**
     * Checks if door is open and acts accordingly.
     */
    @Override
    protected void update() {
        if (this.isOpen) {
            this.getSprite().setImageSet("open");
            this.openTimer += Game.getInstance().getDelta();
        } else {
            this.getSprite().setImageSet("closed");
        }

        if (this.openTimer >= Door.OPEN_ANIMATION_DURATION) {
            Game.getInstance().incrementScore(
                (int) Game.getInstance().getTime() * 10);
            Game.getInstance().setLevelFinish();
        }
    }


}
