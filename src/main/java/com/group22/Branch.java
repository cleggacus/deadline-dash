package com.group22;

import java.util.ArrayList;

/**
 * The Branch class handles the paths of the smart mover. It makes a fake smart
 * mover to test for all valid movements from an existing branch. It can return
 * an array of branches and an array of coordinates as the path.
 * @author Lewis Meekings
 * @version 1.0
 */
public class Branch {

    /**
     * The X and Y coordinates of this branch.
     */
    private int branchX;
    private int branchY;
    
    /**
     * A fake entity used to test for valid movements from the current position.
     */
    private SmartMover fakeSmartMover;
    
    /**
     * The branches from the current branch, at most one per direction, right,
     * left, down, up.
     */
    private Branch leftBranch;
    private Branch rightBranch;
    private Branch downBranch;
    private Branch upBranch;

    /**
     * An arraylist containing any branch that has already been used.
     */

    private ArrayList<Branch> existingBranches;

    /**
     * An array list containing all the coordinates to reach the current tile.
     */
    
    private ArrayList<Integer> path;
    private static ArrayList<ArrayList<Integer>> targetPaths = new ArrayList<ArrayList<Integer>>();
    /**
     * Constructor for the branch, constructs branches of this branch, and adds
     * the branch coordinates towards the path.
     * @param posX the horizontal position of this branch.
     * @param posY the vertical position of this branch.
     */
    public Branch(int posX, int posY){
        this.branchX = posX;
        this.branchY = posY;
        existingBranches.add(this);
        path.add(posX, posY);
        if(!isTarget(this)){
            this.fakeSmartMover = new SmartMover(branchX, branchY);
            this.setLeft(posX, posY);
            this.setRight(posX, posY);
            this.setDown(posX, posY);
            this.setUp(posX, posY);
        }
        this.fakeSmartMover = null;
    }

    /**
     * Constructs the left branch of this branch if it exists
     * @param posX The current horizontal position
     * @param posY The current vertical position.
     */
    public void setLeft(int posX, int posY){
        if(this.getMover().nextLeft() == this.getX() ||
        compareBranch(new Branch(this.getMover().nextLeft(), this.getY()))){
            this.leftBranch = null;
        } else {
            this.leftBranch = new Branch(this.getMover().nextLeft(), this.getY());
        }
    }

    public void setRight(int posX, int posY){
        if(this.getMover().nextRight() == this.getX() ||
        compareBranch(new Branch(this.getMover().nextRight(), this.getY()))){
            this.rightBranch = null;
        } else {
            this.rightBranch = new Branch(this.getMover().nextRight(), this.getY());
        }
    }

    public void setDown(int posX, int posY){
        if(this.getMover().nextDown() == this.getY() ||
        compareBranch(new Branch(this.getX(), this.getMover().nextDown()))){
            this.downBranch = null;
        } else {
            this.downBranch = new Branch(this.getX(), this.getMover().nextDown());
        }
    }

    public void setUp(int posX, int posY){
        if(this.getMover().nextUp() == this.getY() ||
        compareBranch(new Branch(this.getX(), this.getMover().nextUp()))){
            this.upBranch = null;
        } else {
            this.upBranch = new Branch(this.getX(), this.getMover().nextUp());
        }
    }

    /**
     * Returns coordinates
     * @return the X coordinates of this branch
     */
    public int getX(){
        return this.branchX;
    }

    /**
     * Returns coordinates
     * @return the Y coordinates of this branch
     */
    public int getY(){
        return this.branchY;
    }

    /**
     * Returns a fake smart mover.
     * @return the fake smart mover held in this branch
     */
    public SmartMover getMover(){
        return this.fakeSmartMover;
    }

    /**
     * Returns a path
     * @return the coordinates to follow the path
     */
    public ArrayList<Integer> getPath(){
        return this.path;
    }

    /**
     * Returns all paths that reach a valid target, a valid target being a
     * pickup or the door if there are no pickups on the field
     * @return The list of paths that reach a valid target.
     */
    public ArrayList<ArrayList<Integer>> getPaths(){
        return Branch.targetPaths;
    }


    /**
     * Compares a branch to the existing branches, if they have the same
     * coordinates its a match
     * @param newBranch The branch candidate being compared
     * @return Whether the branch is in existing branches already
     */
    public Boolean compareBranch(Branch newBranch){
        Boolean isBranch = false;
        for (Branch branch : existingBranches){
            if(newBranch.getX() == branch.getX()
            && newBranch.getY() == branch.getY()){
                isBranch = true;
            }
        }
        return isBranch;
    }
    
    public Boolean isTarget(Branch newBranch){
        ArrayList<PickUp> pickups = Game.getInstance().getEntities(PickUp.class);
        ArrayList<Door> doors = Game.getInstance().getEntities(Door.class);
        if(pickups.isEmpty()){
            for (Door door : doors){
                if (door.getX() == this.getX() && door.getY() == this.getY()){
                    targetPaths.add(this.getPath());
                    return true;
                }
            }
        } else {
            for (PickUp pickup : pickups){
                if (pickup.getX() == this.getX() && pickup.getY() == this.getY()){
                    targetPaths.add(this.getPath());
                    return true;
                }
            }
        }
        return false;
    }

    public void clearPaths(){
        targetPaths.clear();
    }
}
