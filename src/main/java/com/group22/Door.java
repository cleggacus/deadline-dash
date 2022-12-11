package com.group22;

/**
 * Door class is a subclass of Entity and is an entity which is drawn to screen
 *
 * Each instance owns its own values for coordinates which are inherited
 *
 *
 * @author Cellan Lees
 * @version 1.0
 */
public class Door extends Entity {
    private static final double OPEN_ANIMATION_DURATION = 0.1;
    private double openTimer = 0;
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
     * @param isOpen setter for isOpen
     */
    public void setIsOpen(boolean isOpen) {
        this.isOpen = isOpen;
    }
    
    /** 
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
     * 
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
