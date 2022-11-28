package com.group22;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class LevelLoader {

    public LevelLoader(){

    }
    private String levelFile = "/resources/com/group22/levels.txt";
    
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
            for(int i=0; i < height; i++){
                tiles[i] = levelData.get(tilesInitialIndex + i).split(" ");
            }

            int numEntities = Integer.parseInt(levelData.get(3 + height));
            String[][] entities = new String[numEntities][3];
            int entitiesInitialIndex = 3 + height + 1;
            for(int i=0; i < height; i++){
                entities[i] = levelData.get(entitiesInitialIndex + i).split(" ");
            }

            int numScores = Integer.parseInt(levelData.get(3 + height + numEntities + 1));
            String[][] scores = new String[numScores][2];
            int scoresInitialIndex = entitiesInitialIndex + numEntities;
            for(int i=0; i < height; i++){
                scores[i] = levelData.get(scoresInitialIndex + i).split(" ");
            }

            Level currentLevel = new Level(title, timeToComplete, height, width, tiles, entities, scores);
            levelArray.add(currentLevel);

            if(levelData.size() >= scoresInitialIndex + numScores + 2){
                return getLevelFromData(levelData.subList(scoresInitialIndex + numScores + 2, levelData.size()-1), levelArray);
            } else {
                return levelArray;
            }



        } catch(Exception e){
            System.out.println("Error in file format");

        }
               
        return null;

    }

    /**
     * This function returns a list of all the levels in the game.
     * 
     * @return The method getLevelData is being returned.
     */
    public List<Level> getAllLevels(){
        List<Level> levelArray = new ArrayList<Level>();
        List<String> levelFileArray = getLevelDataFromFile();

        return getLevelFromData(levelFileArray, levelArray);
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
