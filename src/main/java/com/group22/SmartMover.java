package com.group22;

import java.util.ArrayList;

public class SmartMover extends LandMover{

    ArrayList<Integer> path;
    public SmartMover(int posX, int posY){
        super(posX, posY);
        this.getSprite().setImage("NPC/SmartMover.PNG");
    }

    public void findPath(){
        Branch originBranch = new Branch(this.getX(), this.getY());
        ArrayList<ArrayList<Integer>> paths = originBranch.getPaths();
        if(paths.isEmpty()){
            //path = new ArrayList<>(RNG move);
        } else {
            for(ArrayList<Integer> pathA : paths){
                for(ArrayList<Integer> pathB : paths){
                    if(pathA.size() < pathB.size()){
                        paths.remove(pathB);
                    }
                }
            }
    
            ArrayList<PickUp> pickups = Game.getInstance().getEntities(PickUp.class);
            if(pickups.isEmpty() || paths.size() == 1){
                path = new ArrayList<>(paths.get(0));
            } else {
                path = new ArrayList<>(reduceTargets(paths));
            }
        }
    }

    public ArrayList<Integer> reduceTargets(ArrayList<ArrayList<Integer>> paths){
        ArrayList<PickUp> allPickups =
        Game.getInstance().getEntities(PickUp.class);
        ArrayList<PickUp> targetPickups = new ArrayList<>();
        ArrayList<Loot> targetLoots = new ArrayList<>();
        for(ArrayList<Integer> path : paths){
            for (PickUp pickup : allPickups){
                if(pickup.getX() == path.get(path.size() - 2) &&
                pickup.getY() == path.get(path.size() - 1)){
                    targetPickups.add(pickup);
                }
            }
        }

        for(PickUp pickup : targetPickups){
            if(pickup instanceof Loot){
                targetLoots.add((Loot) pickup);
            }
        }

        if(targetLoots.isEmpty()){
            return paths.get(0);
        } else {
            int i = 0;
            for(PickUp pickup : targetPickups){
                if(pickup instanceof Loot){
                    
                } else {
                    targetPickups.remove(pickup);
                    paths.remove(i);
                }
                i++;
            }
            
            for(Loot lootA : targetLoots){
                i = 0;
                for(Loot lootB : targetLoots){
                    if(lootA.getValue() > lootB.getValue()){
                        targetLoots.remove(lootB);
                        paths.remove(i);
                    }
                    i++;
                }
            }
            
            return paths.get(0);
        }
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
