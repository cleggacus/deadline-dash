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

    @Override
    public void activatePickUpEffect(LandMover landMover) {
        for (Entity gate : Game.getInstance().getEntities(Gate.class)){
            if (((Gate) gate).getGateColour() == this.getLeverColour()){
                 ((Gate) gate).setGateIsOpen(true);
            }
        }
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
