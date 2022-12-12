package com.group22;

import java.util.ArrayList;
import javafx.scene.input.KeyCode;

/**
 * Author Liam Clegg
 * version 1.1
 *{@code Player} hold info about the players movements as well as other
 * inherited from {@code LandMover}.
 */
public class Player extends LandMover {
    protected double time;
    private KeyCode lastDown;
    private boolean torch;
    private boolean touchingDoor = false;
    private boolean touchingPickup = false;

    /**
     * creates an entity with starting position sets in super class
     * @param x coords
     * @param y coords
     * @param torch
     */
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

        this.getSprite().setImageSet("idle");
        this.setSpriteOffset(0, -0.3);
        this.moveEvery = 0.15;
    }

    /** 
     * @return boolean
     */
    public boolean hasTorch() {
        return this.torch;
    }
    
    /**
     * checks to see if the player character is at the door
     * if they are then checks whether all loot has been removed from
     * arraylist to et the levels door as open
     */
    public void isAtDoor() {
        ArrayList<PickUp> pickups = 
            new ArrayList<>(Game.getInstance().getEntities(Loot.class));
        pickups.addAll(Game.getInstance().getEntities(Lever.class));
        if (pickups.isEmpty() &&
            this.getX() == Game.getInstance().getDoor().getX() && 
            this.getY() == Game.getInstance().getDoor().getY()) {

            Game.getInstance().getDoor().setIsOpen(true);
        }
    }

    
    /**
     * tostring for player returns x positon y position and torch details
     * @return String
     */
    @Override
    public String toString() {
        return ("player " + getX() + " " + getY() + (torch ? " torch" : ""));
    }

    @Override
    protected void move(int x, int y) {
        if (x == 1) {
            this.getSprite().setImageSet("right");
        } else if (x == -1) {
            this.getSprite().setImageSet("left");
        } else if (y == 1) {
            this.getSprite().setImageSet("down");
        } else if (y == -1) {
            this.getSprite().setImageSet("up");
        }

        super.move(x, y);
    }

    /**
     * to allow replays with previous players movements
     * @param x x coordinate
     * @param y y coordinate
     */

    protected void moveWithReplay(int x, int y) {
        ReplayFrame frame = new ReplayFrame(x, y, this.time);
        Game.getInstance().addFrameToReplay(frame);
        this.move(x, y);
    }

    /**
     * records movements to be reran later
     */

    @Override
    protected void updateMovement() {
        if (this.lastDown == null) {
            this.lastDown = Game.getInstance().getLastKeyDown(
                KeyCode.W, KeyCode.A, KeyCode.S, KeyCode.D);
        }

        this.getSprite().setImageSet("idle");
        
        if (this.lastDown == KeyCode.W) {
            moveWithReplay(0, -1);
        } else if (this.lastDown == KeyCode.S) {
            moveWithReplay(0, 1);
        } else if (this.lastDown == KeyCode.A) {
            moveWithReplay(-1, 0);
        } else if (this.lastDown == KeyCode.D) {
            moveWithReplay(1, 0);
        }

        this.lastDown = null;
    }


    /**
     * refreshed by frame to check what the player is touching
     * handles sprite offset to appear as animation
     *
     */
    @Override
    protected void update() {
        isAtDoor();

        ArrayList<Door> doors = Game.getInstance().getEntities(Door.class);
        ArrayList<PickUp> pickUps = 
            Game.getInstance().getEntities(PickUp.class);


        for (Door door : doors) {
            if (door.getX() == this.getX() && this.getY() == door.getY()) {
                touchingDoor = true;
            }
        }

        for (PickUp pickUp : pickUps) {
            if (pickUp.getX() == this.getX() && this.getY() == pickUp.getY()) {
                touchingPickup = true;
            }
        }

        if (touchingDoor) {
            this.setSpriteOffset(0, 0);
        } else if (touchingPickup) {
            this.setSpriteOffset(0, -0.5);
        } else {
            this.setSpriteOffset(0, -0.25);
        }

        if (
            Game.getInstance().getKeyDown(KeyCode.W) || 
            Game.getInstance().getKeyDown(KeyCode.S) || 
            Game.getInstance().getKeyDown(KeyCode.A) || 
            Game.getInstance().getKeyDown(KeyCode.D)) {
                
            this.lastDown = Game.getInstance().getLastKeyDown(
                KeyCode.W, KeyCode.A, KeyCode.S, KeyCode.D);
        }

        if (torch) {
            Game.getInstance().getTile(getX(), getY()).lightUp();
        }

        this.time += Game.getInstance().getDelta();
    }
}
