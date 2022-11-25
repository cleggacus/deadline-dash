package com.group22;

import java.util.ArrayList;

public class Lever extends PickUp {
    private String leverColour;
    private Boolean leverIsOpen = false;
    private static ArrayList<Gate> gates;

    public Lever(int leverX, int leverY, Sprite leverSprite, String leverColour){
        super(leverX, leverY, leverSprite);
        setLeverColour(leverColour);
        if(gates.isEmpty()){
            for (Entity gate : Game.getInstance().getEntities(Gate.class)){
                gates.add((Gate) gate);
            }
        }
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

    public void activatePickUpEffect() {
        for (Gate gate : gates){
                if(gate.getGateColour() == this.getLeverColour()){
                    gate.setGateIsOpen(true);
                }
            }
        }
}
