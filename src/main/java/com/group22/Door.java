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
    private static final double OPEN_ANIMATION_DURATION = 0.5;
    private double openTimer = 0;
    private boolean isOpen = false;
    private boolean gateIsOpen = false;

    /**
     * cosntructor calls super constructor
     * no need for a setter as can assume is open is false at start of game
     * @param doorX
     * @param doorY
     * @param
     */

    public Door(int doorX, int doorY){
        super(doorX,doorY);
        this.setSpriteOffset(0, -0.3);
        this.getSprite().addImageSet("closed", new String[] {
                "item/door_closed.png"
        });

        this.getSprite().addImageSet("open", new String[] {
                "item/DoorOpen.png"
        });
    }

    public void setIsOpen(boolean isOpen) {
        this.isOpen = isOpen;
    }



    public Boolean getGateIsOpen(){
        return this.gateIsOpen;
    }

    @Override
    protected void updateMovement() {
        // TODO Auto-generated method stub
        
    }

    @Override
    protected void update() {
        // TODO Auto-generated method stub
        if(this.isOpen) {
            this.getSprite().setImageSet("open");
            this.openTimer += Game.getInstance().getDelta();
        } else {
            this.getSprite().setImageSet("closed");
        }

        if(this.openTimer >= Door.OPEN_ANIMATION_DURATION) {
            Game.getInstance().setLevelFinish();
        }
    }


}
