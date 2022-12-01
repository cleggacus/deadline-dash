package com.group22;

import java.util.ArrayList;

public class SmartMover extends LandMover{

    private ArrayList<Branch> existingBranches;
    public SmartMover(int posX, int posY){
        super(posX, posY);
        this.getSprite().setImage("NPC/SmartMover.PNG");
    }

    public String findPath(){
        Branch originBranch = new Branch(this.getX(), this.getY());
        return null;
    }
    @Override
    protected void updateMovement() {
        // TODO Auto-generated method stub
        
    }

    @Override
    protected void update() {
        // TODO Auto-generated method stub
        
    }
    
}
