package com.group22;

import javafx.scene.input.KeyCode;

public class Player extends LandMover {
    public Player(int x, int y) {
        super(x, y);

        this.getSprite().setAnimationSpeed(0.1);

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

        this.spriteOffsetY = -0.3;
        this.moveEvery = 0.2;
    }

    @Override
    protected void updateMovement() {
        this.getSprite().setImageSet("idle");
        if(Game.getInstance().getKeyState(KeyCode.W) && moveIfValid(0, -1)) {
            this.getSprite().setImageSet("up");
        } else if(Game.getInstance().getKeyState(KeyCode.S) && moveIfValid(0, 1)) {
            this.getSprite().setImageSet("down");
        }

        if(Game.getInstance().getKeyState(KeyCode.A) && moveIfValid(-1, 0)) {
            this.getSprite().setImageSet("left");
        } else if(Game.getInstance().getKeyState(KeyCode.D) && moveIfValid(1, 0)) {
            this.getSprite().setImageSet("right");
        }
    }

    private boolean moveIfValid(int x, int y) {
        if(x == 0 && y == 0)
            return false;

        if(x != 0 && y != 0)
            return false;

        int tempX = this.x;
        int tempY = this.y;

        this.move(x, y);

        if(tempX != this.x || tempY != this.y)
            return true;

        return false;
    }

    @Override
    protected void update() {
        if(
            Game.getInstance().getKeyDown(KeyCode.W) || 
            Game.getInstance().getKeyDown(KeyCode.S) || 
            Game.getInstance().getKeyDown(KeyCode.A) || 
            Game.getInstance().getKeyDown(KeyCode.D)
        ) {
            this.resetMovementUpdate();
        }
    }
}
