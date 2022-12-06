package com.group22;

import java.util.ArrayList;

import javafx.scene.input.KeyCode;

public class ReplayPlayer extends LandMover {

    private ArrayList<KeyCode> keysDown;
    private ArrayList<ReplayFrame> frames;
    private int currentFrame;
    public ReplayPlayer(int x, int y, ArrayList<ReplayFrame> frames) {
        super(x, y);
        this.frames = frames;
        this.currentFrame = 0;
        this.keysDown = new ArrayList<KeyCode>();
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
    
    @Override
    protected void updateMovement() {
        this.getSprite().setImageSet("idle");

        for(KeyCode keyDown : keysDown){
            if(keyDown == KeyCode.W) {
                this.getSprite().setImageSet("up");
                move(0, -1);
            } else if(keyDown == KeyCode.S) {
                this.getSprite().setImageSet("down");
                move(0, 1);
            } else if(keyDown == KeyCode.A) {
                this.getSprite().setImageSet("left");
                move(-1, 0);
            } else if(keyDown == KeyCode.D) {
                this.getSprite().setImageSet("right");
                move(1, 0);
            }
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
        if(frames.size() == 0){
            Game.getInstance().setGameOver();
        }
        if(frames.get(0).getKeyTime() <= Game.getInstance().getTimeElapsed()){
            if(frames.get(0).getKeyDown()){
                this.keysDown.add(frames.get(this.currentFrame).getKey());
                System.out.println("Key " + frames.get(this.currentFrame).getKey() + " down");
            } else {
                System.out.println("Key " + frames.get(this.currentFrame).getKey() + " up");
                this.keysDown.remove(frames.get(this.currentFrame).getKey());
            }
            frames.remove(0);
        }
    }

}
