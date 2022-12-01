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
     * An array list containing all valid branches from the current branch.
     */
    private ArrayList<Branch> branches = new ArrayList<>();

    /**
     * An arraylist containing any branch that has already been used.
     */

    private ArrayList<Branch> existingBranches;

    /**
     * An array list containing all of the target branches.
     */

    private ArrayList<Branch> targetBranches;
    
    /**
     * An array list containing all the coordinates to reach the current tile.
     */
    
    private ArrayList<Integer> path;

    /**
     * Constructor for the branch, constructs branches of this branch, and adds
     * the branch coordinates towards the path.
     * @param posX the horizontal position of this branch.
     * @param posY the vertical position of this branch.
     */
    public Branch(int posX, int posY){
        this.branchX = posX;
        this.branchY = posY;
        this.fakeSmartMover = new SmartMover(branchX, branchY);
        existingBranches.add(this);
        path.add(posX, posY);
        this.setLeft(posX, posY);
        this.setRight(posX, posY);
        this.setDown(posX, posY);
        this.setUp(posX, posY);
    }

    /**
     * Constructs the left branch of this branch if it exists
     * @param posX The current horizontal position
     * @param posY The current vertical position.
     */
    public void setLeft(int posX, int posY){
        if(fakeSmartMover.nextLeft() == this.getX() ||
        compareBranch(new Branch(this.getMover().nextLeft(), this.getY())) ){
            this.leftBranch = null;
        } else {
            this.leftBranch = new Branch(this.getMover().nextLeft(), this.getY());
            branches.add(leftBranch);
        }
    }

    public void setRight(int posX, int posY){
        if(fakeSmartMover.nextRight() == this.getX()){
            this.rightBranch = null;
        } else {
            this.rightBranch = new Branch(this.getMover().nextRight(), this.getY());
            branches.add(rightBranch);
        }
    }

    public void setDown(int posX, int posY){
        if(fakeSmartMover.nextDown() == this.getY()){
            this.downBranch = null;
        } else {
            this.downBranch = new Branch(this.getX(), this.getMover().nextDown());
            branches.add(downBranch);
        }
    }

    public void setUp(int posX, int posY){
        if(fakeSmartMover.nextUp() == this.getY()){
            this.upBranch = null;
        } else {
            this.upBranch = new Branch(this.getX(), this.getMover().nextUp());
            branches.add(upBranch);
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
     * Returns branches
     * @return the sub-branches connected to this branch.
     */
    public ArrayList<Branch> getBranches(){
        this.fakeSmartMover = null;
        return this.branches;
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
     * @return the ooordinates to follow the path
     */
    public ArrayList<Integer> getPath(){
        return this.path;
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
}
