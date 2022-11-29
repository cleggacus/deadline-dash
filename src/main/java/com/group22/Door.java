package com.group22;


public class Door extends Entity {

    private Boolean gateIsOpen = false;

    /**
     * cosntructor calls super constructor
     * no need for a setter as can assume is open is false at start of game
     * @param doorX
     * @param doorY
     * @param sprite
     */

    public Door(int doorX, int doorY){
        super(doorX,doorY);
        this.getSprite().setImage("item/door_closed.png");
    }

    protected void setGateIsOpen(Boolean gateIsOpen) {
        this.gateIsOpen = gateIsOpen;
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
        
    }
}
