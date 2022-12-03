package com.group22;

public class Bomb extends Entity{

    private int countdown = 3;

    /**
     *
     * @param x the horizontal position of a bomb on the map
     * @param y the vertical poisiton of a bomb on the map
     * @param countdown the countdown timer of the bomb's detonation
     */

    public Bomb(int x, int y, int countdown) {
        super(x, y);
        this.countdown = countdown;

        this.getSprite().addImageSet("tick", Sprite.createImageFade(
            "item/farron/farron0.png",
            "item/farron/farron5.png",
            12*3
        ));

        this.getSprite().setAnimationSpeed(countdown);
        this.getSprite().setImageSet("tick");
        this.getSprite().setAnimationType(AnimationType.SINGLE);
    }

    /* public void checkNeighbourTiles(){
        while {
            for (int i = x - 1; i <= x + 1; i++) {
                for (int j = y - 1; j <= y + 1; j++) {
                    if (i != x || j != y) { //ignore the bomb tile
                    }

                }
            }
        }
    } */

    public void activateBomb(){
    }

    @Override
    protected void updateMovement() {
        // TODO Auto-generated method stub
        
    }

    double time = 0;

    @Override
    protected void update() {
        double shakeAmountX = 0.05 * Math.sin(1.1*countdown * Math.pow(time, 3));
        double shakeAmountY = 0.05 * Math.sin(0.9*countdown * Math.pow(time, 3));

        this.setSpriteOffset(shakeAmountX, shakeAmountY);
        time += Game.getInstance().getDelta();
        // TODO Auto-generated method stub
        
    }
}
