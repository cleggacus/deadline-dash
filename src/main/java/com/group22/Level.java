package com.group22;

import java.util.List;

public class Level {
    private int timeToComplete;
    private String title;
    private int height;
    private int width;
    private Tile[][] tiles;
    private List<? super Entity> entities;
    private String[][] scores;
    
    
    public Level(String title, int timeToComplete, int height, int width,  String[][] tiles, String[][] entities, String[][] scores){
        this.title = title;
        this.timeToComplete = timeToComplete;
        this.height = height;
        this.width = width;
        this.tiles = parseTiles(tiles);
        this.entities = parseEntities(entities);
        this.scores = scores;
    }

    private Tile[][] parseTiles(String[][] tiles){
        Tile[][] levelTiles = new Tile[width][height];

        for(int y = 0; y < height; y++) {
            for(int x = 0; x < width; x++) {
                levelTiles[x][y] = new Tile(x, y, tiles[x][y]);
            }
        }
        return levelTiles;
    }

    private List<Entity> parseEntities(String[][] entities){
        for(int i=0; i<entities.length; i++){
            switch(entities[i][0]){
                case("player"):
                    /* Awaiting Player class implementation

                    this.entities.add(new Player(
                        entities[i][1],
                        entities[i][2]
                        ));
                    */
                    break;
                case("door"):
                    /* Awaiting Door class implementation

                    this.entities.add(new Door(
                        Integer.parseInt(entities[i][1]),
                        Integer.parseInt(entities[i][2]),
                        new Image("door_closed.png")));
                    */
                    break;
                case("clock"):
                    /* Awaiting Clock class implementation

                    this.entities.add(new Clock(
                        Integer.parseInt(entities[i][1]),
                        Integer.parseInt(entities[i][2]),
                        Integer.parseInt(entities[i][3])
                        ));
                    */
                    break;
                case("bomb"):
                    /* Awaiting Bomb class implementation

                    this.entities.add(new Bomb(
                        Integer.parseInt(entities[i][1]),
                        Integer.parseInt(entities[i][2]),
                        Integer.parseInt(entities[i][3])
                        ));
                    */
                    break;
                case("followingthief"):
                    /* Awaiting FollowingThief class implementation

                    this.entities.add(new FollowingThief(
                        Integer.parseInt(entities[i][1]),
                        Integer.parseInt(entities[i][2]),
                        new Sprite("character/following_thief.png"),
                        entities[i][3]
                        ));
                    */
                    break;
                case("loot"):
                    /* Awaiting Loot class implementation

                    this.entities.add(new Loot(
                        Integer.parseInt(entities[i][1]),
                        Integer.parseInt(entities[i][2]),
                        new Sprite("item/{entities[i][0]}.png"),
                        Integer.parseInt(entities[i][3])
                        ));
                    */
                    break;

            }
        }

        return null;
    }

    /*
    TBC.....
    public Entity[] getEntities(){
        return entities;
    }

    public Score[] getScores(){
        return scores;
    }*/

    public String getTitle(){
        return title;
    }

    public int getTimeToComplete(){
        return timeToComplete;
    }

    public String[][] getHighscores(){
        return scores;
    }

    public Tile[][] getTiles(){
        return tiles;
    }

    public List<? super Entity> getEntities(){
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
