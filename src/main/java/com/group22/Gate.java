package com.group22;

public class Gate extends Entity {

    private Boolean gateIsOpen = false;
    private String gateColour;

    public Gate(int gateX, int gateY /**,Sprite sprite,**/,String gateColour){
       //Super(gateX,gatY/**,Sprite sprite,**/)
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

    public String getGateColour() {
        return this.gateColour;
    }

    public Boolean getGateIsOpen() {
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
