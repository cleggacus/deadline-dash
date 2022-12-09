package com.group22;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

/**
 * The SmartMover class handles the moving of the smart mover, using paths
 * discovered with its branches through the branch class. The smart mover can
 * also change its path if the pickup it is after is blown up or collected.
 * @author Lewis Meekings
 * @version 1.0
 */
public class SmartMover extends LandMover {

    private ArrayList<Integer> path = new ArrayList<>();
    /**
     * The constructor for the smartmover entity class.
     * @param posX The horizontal position of this entity
     * @param posY The vertical position of this entity
     */
    public SmartMover(int posX, int posY) {
        super(posX, posY);
        this.getSprite().setImage("NPC/SmartMover.png");
        this.moveEvery = 0.45;
    }

    /**
     * Finds a path for this landmover. It does this using the branch class to
     * look for every possible path.
     */
    protected void findPath() {
        ArrayList<ArrayList<Integer>> paths = new ArrayList<>();
        this.path = new ArrayList<>();
        Boolean targetFound = false;
        Branch root = new Branch(this.getX(), this.getY(), new ArrayList<>());
        root.clear();
        root.addBranch(root);

        ArrayList<Branch> currentBranches = new ArrayList<>();
        ArrayList<Branch> newBranches = new ArrayList<>();
        currentBranches.add(root);

        while (!targetFound && !currentBranches.isEmpty()) {
            for (Branch branch : currentBranches) {
                if (branch.isTarget()) {
                    targetFound = true;
                }
                ArrayList<Branch> connections = new ArrayList<>();
                Branch left = new Branch(
                    branch.getLeft(), branch.getY(), branch.getPath());
                Branch right = new Branch(
                    branch.getRight(), branch.getY(), branch.getPath());
                Branch up = new Branch(
                    branch.getX(), branch.getUp(), branch.getPath());
                Branch down = new Branch(
                    branch.getX(), branch.getDown(), branch.getPath());
                connections.add(left);
                connections.add(right);
                connections.add(up);
                connections.add(down);

                for (Branch subBranch : connections) {
                    if (subBranch.isUniqueBranch()) {
                        newBranches.add(subBranch);
                        subBranch.addBranch(subBranch);
                        if (subBranch.isTarget()) {
                            targetFound = true;
                        }
                    }
                }
            }
            currentBranches = new ArrayList<>(newBranches);
            newBranches = new ArrayList<>();
        }
        
        paths = Branch.getPaths();
        if (paths.isEmpty()) {
                randomMove();
        } else {
            removeBiggerPaths(paths);
            ArrayList<PickUp> pickups = Game.getInstance().getEntities(
                PickUp.class);
            if (pickups.isEmpty() || paths.size() == 1) {
                path = new ArrayList<>(paths.get(0));
            } else {
                path = new ArrayList<>(reduceTargets(paths));
            }
            path.remove(0);
            path.remove(0);
        }
    }

    /**
     * Will call to randomly move left, right, up or down until it gets a valid
     * movement.
     */
    protected void randomMove() {
        while (path.isEmpty()) {
            Random rngNum = new Random();
            int rngMove = rngNum.nextInt(4);
            switch (rngMove) {
                case 0:
                    if (nextLeft() != this.getX() && 
                        !(isBlocked(nextLeft(), this.getY()))) {
                        path.add(nextLeft());
                        path.add(this.getY());
                    }
                    break;
                case 1:
                    if (nextRight() != this.getX() && 
                        !(isBlocked(nextRight(), this.getY()))) {
                        path.add(nextRight());
                        path.add(this.getY());
                    }
                    break;
                case 2:
                    if (nextUp() != this.getY() && 
                        !(isBlocked(this.getX(), nextUp()))) {
                        path.add(this.getX());
                        path.add(this.nextUp());
                    }
                    break;
                case 3:
                    if (nextDown() != this.getX() && 
                        !(isBlocked(this.getX(), nextDown()))) {
                        path.add(this.getX());
                        path.add(this.nextDown());
                    }
                    break;
            }
        }
    }

    /**
     * A method to remove bigger paths, though in theory all paths returned are
     * the same length. This is just backup in case we return variable length
     * paths.
     * @param paths The array of paths to remove bigger paths from.
     */
    protected void removeBiggerPaths(ArrayList<ArrayList<Integer>> paths) {
        Boolean shouldRemove = false;
            int i = 0;
            for (Iterator<ArrayList<Integer>> iter = 
                paths.iterator(); iter.hasNext();) {
                if (shouldRemove == true){
                    iter.remove();
                    i--;
                }
                shouldRemove = false;
                for (ArrayList<Integer> pathA : paths) {
                    if (paths.get(i).size() > pathA.size()) {
                        shouldRemove = true;
                    }
                }
                iter.next();
                i++;
            }

            if (shouldRemove == true) {
                paths.remove(paths.size() - 1);
            }
    }

    /**
     * Checks the destination of each path to get its pickup and puts them into
     * an array of collectibles. Then if there is loot in the pickup list it
     * will prioritise the loot and chuck out the rest. If there are different
     * loot variants it will prioritise the most valuable, chucking out the rest
     * . Since the paths have already been sorted by length there is no need to
     * do this here.
     * @param paths the array of paths passed to this function. It must reduce
     * paths until all paths have equal priority.
     */
    protected ArrayList<Integer> reduceTargets(
        ArrayList<ArrayList<Integer>> paths) {
        ArrayList<PickUp> pickups = 
            Game.getInstance().getEntities(PickUp.class);
        ArrayList<PickUp> targetPickUps = new ArrayList<>();
        for (ArrayList<Integer> pathA : paths) {
            for (PickUp pickup : pickups) {
                if (pickup.getX() == pathA.get(pathA.size() - 2) && 
                    pickup.getY() == pathA.get(pathA.size() - 1)) {
                    targetPickUps.add(pickup);
                }
            }
        }

        int i = 0;

        Boolean shouldRemove = false;
        for (Iterator<ArrayList<Integer>> iter = 
            paths.iterator(); iter.hasNext();) {
            if (shouldRemove == true) {
                    iter.remove();
                    i--;
                    targetPickUps.remove(i);
            }

            shouldRemove = false;
            for (PickUp pickup : targetPickUps) {
                PickUp pickup1 = targetPickUps.get(i);
                PickUp pickup2 = pickup;
                if (!(pickup1 instanceof Loot) && pickup2 instanceof Loot) {
                    shouldRemove = true;
                } else if (pickup1 instanceof Loot && pickup2 instanceof Loot) {
                    Loot loot1 = (Loot) pickup1;
                    Loot loot2 = (Loot) pickup2;
                    if (loot1.getValue() < loot2.getValue()) {
                        shouldRemove = true;
                    }
                }
            }

            iter.next();
            i++;
        }

        if (shouldRemove == true) {
            paths.remove(paths.size() - 1);
        }

        return paths.get(0);
    }

    /**
     * Method for altering the smart movers path on the fly for example if the
     * player take the loot its after. Its not core functionality for the
     * class but I wanted to include it in the project.
     * @return Whether the item the smart mover is after still exists.
     */
    protected Boolean alterPath() {
        ArrayList<Entity> entities = new ArrayList<>(
            Game.getInstance().getEntities());
        Boolean shouldRemove = false;
        int i = 0;
        for (Iterator<Entity> iter = entities.iterator(); iter.hasNext();) {
            if (shouldRemove == true) {
                iter.remove();
                i--;
            }

            shouldRemove = false;
            Entity entity = entities.get(i);
            if (entity.getX() != path.get(path.size() - 2) || 
                entity.getY() != path.get(path.size() - 1)) {
                shouldRemove = true;
            }

            iter.next();
            i++;
        }
        if (shouldRemove == true) {
            entities.remove(entities.size() - 1);
        }

        for (Entity entity : entities) {
            if (entity instanceof PickUp || entity instanceof Door) {
                return false;
            }
        }

        return true;
    }

    private void isAtDoor() {
        ArrayList<PickUp> pickups
        = new ArrayList<>(Game.getInstance().getEntities(Loot.class));
        pickups.addAll(Game.getInstance().getEntities(Lever.class));
        if (pickups.isEmpty() && 
            this.getX() == Game.getInstance().getDoor().getX() && 
            this.getY() == Game.getInstance().getDoor().getY()) {
            Game.getInstance().setGameOver();
        }
    }
    
    /**
     * Updates the movement of the smart mover, if it currently doesn't have a
     * path it will call findPath() to generate one.
     */
    @Override
    protected void updateMovement() {
        if (path.isEmpty()) {
            isAtDoor();
            findPath();
        } else if (alterPath()) {
            findPath();
        }

        int destinationX = path.get(0);
        int destinationY = path.get(1);
        move(destinationX - this.getX(), destinationY - this.getY());
        
        if (this.getX() == destinationX && this.getY() == destinationY) {
            path.remove(0);
            path.remove(0);
        }
    }

    @Override
    protected void update() {}
    
}
