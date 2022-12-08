package com.group22;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class LevelLoader {

    private int linePos = 0;
    private List<Level> levels;
    public LevelLoader(){
    }
    private static final String LEVEL_FILE =
    "src/main/resources/com/group22/levels.txt";
    /**
     * A recursive method that takes an ArrayList of type strings 
     * and an empty ArrayList of type Level, and returns a list of levels.
     * 
     * @param levelData A list of strings, each string is a line from the
     * levels file. Once one level is read, if another exists in the file,
     * the method uses recursion to get the next level.
     * @param levelArray The list of levels that will be returned.
     * @return The method returns a list of levels.
     */
    public List<Level> getLevelFromData(List<String> levelData,
    List<Level> levelArray){

        try{
            this.linePos = 0;

            final String TITLE = getStringFromData(levelData.get(linePos));
            final int TIME_TO_COMPLETE = getIntFromData(levelData.get(linePos));
            final int[] WIDTH_HEIGHT_SPLIT = getIntArrayFromData(
                levelData.get(linePos), " ");
            final int WIDTH = WIDTH_HEIGHT_SPLIT[0];
            final int HEIGHT = WIDTH_HEIGHT_SPLIT[1];

            Tile[][] tiles = new Tile[WIDTH][HEIGHT];
            ArrayList<String[]> entities = new ArrayList<String[]>();
            tiles = getTilesFromData(levelData, WIDTH, HEIGHT, tiles);

            final int NUM_ENTITIES = getIntFromData(levelData.get(linePos));
            entities.addAll(getEntitiesFromData(
                levelData, NUM_ENTITIES, WIDTH, HEIGHT));

            ReplayManager replayManager = new ReplayManager();
            ArrayList<Replay> replays = replayManager.getReplaysFromLevelTitle(TITLE);
        
            final Level CURRENT_LEVEL = new Level(
                TITLE, TIME_TO_COMPLETE, HEIGHT, WIDTH, 
                tiles, entities, replays, levelArray.size());
            levelArray.add(CURRENT_LEVEL);

            this.linePos = this.linePos + 1;
            if(levelData.size() > this.linePos){
                return getLevelFromData(levelData.subList(this.linePos,
                                        levelData.size()), levelArray);
            } else {
                return levelArray;
            }

        } catch(Exception e){
            e.printStackTrace();
        }

        return null;
    }
    
    private List<String[]> getEntitiesFromData(List<String> levelData,
    int numEntities, int width, int height) throws Exception{

        List<String[]> entities = new ArrayList<String[]>();
        for(int i=0; i < numEntities; i++){
            final String[] SPLIT_ENTITIES = getStringArrayFromData(
                levelData.get(linePos), " ");
            entities.add(SPLIT_ENTITIES);
        }
        return entities;
    }

    /**
     * It takes a list of strings, and converts it into a 2D array of tiles
     * 
     * @param levelData The data from the file
     * @param width The width of the level
     * @param height The height of the level in tiles
     * @param tiles The 2D array of tiles that will be returned
     * @return The method is returning a 2D array of Tile objects.
     */
    private Tile[][] getTilesFromData(List<String> levelData,
    int width, int height, Tile[][] tiles) throws LevelFormatException{
        for(int y=0; y < height; y++){
            final String[] SPLIT_ROW = getStringArrayFromData(
                levelData.get(linePos), " ");
            for(int x=0; x < width; x++){
                tiles[x][y] = parseTile(SPLIT_ROW[x], x, y);
            }
        }
        return tiles;
    }

    private String getStringFromData(String data){
        this.linePos += 1;
        return data;
    }

    private String[] getStringArrayFromData(String data, String splitOn){
        this.linePos += 1;
        return data.split(splitOn);
    }

    private int[] getIntArrayFromData(String data, String splitOn){
        this.linePos += 1;
        int[] iArray = Arrays.stream(data.split(splitOn)).mapToInt(
            Integer::parseInt).toArray();
        return iArray;
    }

    private int getIntFromData(String data){
        this.linePos += 1;
        return Integer.parseInt(data);
    }

    private Tile parseTile(String tile, int x, int y){
        Tile newTile = new Tile(x, y);
        newTile.setTileLayout(tile);
        return newTile;
    }

    /**
     * This function returns a list of all the levels in the game.
     * Reads the {@code level.txt} file and get results
     * of {@code getLevelFromData()} using the data retrieved from the file.
     * 
     * @return The method getLevelData is being returned.
     */
    public List<Level> getAllLevels(){
        return this.levels;
    }

    /**
     * Reads a text file and returns an ArrayList of Strings
     * 
     * @return An ArrayList of Strings.
     */
    public ArrayList<String> getLevelFileData(){
        
        ArrayList<String> dataArray = new ArrayList<String>();
        
        try {
            Scanner sc = new Scanner(new File(LEVEL_FILE));

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

    public File getLevelFile(){
        return new File(LEVEL_FILE);
    }

    /**
     * Reads the levels.txt file, calls getLevelFromData on
     * the data to set this.levels to an ArrayList of levels.
     */
    public void setUp(){
        ArrayList<String> dataArray = new ArrayList<>();
        dataArray.addAll(this.getLevelFileData());
        this.levels = this.getLevelFromData(dataArray, new ArrayList<Level>());

    }

}
