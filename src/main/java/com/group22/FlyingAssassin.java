package com.group22;

import java.util.ArrayList;

/**
 * 
 * The class {@code FlyingAssassin} impliments an entity which moves across the board in a straight line regardless of the tiles. 
 * Turns 180 degrees if it hits a wall.
 * If it occupies same space as player then the player loses
 * 
 * @author Ezana Tareke
 * @version 1.0
 */
public class FlyingAssassin extends Entity {
    private int moveByY;
    private int moveByX;

    public FlyingAssassin(int x, int y, boolean isVertical) {
        super(x, y);
        this.getSprite().setImage("NPC/FollowingThief.png");
        this.moveEvery = 0.2;

        this.moveByX = isVertical ? 0 : 1;
        this.moveByY = isVertical ? 1 : 0;
    }
    

    @Override
    protected void updateMovement() {
        if(!Game.getInstance().isInBounds(getX()+this.moveByX, getY()+this.moveByY)){
            this.moveByY = -this.moveByY;
            this.moveByX = -this.moveByX;
        }

        this.move(this.moveByX, this.moveByY);


        
    }

    @Override
    protected void update() {
        // TODO Auto-generated method stub
        
        ArrayList<LandMover> landMovers = Game.getInstance().getEntities(LandMover.class);

        for(LandMover landMover : landMovers) {
            if(
                landMover.getX() == this.getX() && 
                landMover.getY() == this.getY()
            ) {
                Game.getInstance().removeEntity(landMover);
            }
        }
    }
}
