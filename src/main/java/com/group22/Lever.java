package com.group22;

import java.util.ArrayList;

/**
 * {@code Lever} class is a subclass of {@code Pickup} and is an entity which is drawn to screen
 * Each instance owns its own values for 
 * coordinates which are inherited and also a colour
 *
 * @author Cellan Lees
 * @version 1.1
 */
public class Lever extends PickUp {
    private String leverColour;
    private Boolean leverIsOpen = false;

    /**
     * 
     * @param leverX the horizontal position of a lever on the map
     * @param leverY the vertical position of a lever on the map
     * @param leverColour the colour of a lever
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
     * @param leverColour setter for the colour of a lever
     */
    private void setLeverColour(String leverColour) {
        this.leverColour = leverColour;
    }


    /** 
     * @return String the colour of a lever
     */
    public String getLeverColour() {
        return this.leverColour;
    }

    
    /** 
     * @return String of lever X position, Y position and the colour to be
     * saved/loaded.
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
