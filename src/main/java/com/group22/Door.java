package com.group22;

import javafx.scene.image.Image;

public class Door /** extends entity*/ {

    private Boolean gateIsOpen = false;

    public Door(int doorX, int doorY, Image sprite){
        //super(doorX,doorY,Image sprite)
    }

    protected void setGateIsOpen(Boolean gateIsOpen) {
        this.gateIsOpen = gateIsOpen;
    }

    public Boolean getGateIsOpen(){
        return this.gateIsOpen;
    }
}
