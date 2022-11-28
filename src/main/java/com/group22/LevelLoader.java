package com.group22;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class LevelLoader {

    public LevelLoader(){

    }
    private String levelFile = "/resources/com/group22/levels.txt";
    
    public List<Level> getFromLevelData(List<String> levelData, List<Level> levelArray){

        try{
            String title;
            Boolean locked;
            int height;
            int width;

            title = levelData.get(0);

            locked = Boolean.parseBoolean(levelData.get(1));

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

            Level currentLevel = new Level(title, locked, height, width, tiles, entities, scores);
            levelArray.add(currentLevel);

            if(levelData.size() >= scoresInitialIndex + numScores + 2){
                return getFromLevelData(levelData.subList(scoresInitialIndex + numScores + 2, levelData.size()-1), levelArray);
            } else {
                return levelArray;
            }



        } catch(Exception e){
            System.out.println("Error in file format");

        }
               
        return null;

    }

    public List<Level> getAllLevels(){
        List<Level> levelArray = new ArrayList<Level>();

        return getLevelData(levelArray);
    }

    public List<Level> getLevelData(List<Level> levelArray){
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
        return getFromLevelData(dataArray, levelArray);
    }

    /*
    TBC

    public Level getLevel(){
        return;
    }*/

}
