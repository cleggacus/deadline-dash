package com.group22;

// randomly moving land move demo
public class TestObject extends LandMover {
    private int moveX = 0;
    private int moveY = 0;

    public TestObject() {
        super(1,1);

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
        this.moveEvery = 0.3;

        this.x = (int)(Math.floor(Math.random() * Game.getInstance().getViewWidth()));
        this.y = (int)(Math.floor(Math.random() * Game.getInstance().getViewHeight()));
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
    protected void updateMovement() {
        this.getSprite().setImageSet("idle");

        if(Math.random() < 0.3 || !moveIfValid(moveX, moveY)) {
            do {
                boolean willMoveX = Math.random() < 0.5;
                moveX = willMoveX ? (int)(Math.floor(Math.random() * 3 - 1)) : 0;
                moveY = willMoveX ? 0 : (int)(Math.floor(Math.random() * 3 - 1));
            } while(!moveIfValid(moveX, moveY));
        }

        if(moveY < 0)
            this.getSprite().setImageSet("up");
        else if(moveY > 0)
            this.getSprite().setImageSet("down");

        if(moveX < 0)
            this.getSprite().setImageSet("left");
        else if(moveX > 0)
            this.getSprite().setImageSet("right");
    }

    @Override
    protected void update() {}
}
