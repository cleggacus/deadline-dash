package com.group22;

import java.util.ArrayList;

/**
 * Lever class is a subclass of Pickup and is an entity which is drawn to screen
 * Each instance owns its own values for 
 * coordinates which are inherited and also a colour
 *
 * @author Cellan Lees
 * @version 1.0
 */
public class Lever extends PickUp {
    private String leverColour;
    private Boolean leverIsOpen = false;

    /**
     * 
     * @param leverX
     * @param leverY
     * @param leverColour
     */
    public Lever(int leverX, int leverY, String leverColour) {
        super(leverX, leverY);
        setLeverColour(leverColour);
        this.setSpriteOffset(0, -0.25);
        this.getSprite().setImage("item/lever.png");
        this.getSprite().applyColor(
            TileColor.getFromLabel(leverColour.charAt(0)).color);
    }

    
    /** 
     * @param leverColour
     */
    private void setLeverColour(String leverColour) {
        this.leverColour = leverColour;
    }

    
    /** 
     * @param leverIsOpen
     */
    protected void setIsOpened(Boolean leverIsOpen) {
        this.leverIsOpen = leverIsOpen;
    }

    
    /** 
     * @return String
     */
    public String getLeverColour() {
        return this.leverColour;
    }

    
    /** 
     * @return Boolean
     */
    public Boolean getIsOpen() {
        return this.leverIsOpen;
    }

    
    /** 
     * @return String
     */
    @Override
    public String toString() {
        return ("lever " + getX() + " " + getY() + " " + getLeverColour());
    }

    
    /**
     * Searches through a list of gates via the list of entities, opening gates
     * of the same colour as the lever.
     * @param landMover the object activating the pickup
     */
    @Override
    public void activatePickUpEffect(LandMover landMover) {
        ArrayList<Gate> gates = Game.getInstance().getEntities(Gate.class);

        for (Gate gate : gates) {
            if (gate.getGateColour().equals( this.getLeverColour())) {
                 gate.setGateIsOpen(true);
            }
        }

        Game.getInstance().removeEntity(this);
    }
}
