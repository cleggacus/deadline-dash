package com.group22;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class LevelLoader {

    public LevelLoader(){
    }
    private String levelFile = "src/main/resources/com/group22/levels.txt";
    
    /**
     * A recursive method that takes a list of strings, and returns a list of levels.
     * 
     * @param levelData A list of strings, each string is a line from the levels file.
     *                  Once one level is read, if another exists in the file, the method
     *                  uses recursion to get the next level.
     * @param levelArray The list of levels that will be returned.
     * @return The method returns a list of levels.
     */
    public List<Level> getLevelFromData(List<String> levelData, List<Level> levelArray){

        try{
            String title;
            int timeToComplete;
            int height;
            int width;

            title = levelData.get(0);

            timeToComplete = Integer.parseInt(levelData.get(1));

            String[] widthHeightSplit = levelData.get(2).split(" ");

            width = Integer.parseInt(widthHeightSplit[0]);
            height = Integer.parseInt(widthHeightSplit[1]);
            String[][] tiles = new String[width][height];
            
            int tilesInitialIndex = 3;
            for(int y=0; y < height; y++){
                for(int x=0; x < width; x++){
                    tiles[x][y] = levelData.get(tilesInitialIndex+y).split(" ")[x];
                }
            }

            int numEntities = Integer.parseInt(levelData.get(3 + height));
            String[][] entities = new String[numEntities][3];
            int entitiesInitialIndex = 3 + height + 1;
            for(int i=0; i < numEntities; i++){
                entities[i] = levelData.get(entitiesInitialIndex + i).split(" ");
            }

            int numScores = Integer.parseInt(levelData.get(height + numEntities + 4));
            String[][] scores = new String[numScores][2];
            int scoresInitialIndex = entitiesInitialIndex + numEntities + 1;
            for(int i=0; i < numScores; i++){
                scores[i] = levelData.get(scoresInitialIndex + i).split(" ");
            }

            Level currentLevel = new Level(title, timeToComplete, height, width, 
                                    parseTiles(tiles, width, height), parseEntities(entities), scores);
            levelArray.add(currentLevel);

            if(levelData.size() >= scoresInitialIndex + numScores + 2){
                return getLevelFromData(levelData.subList(scoresInitialIndex + numScores + 1, levelData.size()-1), levelArray);
            } else {
                return levelArray;
            }



        } catch(Exception e){
            System.out.println("Error in file format");
            return null;

        }
               

    }


    private Tile[][] parseTiles(String[][] tiles, int width, int height){
        Tile[][] levelTiles = new Tile[width][height];
        for(int y = 0; y < height; y++) {
            for(int x = 0; x < width; x++) {
                levelTiles[x][y] = new Tile(x, y);
                levelTiles[x][y].setTileLayout(tiles[x][y]);
            }
        }
        return levelTiles;
    }

    
    /** 
     * Takes input from the LevelLoader class, and parses a 2D array of strings to an ArrayList of Entity objects.
     * 
     * @param entities
     * @return List<Entity>
     */
    private List<Entity> parseEntities(String[][] entities){
        List<Entity> parsedEntities = new ArrayList<Entity>();
        for(int i=0; i<entities.length; i++){
            switch(entities[i][0]){
                case("player"):
                    /* Awaiting Player class implementation

                    this.entities.add(new Player(
                        Integer.parseInt(entities[i][1]),
                        Integer.parseInt(entities[i][2])
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
                    parsedEntities.add(new Clock(
                        Integer.parseInt(entities[i][1]),
                        Integer.parseInt(entities[i][2]),
                        Integer.parseInt(entities[i][3])
                        ));
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
                        new Sprite("character/following_thief.png"), //shouldn't there be a constructor without the sprite attribute for NPC's?
                        entities[i][3]
                        ));
                    
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


    /**
     * This function returns a list of all the levels in the game.
     * 
     * @return The method getLevelData is being returned.
     */
    public List<Level> getAllLevels(){
        return getLevelFromData(getLevelDataFromFile(), new ArrayList<Level>());
    }

    /**
     * Reads the {@code level.txt} file
     * 
     * @return The method returns a list of type String containing each line of the file.
     */
    public List<String> getLevelDataFromFile(){
        List<String> dataArray = new ArrayList<String>();
        try {
            Scanner sc = new Scanner(new File(levelFile));

            while(sc.hasNext()){
                String line = sc.nextLine();
                dataArray.add(line);
            }

            sc.close();
        }
            catch(Exception e) {
            e.getStackTrace();
        }
        return dataArray;
    }

    /*
    TBC

    public Level getLevel(){
        return;
    }*/

}
