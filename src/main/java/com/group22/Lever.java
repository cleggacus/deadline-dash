package com.group22;

public class Lever extends PickUp {
    private String leverColour;
    private Boolean leverIsOpen = false;

    public Lever(int leverX, int leverY, Sprite leverSprite, String leverColour){
        super(leverX, leverY, leverSprite);
        setLeverColour(leverColour);
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
/** Entities is the place holder for the array list of all entities, 
 * if we don't have one already then we should have
    public void activatePickUpEffect() {
        for (Gate gate : Entities){
            if (gate.getGateColour == this.getLeverColour){
                gate.setGateIsOpen(True);
            }
        }
    }
*/

}
