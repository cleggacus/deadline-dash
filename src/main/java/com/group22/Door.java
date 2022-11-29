package com.group22;


public class Door /** extends entity*/ {

    private Boolean gateIsOpen = false;

    /**
     * cosntructor calls super constructor
     * no need for a setter as can assume is open is false at start of game
     * @param doorX
     * @param doorY
     * @param sprite
     */

    public Door(int doorX, int doorY, Sprite sprite){
        //super(doorX,doorY,Image sprite)
    }

    protected void setGateIsOpen(Boolean gateIsOpen) {
        this.gateIsOpen = gateIsOpen;
    }

    public Boolean getGateIsOpen(){
        return this.gateIsOpen;
    }
}
