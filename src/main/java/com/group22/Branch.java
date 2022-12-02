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
     * the branch coordinates towards the path. Will not construct additional
     * branches if a target has been found on the path.
     * @param posX the horizontal position of this branch.
     * @param posY the vertical position of this branch.
     */
    public Branch(int posX, int posY){
        this.branchX = posX;
        this.branchY = posY;
        existingBranches.add(this);
        path.add(posX, posY);
        if(!isTarget(this)){
            this.setLeft();
            this.setRight(posX, posY);
            this.setDown(posX, posY);
            this.setUp(posX, posY);
        }
    }

    /**
     * Constructs the left branch of this branch if you can move to it legally
     * and it hasn't been branched to already.
     */
    public void setLeft(){
        boolean found = false;
        int i = this.getX();

        while(i > 0 && !found) {
            i--;
            found = isMoveLegal(i - this.getX(), 0);
        }

        if(!found || compareBranch(i, this.getY())) {
            this.leftBranch = null;
        } else {
            this.leftBranch = new Branch(i, this.getY());
        }
        
    }

    /**
     * Constructs the right branch of this branch if you can move to it legally
     * and it hasn't been branched to already.
     */
    public void setRight(int posX, int posY){

        boolean found = false;
        int i = this.getX();

        while(i > 0 && !found) {
            i++;
            found = isMoveLegal(i - this.getX(), 0);
        }

        if(!found || compareBranch(i, this.getY())) {
            this.rightBranch = null;
        } else {
            this.rightBranch = new Branch(i, this.getY());
        }
    }

    /**
     * Constructs the down branch of this branch if you can move to it legally
     * and it hasn't been branched to already.
     */
    public void setDown(int posX, int posY){

        boolean found = false;
        int i = this.getX();

        while(i > 0 && !found) {
            i--;
            found = isMoveLegal(0, i - this.getY());
        }

        if(!found || compareBranch(this.getX(), i)) {
            this.downBranch = null;
        } else {
            this.downBranch = new Branch(this.getX(), i);
        }
    }

    /**
     * Constructs the up branch of this branch if you can move to it legally
     * and it hasn't been branched to already.
     */
    public void setUp(int posX, int posY){

        boolean found = false;
        int i = this.getX();

        while(i > 0 && !found) {
            i++;
            found = isMoveLegal(0, i - this.getY());
        }

        if(!found || compareBranch(this.getX(), i)) {
            this.upBranch = null;
        } else {
            this.upBranch = new Branch(this.getX(), i);
        }
    }

    /**
     * Returns coordinates
     * @return the X coordinates of this branch
     */
    private int getX(){
        return this.branchX;
    }

    /**
     * Returns coordinates
     * @return the Y coordinates of this branch
     */
    private int getY(){
        return this.branchY;
    }

    /**
     * Returns a path
     * @return the coordinates to follow the path
     */
    private ArrayList<Integer> getPath(){
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
    private Boolean compareBranch(int x, int y){
        Boolean isBranch = false;
        for (Branch branch : existingBranches){
            if(x == branch.getX() && y == branch.getY()){
                isBranch = true;
            }
        }
        return isBranch;
    }

    /**
     * Returns whether move (x, y) is legal according to tile colors.
     * 
     * @param x
     *      Change is x from current position to check.
     * 
     * @param y
     *      Change is y from current position to check.
     * 
     * @return
     *      If the x and y added to the current position is a valid colour.
     */
    private boolean isMoveLegal(int x, int y) {
        if(!Game.getInstance().isInBounds(this.getX() + x, this.getY() + y))
            return false;

        return Game.getInstance().colorMatch(this.getX(), this.getY(), this.getX() + x, this.getY() + y);
    }
    
    /**
     * Evaluates whether the current branch has a target on it, and if so
     * extracts that branchs path to target paths.
     * @param newBranch the branch in question.
     * @return whether the target was found on the branch.
     */
    private Boolean isTarget(Branch newBranch){
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

    /**
     * Clears the static target paths array which is used to store valid paths.
     */
    public void clearPaths(){
        targetPaths.clear();
    }
}
