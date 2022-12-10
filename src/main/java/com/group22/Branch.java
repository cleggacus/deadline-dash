package com.group22;

import java.util.ArrayList;


/**
 * The Branch class handles the paths of the smart mover. It stores only its own
 * path, paths with a target on and potential new branches it is connected to.
 * Has a method to test each branch for uniqueness.
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
     * The connections  from the current branch, at most one per direction,
     * right, left, down, up.
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

    /**
     * An array list of array lists, every array list in it is a path.
     */
    private static ArrayList<ArrayList<Integer>> targetPaths;

    /**
     * This code sets colliders for the class, if it sees a static obstruction
     * like a bomb or gate it will not include those tiles in the path.
     */
    @SuppressWarnings("unchecked")
    private static final Class<? extends Entity>[] COLLIDERS = new Class[] {
        Bomb.class,  
        Gate.class
    };

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

        if (isTarget()){
            targetPaths.add(this.path);
        }
        this.setLeft();
        this.setRight();
        this.setDown();
        this.setUp();
    }

    /**
     * Creates the left connection of this branch if you can move to it
     * legally.
     */
    private void setLeft(){
        boolean found = false;
        int i = this.getX();

        while (i > 0 && !found) {
            i--;
            found = isMoveLegal(i - this.getX(), 0);
        }

        if (!found || isBlocked(i, this.getY())) {
            this.leftConnection = this.getX();
        } else {
            this.leftConnection = i;
        }
        
    }

    /**
     * Creates the right connection of this branch if you can move to it
     * legally.
     */
    private void setRight(){
        boolean found = false;
        int i = this.getX();
        int width = Game.getInstance().getViewWidth();

        while (i < width - 1 && !found) {
            i++;
            found = isMoveLegal(i - this.getX(), 0);
        }

        if (!found || isBlocked(i, this.getY())) {
            this.rightConnection = this.getX();
        } else {
            this.rightConnection = i;
        }
    }

    /**
     * Creates the dpwn connection of this branch if you can move to it
     * legally.
     */
    private void setDown(){
        boolean found = false;
        int i = this.getY();
        int height = Game.getInstance().getViewHeight();

        while (i < height - 1 && !found) {
            i++;
            found = isMoveLegal(0, i - this.getY());
        }

        if (!found || isBlocked(this.getX(), i)) {
            this.downConnection = this.getY();
        } else {
            this.downConnection = i;
        }
    }

    /**
     * Creates the up connection of this branch if you can move to it
     * legally.
     */
    private void setUp(){

        boolean found = false;
        int i = this.getY();

        while (i > 0 && !found) {
            i--;
            found = isMoveLegal(0, i - this.getY());
        }

        if (!found || isBlocked(this.getX(), i)) {
            this.upConnection = this.getY();
        } else {
            this.upConnection = i;
        }
    }

    /**
     * Returns coordinates.
     * @return the X coordinates of this branch.
     */
    public int getX(){
        return this.branchX;
    }

    /**
     * Returns coordinates.
     * @return the Y coordinates of this branch.
     */
    public int getY(){
        return this.branchY;
    }

    /**
     * Returns a path.
     * @return the coordinates to follow the path.
     */
    public ArrayList<Integer> getPath(){
        return this.path;
    }

    /**
     * Returns all paths that reach a valid target, a valid target being a
     * pickup or the door if there are no pickups on the field.
     * @return The list of paths that reach a valid target.
     */
    public static ArrayList<ArrayList<Integer>> getPaths(){
        return Branch.targetPaths;
    }

    /**
     * Gets the left connection of a branch.
     * @return the left connection of the branch.
     */
    public int getLeft(){
        return leftConnection;
    }

    /**
     * Gets the right connection of a branch.
     * @return the left connection of the branch.
     */
    public int getRight(){
        return rightConnection;
    }

    /**
     * Gets the down connection of a branch.
     * @return the left connection of the branch.
     */
    public int getDown(){
        return downConnection;
    }

    /**
     * Gets the up connection of a branch.
     * @return the left connection of the branch.
     */
    public int getUp(){
        return upConnection;
    }


    /**
     * Compares a branch to the existing branches, if they have the same
     * coordinates the branch is not unique.
     * @return Whether the branch is in existing branches already
     */
    public Boolean isUniqueBranch(){
        Boolean isUnique = true;
        for (Branch branch : existingBranches){
            if (this.getX() == branch.getX() && this.getY() == branch.getY()){
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
        if (!Game.getInstance().isInBounds(this.getX() + x, this.getY() + y))
            return false;

        return Game.getInstance().colorMatch(this.getX()
        , this.getY(), this.getX() + x, this.getY() + y);
    }
    
    /**
     * Evaluates whether the current branch has a target on it, and if so
     * extracts that branchs path to target paths.
     * @return whether the target was found on the branch.
     */
    public Boolean isTarget(){
        ArrayList<PickUp> pickups
        = new ArrayList<>(Game.getInstance().getEntities(Loot.class));
        pickups.addAll(Game.getInstance().getEntities(Lever.class));
        if (pickups.isEmpty()){
            if (this.getX() == Game.getInstance().getDoor().getX()
            && this.getY() == Game.getInstance().getDoor().getY()){
                return true;
            }
        } else {
            for (PickUp pickup : pickups){
                if (pickup.getX() == this.getX()
                && pickup.getY() == this.getY()){
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Adds branches to the array of existing branches.
     * @param newBranch the Branch being added.
     */
    public void addBranch(Branch newBranch){
        existingBranches.add(newBranch);
    }

    /**
     * Gets every unique branch stored.
     * @return the stored unique branches.
     */
    public ArrayList<Branch> getBranches(){
        return existingBranches;
    }

    /**
     * Clears the static target paths array which is used to store valid paths.
     * Also clears the existing branches.
     */
    public void clear(){
        targetPaths = new ArrayList<>();
        existingBranches = new ArrayList<>();
    }

    /**
     * Checks whether the destination is blocked.
     * @param x The destinations X coordinates.
     * @param y The destinations Y coordinates.
     * @return If the destination has been blocked by a bomb or gate.
     */
    protected boolean isBlocked(int x, int y){
        for (Class<? extends Entity> entityClass : COLLIDERS) {
            ArrayList<? extends Entity> entities
            = Game.getInstance().getEntities(entityClass);

            for (Entity entity : entities){
                if (entity instanceof Gate && ((Gate) entity).getGateIsOpen()){
                } else if (x ==  entity.getX() && y == entity.getY()){
                    return true;
                }
                }
            }
        return false;
    }
}
