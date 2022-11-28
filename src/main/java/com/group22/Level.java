package com.group22;
public class Level {
    private boolean locked;
    private String title;
    private int height;
    private int width;
    private String[][] tiles;
    private String[][] entities;
    private String[][] scores;
    /*
    TBC.....

    private Tile[] tiles;
    private Entity[] entities;
    private Score[] scores;*/

    public Level(String title, boolean locked, int height, int width,  String[][] tiles, String[][] entities, String[][] scores){
        this.title = title;
        this.locked = locked;
        this.height = height;
        this.width = width;
        this.tiles = tiles;
        this.entities = entities;
        this.scores = scores;
    }

    /*
    TBC.....
    
    public Tile[] getTiles(){
        return tiles;
    }

    public Entity[] getEntities(){
        return entities;
    }

    public Score[] getScores(){
        return scores;
    }*/

    public String getTitle(){
        return title;
    }

    public boolean getIsLocked(){
        return locked;
    }

    public String[][] getHighscores(){
        return scores;
    }

    public String[][] getTiles(){
        return tiles;
    }

    public String[][] getEntities(){
        return entities;
    }

    public int getHeight(){
        return height;
    }

    public int getWidth(){
        return width;
    }
    /* setters to be implemented?? */
}
