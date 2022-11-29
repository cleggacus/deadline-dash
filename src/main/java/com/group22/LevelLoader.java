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
            Tile[][] tiles = new Tile[width][height];
            
            int tilesInitialIndex = 3;
            for(int y=0; y < height; y++){
                String[] splitRow = levelData.get(tilesInitialIndex+y).split(" ");
                for(int x=0; x < width; x++){
                    tiles[x][y] = parseTile(splitRow[x], x, y);
                }
            }

            int numEntities = Integer.parseInt(levelData.get(3 + height));
            List<Entity> entities = new ArrayList<Entity>();
            int entitiesInitialIndex = height + tilesInitialIndex + 1;
            for(int i=0; i < numEntities; i++){
                entities.add(parseEntities(levelData.get(entitiesInitialIndex + i).split(" ")));
            }

            int numScores = Integer.parseInt(levelData.get(entitiesInitialIndex + numEntities));
            String[][] scores = new String[numScores][2];
            int scoresInitialIndex = entitiesInitialIndex + numEntities + 1;
            for(int i=0; i < numScores; i++){
                scores[i] = levelData.get(scoresInitialIndex + i).split(" ");
            }

            Level currentLevel = new Level(title, timeToComplete, height, width, 
                                    tiles, entities, scores);
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


    private Tile parseTile(String tile, int x, int y){
        Tile newTile = new Tile(x, y);
        newTile.setTileLayout(tile);
        return newTile;
    }

    
    /** 
     * Takes input from the LevelLoader class, and parses a 2D array of strings to an ArrayList of Entity objects.
     * 
     * @param entities
     * @return List<Entity>
     */
    private Entity parseEntities(String[] entity){
        switch(entity[0]){
            case("player"):
                return new Player(
                    Integer.parseInt(entity[1]),
                    Integer.parseInt(entity[2]));
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
                    Integer.parseInt(entity[2]));
            case("loot"):
                    return new Loot(
                    Integer.parseInt(entity[1]),
                    Integer.parseInt(entity[2]));

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
