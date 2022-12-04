package com.group22;

import java.util.ArrayList;

public class Gate extends Entity {

    private Boolean gateIsOpen = false;
    private String gateColour;

    public Gate(int gateX, int gateY, String gateColour) {
        super(gateX, gateY);
        setGateColour(gateColour);

        this.getSprite().setImage("item/gate.png");
        this.getSprite().applyColor(TileColor.getFromLabel(gateColour.charAt(0)).color);
    }

    private void setGateColour(String gateColour) {
        this.gateColour = gateColour;
    }

    protected void setGateIsOpen(Boolean gateIsOpen) {
        this.gateIsOpen = gateIsOpen;

    }
    public Boolean getGateIsOpen(){
        return gateIsOpen;
    }

    public String getGateColour() {
        return this.gateColour;
    }

    @Override
    protected void updateMovement() {
        // TODO Auto-generated method stub

    }
    
    protected void removeGate(){


    }

    @Override
    protected void update() {

    }

    public void activatePickUpEffect() {

    }


}

