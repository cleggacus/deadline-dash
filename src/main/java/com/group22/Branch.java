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
     * The connections  from the current branch, at most one per direction, right,
     * left, down, up.
     */
    private int leftConnection;
    private int rightConnection;
    private int downConnection;
    private int upConnection;

    /**
     * An arraylist containing any branch that has already been used.
     */

    private static ArrayList<Branch> existingBranches = new ArrayList<>();

    /**
     * An array list containing all the coordinates to reach the current tile.
     */
    
    private ArrayList<Integer> path;
    private static ArrayList<ArrayList<Integer>> targetPaths;
    /**
     * Constructor for the branch, constructs branches of this branch, and adds
     * the branch coordinates towards the path. Will not construct additional
     * branches if a target has been found on the path.
     * @param posX the horizontal position of this branch.
     * @param posY the vertical position of this branch.
     */
    public Branch(int posX, int posY, ArrayList<Integer> currentPath){
        this.branchX = posX;
        this.branchY = posY;
        this.path = new ArrayList<>(currentPath);
        this.path.add(posX);
        this.path.add(posY);

        if(isTarget()){
            targetPaths.add(this.path);
        }
        this.setLeft();
        this.setRight();
        this.setDown();
        this.setUp();
    }

    /**
     * Constructs the left branch of this branch if you can move to it legally
     * and it hasn't been branched to already.
     */
    private void setLeft(){
        boolean found = false;
        int i = this.getX();

        while(i > 0 && !found) {
            i--;
            found = isMoveLegal(i - this.getX(), 0);
        }

        if(!found) {
            this.leftConnection = this.getX();
        } else {
            this.leftConnection = i;
        }
        
    }

    /**
     * Constructs the right branch of this branch if you can move to it legally
     * and it hasn't been branched to already.
     */
    private void setRight(){
        boolean found = false;
        int i = this.getX();
        int width = Game.getInstance().getViewWidth();

        while(i < width-1 && !found) {
            i++;
            found = isMoveLegal(i - this.getX(), 0);
        }

        if(!found) {
            this.rightConnection = this.getX();
        } else {
            this.rightConnection = i;
        }
    }

    /**
     * Constructs the down branch of this branch if you can move to it legally
     * and it hasn't been branched to already.
     */
    private void setDown(){
        boolean found = false;
        int i = this.getY();
        int height = Game.getInstance().getViewHeight();

        while(i < height-1 && !found) {
            i++;
            found = isMoveLegal(0, i - this.getY());
        }

        if(!found) {
            this.downConnection = this.getY();
        } else {
            this.downConnection = i;
        }
    }

    /**
     * Constructs the up branch of this branch if you can move to it legally
     * and it hasn't been branched to already.
     */
    private void setUp(){

        boolean found = false;
        int i = this.getY();

        while(i > 0 && !found) {
            i--;
            found = isMoveLegal(0, i - this.getY());
        }

        if(!found) {
            this.upConnection = this.getY();
        } else {
            this.upConnection = i;
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
    public static ArrayList<ArrayList<Integer>> getPaths(){
        return Branch.targetPaths;
    }

    public int getLeft(){
        return leftConnection;
    }

    public int getRight(){
        return rightConnection;
    }

    public int getDown(){
        return downConnection;
    }

    public int getUp(){
        return upConnection;
    }


    /**
     * Compares a branch to the existing branches, if they have the same
     * coordinates the branch is not unique.
     * @param newBranch The branch candidate being compared
     * @return Whether the branch is in existing branches already
     */
    public Boolean isUniqueBranch(){
        Boolean isUnique = true;
        for (Branch branch : existingBranches){
            if(this.getX() == branch.getX() && this.getY() == branch.getY()){
                isUnique = false;
            }
        }
        return isUnique;
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
    public Boolean isTarget(){
        ArrayList<PickUp> pickups = Game.getInstance().getEntities(PickUp.class);
        ArrayList<Door> doors = Game.getInstance().getEntities(Door.class);
        if(pickups.isEmpty()){
            for (Door door : doors){
                if (door.getX() == this.getX() && door.getY() == this.getY()){
                    return true;
                }
            }
        } else {
            for (PickUp pickup : pickups){
                if (pickup.getX() == this.getX() && pickup.getY() == this.getY()){
                    return true;
                }
            }
        }
        return false;
    }

    public void addBranch(Branch newBranch){
        existingBranches.add(newBranch);
    }

    public ArrayList<Branch> getBranches(){
        return existingBranches;
    }
    /**
     * Clears the static target paths array which is used to store valid paths.
     */
    public void clear(){
        targetPaths = new ArrayList<>();
        existingBranches = new ArrayList<>();
    }
}
