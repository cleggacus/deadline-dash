package com.group22;

public class Lever extends PickUp {
    private String leverColour;
    private Boolean leverIsOpen = false;

    public Lever(int leverX, int leverY, String leverColour){
        super(leverX, leverY);
        setLeverColour(leverColour);
        this.getSprite().setImage("item/lever.png");
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
        for (Entity gate : Game.getInstance().getEntities(Gate.class)){
            if (((Gate) gate).getGateColour() == this.getLeverColour()){
                 ((Gate) gate).setGateIsOpen(true);
            }
        }
        Game.getInstance().removeEntity(this);
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
