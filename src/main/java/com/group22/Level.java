package com.group22;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.time.LocalDateTime;
import java.util.ArrayList;

/**
 * The Level class is a class that represents a level in the game. It contains the level's title, time
 * to complete, height, width, tiles, entities and scores and implements getters for all of the above.
 * 
 * @author Sam Austin
 * @version 1.0
 */
public class Level {
    private String title;
    private int timeToComplete;
    private int height;
    private int width;
    private Tile[][] tiles;
    private ArrayList<String[]> entities;
    private String[][] scores;
    private boolean playerPresent = false;
    private boolean doorPresent = false;    
    private int levelIndex;

    public Level(String title, int timeToComplete, int height,
    int width, Tile[][] tiles, ArrayList<String[]> entities,
    String[][] scores, int levelIndex){
        this.title = title;
        this.timeToComplete = timeToComplete;
        this.height = height;
        this.width = width;
        this.tiles = tiles;
        this.entities = entities;
        this.scores = scores;
        this.levelIndex = levelIndex;
    }

    public Player getPlayerFromEntities(ArrayList<Entity> entities){
        for(Entity entity : entities){
            if(entity instanceof Player){
                return (Player) entity;
            }
        }
        return null;
    }
    /** 
     * Getter for the level's title
     * @return String
     */
    public String getTitle(){
        return title;
    }

    /** 
     * Getter for the level index
     * @return String
     */
    public int getIndex(){
        return levelIndex;
    }
    
    /** 
     * Getter for the total time to complete the level at the begining
     * @return int
     */
    public int getTimeToComplete(){
        return timeToComplete;
    }

    /** 
     * This will change due to the requirement of player profiles.....
     * @return String[][]
     */
    public String[][] getHighscores(){
        return scores;
    }

    /**
     * @param score A String array with the score and name of the player.
     * @return The position of the score in the highscores array.
     */
    public int getScorePosition(int score){
        String[][] levelHighscores = this.getHighscores();
        if(levelHighscores.length == 0){
            return 1;
        }
        for(int i = 0; i < 10; i++){
            if(Integer.parseInt(levelHighscores[i][1]) < score)
                return i + 1;
        }
        return 11;
    }

    public void completeLevel(Profile profile, int score){
        int profileUnlockedIndex = profile.getMaxUnlockedLevelIndex();
        if(profileUnlockedIndex == this.getIndex()){
            profile.setUnlockedLevelIndex(this.getIndex()+1);
        }
        int scorePos = this.getScorePosition(score);
        if(scorePos <= 10){
            this.writeScore(profile, score, scorePos);
        }
    }

    public void writeScore(Profile profile, int score, int scorePos){
        if(scorePos <= 10){
            LevelLoader levelLoader = new LevelLoader();
            boolean scoreSet = false;
            int fileLevelIndex = 0;
            try{
                ArrayList<String> levelData = levelLoader.getLevelFileData();
                BufferedWriter wr = new BufferedWriter(
                    new FileWriter(levelLoader.getLevelFile(), false));
                for(int i = 0; i < levelData.size(); i++){
                    if(levelData.get(i).equals("-")){
                        fileLevelIndex = fileLevelIndex + 1;
                        if(fileLevelIndex > 0){
                            i += 1;
                        }
                    }
                    if(this.getIndex() == fileLevelIndex && !scoreSet){
                        int tileNumIndex = i + 2;
                        int tileEndIndex = Integer.parseInt(
                            levelData.get(tileNumIndex).split(" ")[1])
                            + tileNumIndex + 1;
                        System.out.println(tileEndIndex);
                        int entityEndIndex = Integer.parseInt(
                            levelData.get(tileEndIndex)) + tileEndIndex;
                        System.out.println(entityEndIndex);
                        int numScoresIndex = entityEndIndex + 1;
                        int numScores = Integer.parseInt(
                            levelData.get(numScoresIndex));
                        System.out.println(numScoresIndex);
                        String scoreToInsert = profile.getName() + " "
                            + String.valueOf(score) + " " 
                            + LocalDateTime.now().toString();
                        System.out.println(scoreToInsert);
                        if(numScores == 10){
                            levelData.add(numScoresIndex + scorePos, 
                            scoreToInsert);
                            levelData.remove(numScoresIndex + numScores + 1);
                            scoreSet = true;
                        } else {
                            levelData.set(numScoresIndex, String.valueOf(numScores + 1));
                            levelData.add(numScoresIndex + scorePos,
                            scoreToInsert);
                            scoreSet = true;
                        }
                    }
    
                }
                for(int i = 0; i<levelData.size(); i++){
                    wr.write(levelData.get(i) + "\n");
                }
                wr.close();
    
                } catch(Exception e){
                    System.out.println(e.getMessage());
    
            }
        }
    }

    
    /** 
     * Getter for retrieving the Tile objects for the level
     * @return Tile[][]
     */
    public Tile[][] getTiles(){
        return tiles;
    }

    
    /** 
     * Getter for retrieving an ArrayList of entities. This may change
     * @return List<? super Entity>
     */
    public ArrayList<String[]> getEntities(){
        return entities;
    }

    
    /** 
     * Getter for retrieving the height of the level
     * @return int
     */
    public int getHeight(){
        return height;
    }

    
    /** 
     * Getter for retrieving the width of the level
     * @return int
     */
    public int getWidth(){
        return width;
    }

    private void isValidEntity(Entity entity) throws Exception{
        if(entity instanceof Player){
            this.playerPresent = true;
        }
        if(entity instanceof Door){
            this.doorPresent = true;
        }
        if(entity.getX() > this.width-1)
            throw new LevelFormatException("Entity out of bounds in x");
        if(entity.getY() > this.height-1)
            throw new LevelFormatException("Entity out of bounds in y");
    }

    private void isValidLevel() throws LevelFormatException{
        if(!this.playerPresent)
            throw new LevelFormatException("Player not present for level " + this.getTitle());
        if(!this.doorPresent)
            throw new LevelFormatException("Door not present for level " + this.getTitle());
    }

    public ArrayList<Entity> createEntities() throws Exception {
        ArrayList<Entity> entities = new ArrayList<>();

        entities.addAll(this.tilesToEntities());
        
        for(String[] entityData : this.getEntities()) {
            Entity entity = parseEntity(entityData);
            isValidEntity(entity);
            entities.add(entity);
        }
        isValidLevel();
        
        return entities;
    }

    private ArrayList<Entity> tilesToEntities(){
        ArrayList<Entity> entities = new ArrayList<Entity>();

        for(Tile[] yTiles : tiles){
            for(Tile xTiles : yTiles){
                entities.add(xTiles);
            }
        }
        return entities;
    }

    private Entity parseEntity(String[] entity){
        switch(entity[0]){
            case("player"):
                return new Player(
                    Integer.parseInt(entity[1]),
                    Integer.parseInt(entity[2]),
                    entity.length >= 4 && entity[3].equals("torch"));
            case("door"):
                return new Door(
                    Integer.parseInt(entity[1]),
                    Integer.parseInt(entity[2]));
            case("clock"):
                return new Clock(
                    Integer.parseInt(entity[1]),
                    Integer.parseInt(entity[2]),
                    Integer.parseInt(entity[3])
                    );
            case("bomb"):
                return new Bomb(
                    Integer.parseInt(entity[1]),
                    Integer.parseInt(entity[2]),
                    Integer.parseInt(entity[3])
                    );
            case("followingthief"):
                return new FollowingThief(
                    Integer.parseInt(entity[1]),
                    Integer.parseInt(entity[2]), 
                    null);
            case("lever"):
                return new Lever(
                    Integer.parseInt(entity[1]),
                    Integer.parseInt(entity[2]),
                    entity[3]);
            case("gate"):
                return new Gate(
                    Integer.parseInt(entity[1]),
                    Integer.parseInt(entity[2]),
                    entity[3]);
            case("loot"):
                return new Loot(
                    Integer.parseInt(entity[1]),
                    Integer.parseInt(entity[2]),
                    Integer.parseInt(entity[3]));
            case("flyingassassin"):
                return new FlyingAssassin(
                    Integer.parseInt(entity[1]),
                    Integer.parseInt(entity[2]),
                    entity[3].equals("v"));
            case("smartmover"):
                return new SmartMover(
                    Integer.parseInt(entity[1]),
                    Integer.parseInt(entity[2]));

        }
        return null;
    }
}
