package com.group22;

import java.util.ArrayList;

import javafx.scene.input.KeyCode;

public class ReplayPlayer extends Player {

    private KeyCode lastDown;
    public ReplayPlayer(int x, int y) {
        super(x, y);
    }
    
    @Override
    protected void updateMovement() {
        this.getSprite().setImageSet("idle");

        //if(this.lastDown == null)
            //this.lastDown = Game.getInstance().getLastReplayKeyDown(KeyCode.W, KeyCode.A, KeyCode.S, KeyCode.D);

        if(this.lastDown == KeyCode.W) {
            this.getSprite().setImageSet("up");
            move(0, -1);
        } else if(lastDown == KeyCode.S) {
            this.getSprite().setImageSet("down");
            move(0, 1);
        } else if(lastDown == KeyCode.A) {
            this.getSprite().setImageSet("left");
            move(-1, 0);
        } else if(lastDown == KeyCode.D) {
            this.getSprite().setImageSet("right");
            move(1, 0);
        }

        ArrayList<Door> doors = Game.getInstance().getEntities(Door.class);
        ArrayList<PickUp> pickUps = Game.getInstance().getEntities(PickUp.class);

        boolean touchingDoor = false;
        boolean touchingPickup = false;

        for(Door door : doors) {
            if(door.getX() == this.getX() && this.getY() == door.getY()) {
                touchingDoor = true;
            }
        }

        for(PickUp pickUp : pickUps) {
            if(pickUp.getX() == this.getX() && this.getY() == pickUp.getY()) {
                touchingPickup = true;
            }
        }

        if(touchingDoor)
            this.setSpriteOffset(0, 0);
        else if(touchingPickup)
            this.setSpriteOffset(0, -0.5);
        else
            this.setSpriteOffset(0, -0.25);

    }

    @Override
    protected void update() {
        //if(Game.getInstance().getReplayKey()){

        //}
    }

}
