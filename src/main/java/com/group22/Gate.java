package com.group22;

/**
 * {@code Gate} class is a subclass of {@code Entity} and is drawn to screen
 *
 * Each instance owns its own values for coordinates 
 * which are inherited and also a colour
 *
 *
 * @author Cellan Lees
 * @version 1.1
 */
public class Gate extends Entity {

    private Boolean gateIsOpen = false;
    private String gateColour;

    /**
     * 
     * @param gateX the horizontal position of a gate on the map
     * @param gateY the vertical position of a gate on the map
     * @param gateColour the color of a gate
     * @param isOpen true if a gate is open
     */
    public Gate(int gateX, int gateY, String gateColour, boolean isOpen) {
        super(gateX, gateY);
        setGateColour(gateColour);
        this.getSprite().addImageSet("closed", new String[] {
            "item/gate.png"
        });

        this.getSprite().addImageSet("open", new String[] {
                "item/gateOpen.png"
        });
        this.getSprite().setImageSet("closed");

        this.getSprite().applyColor(
            TileColor.getFromLabel(gateColour.charAt(0)).color);
            
        if (isOpen) {
            setGateIsOpen(true);
        }
    }

    
    /** 
     * @param gateColour setter for the colour of a gate
     */
    private void setGateColour(String gateColour) {
        this.gateColour = gateColour;
    }

    
    /** 
     * @param gateIsOpen setter for if a gate is open or not
     */
    protected void setGateIsOpen(Boolean gateIsOpen) {
        this.gateIsOpen = gateIsOpen;
        this.getSprite().setImageSet("open");
        this.getSprite().applyColor(
            TileColor.getFromLabel(gateColour.charAt(0)).color);
    }
    
    /** 
     * @return Boolean getter for if a gate is open or not
     */
    public Boolean getGateIsOpen() {
        return gateIsOpen;
    }

    
    /** 
     * @return String of the colour of a gate
     */
    public String getGateColour() {
        return this.gateColour;
    }

    
    /** 
     * @return String of gate X position, Y position, gate colour and
     * if the gate is open or not.
     */
    @Override
    public String toString(){
        return ("gate " + getX() + " " + getY() + " " + 
            getGateColour() + " " + getGateIsOpen());
    }

    /**
     * This method is unused by Gate.
     */
    @Override
    protected void updateMovement() {}

    /**
     * This method is unused by Gate.
     */
    @Override
    protected void update() {

    }



}
