package com.group22;

import java.util.ArrayList;

import javafx.scene.input.KeyCode;

public class Player extends LandMover {
    private KeyCode lastDown;
    private boolean torch;

    public Player(int x, int y, boolean torch) {
        super(x, y);

        this.torch = torch;

        this.getSprite().setAnimationSpeed(0.4);
        this.setShadow(true);

        this.getSprite().addImageSet("idle", new String[] {
            "character/tile000.png",
        });

        this.getSprite().addImageSet("up", new String[] {
            "character/tile012.png",
            "character/tile013.png",
            "character/tile014.png",
            "character/tile015.png",
        });

        this.getSprite().addImageSet("down", new String[] {
            "character/tile000.png",
            "character/tile001.png",
            "character/tile002.png",
            "character/tile003.png",
        });

        this.getSprite().addImageSet("left", new String[] {
            "character/tile004.png",
            "character/tile005.png",
            "character/tile006.png",
            "character/tile007.png",
        });

        this.getSprite().addImageSet("right", new String[] {
            "character/tile008.png",
            "character/tile009.png",
            "character/tile010.png",
            "character/tile011.png",
        });

        this.setSpriteOffset(0, -0.3);
        this.moveEvery = 0.15;
    }

    public boolean hasTorch() {
        return this.torch;
    }
    
    public void isAtDoor(){
        ArrayList<PickUp> pickups
        = new ArrayList<>(Game.getInstance().getEntities(Loot.class));
        pickups.addAll(Game.getInstance().getEntities(Lever.class));
        if(pickups.isEmpty()
        && this.getX() == Game.getInstance().getDoor().getX()
        && this.getY() == Game.getInstance().getDoor().getY()){
            Game.getInstance().getDoor().setIsOpen(true);
        }
    }

    @Override
    public String toString(){
        return ("player " + getX() + " " + getY());
    }

    @Override
    protected void updateMovement() {
        this.getSprite().setImageSet("idle");
        double keyTime = Game.getInstance().getTimeElapsed();

        isAtDoor();
        if(this.lastDown == null)
            this.lastDown = Game.getInstance().getLastKeyDown(KeyCode.W, KeyCode.A, KeyCode.S, KeyCode.D);

        if(this.lastDown == KeyCode.W) {
            this.getSprite().setImageSet("up");
            keyTime = Game.getInstance().getTimeElapsed();
            move(0, -1);
        } else if(lastDown == KeyCode.S) {
            this.getSprite().setImageSet("down");
            keyTime = Game.getInstance().getTimeElapsed();
            move(0, 1);
        } else if(lastDown == KeyCode.A) {
            this.getSprite().setImageSet("left");
            keyTime = Game.getInstance().getTimeElapsed();
            move(-1, 0);
        } else if(lastDown == KeyCode.D) {
            this.getSprite().setImageSet("right");
            keyTime = Game.getInstance().getTimeElapsed();
            move(1, 0);
        }
        Game.getInstance().newReplayFrame(this.getX(), this.getY(), keyTime);

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

        this.lastDown = null;
    }

    @Override
    protected void update() {
        if(
            Game.getInstance().getKeyDown(KeyCode.W) || 
            Game.getInstance().getKeyDown(KeyCode.S) || 
            Game.getInstance().getKeyDown(KeyCode.A) || 
            Game.getInstance().getKeyDown(KeyCode.D)
        ) {
            lastDown = Game.getInstance().getLastKeyDown(KeyCode.W, KeyCode.A, KeyCode.S, KeyCode.D);
        }

        if(torch) {
            Game.getInstance().getRenderer().setLightPosition(getDrawX(), getDrawY(), 0.15);
        }
        if(Game.getInstance().getLastKeyReleased() != null){
            Game.getInstance().resetLastKeyReleased();
        }

    }
}
