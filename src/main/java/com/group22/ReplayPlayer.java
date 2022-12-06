package com.group22;

import java.util.ArrayList;

import javafx.scene.input.KeyCode;

public class ReplayPlayer extends Player {

    private KeyCode lastDown;
    private ArrayList<ReplayFrame> frames;
    private int currentFrame;
    public ReplayPlayer(int x, int y, ArrayList<ReplayFrame> frames) {
        super(x, y);
        this.frames = frames;
        this.currentFrame = 0;
    }
    
    @Override
    protected void updateMovement() {
        this.getSprite().setImageSet("idle");

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
        if(frames.size() == this.currentFrame){
            Game.getInstance().setGameOver();
        }
        if(frames.get(this.currentFrame).getKeyTime() <= Game.getInstance().getTimeElapsed()){
            if(frames.get(this.currentFrame).getKeyDown()){
                this.lastDown = frames.get(this.currentFrame).getKey();
            }
            this.currentFrame += 1;
        }
    }

}
