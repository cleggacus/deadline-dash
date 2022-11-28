package com.group22;

public class Lever /**extends Pickup**/ {
    private String leverColour;
    private Boolean leverIsOpen = false;

    public Lever(int leverX,int leverY,String leverColour/**,Sprite sprite**/){
        //super(leverX,leverY,leverColour)
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


}
