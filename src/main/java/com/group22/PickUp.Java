package com.group22;

public abstract class PickUp extends Entity {
    
    public PickUp (int posX, int posY, Sprite pickUpSprite){
        super(posX, posY, pickUpSprite);
    }

    public abstract activatePickUpEffect ()

    public addToInventory (Player player) {
        player.getInventory.add(this);
    }
}