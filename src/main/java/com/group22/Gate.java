package com.group22;

/**
 * Gate class is a subclass of Entity and is drawn to screen
 *
 * Each instance owns its own values for coordinates which are inherited and also a colour
 *
 *
 * @author Cellan Lees
 * @version 1.0
 */


public class Gate extends Entity {

    private Boolean gateIsOpen = false;
    private String gateColour;

    public Gate(int gateX, int gateY, String gateColour) {
        super(gateX, gateY);
        setGateColour(gateColour);

        this.getSprite().addImageSet("closed", new String[] {
            "item/gate.png"
        });

        this.getSprite().addImageSet("open", new String[] {
                "item/gateOpen.png"
        });
        this.getSprite().setImageSet("closed");

        this.getSprite().applyColor(TileColor.getFromLabel(gateColour.charAt(0)).color);
    }

    private void setGateColour(String gateColour) {
        this.gateColour = gateColour;
    }

    protected void setGateIsOpen(Boolean gateIsOpen) {
        this.gateIsOpen = gateIsOpen;
        this.getSprite().setImageSet("open");
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

