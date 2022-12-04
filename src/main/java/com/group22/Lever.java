package com.group22;

import java.util.ArrayList;

public class Lever extends PickUp {
    private String leverColour;
    private Boolean leverIsOpen = false;

    public Lever(int leverX, int leverY, String leverColour){
        super(leverX, leverY);
        setLeverColour(leverColour);

        this.setSpriteOffset(0, -0.25);
        this.getSprite().setImage("item/lever.png");
        this.getSprite().applyColor(TileColor.getFromLabel(leverColour.charAt(0)).color);
    }

    private void setLeverColour(String leverColour){
        this.leverColour = leverColour;
    }

    protected void setIsOpened(Boolean leverIsOpen){
        this.leverIsOpen = leverIsOpen;
    }

    public String getLeverColour(){
        return this.leverColour;
    }

    public Boolean getIsOpen(){
        return this.leverIsOpen;
    }

    /**
     * Searches through a list of gates via the list of entities, opening gates
     * of the same colour as the lever.
     * @param landMover the object activating the pickup
     */
    @Override
    public void activatePickUpEffect(LandMover landMover) {
        ArrayList<Gate> gates = Game.getInstance().getEntities(Gate.class);
        for (Gate gate : gates){
            if (gate.getGateColour().equals( this.getLeverColour())){
                 gate.setGateIsOpen(true);
                 Game.getInstance().removeEntity(gate);

            }
        }
        Game.getInstance().removeEntity(this);
    }
}
