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
    private static final String levelFile =
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

            String title;
            int timeToComplete;
            int height;
            int width;


            title = getStringFromData(levelData.get(linePos));
            timeToComplete = getIntFromData(levelData.get(linePos));
            int[] widthHeightSplit = getIntArrayFromData(
                levelData.get(linePos), " ");
            width = widthHeightSplit[0];
            height = widthHeightSplit[1];

            Tile[][] tiles = new Tile[width][height];
            ArrayList<String[]> entities = new ArrayList<String[]>();
            tiles = getTilesFromData(levelData, width, height, tiles);

            int numEntities = getIntFromData(levelData.get(linePos));
            entities.addAll(getEntitiesFromData(
                levelData, numEntities, width, height));

            int numScores = getIntFromData(levelData.get(linePos));
            String[][] scores = new String[numScores][2];
            scores = getScoresFromData(levelData, scores, numScores);
        
            Level currentLevel = new Level(
                title, timeToComplete, height, width, 
                tiles, entities, scores, levelArray.size());
            levelArray.add(currentLevel);

            this.linePos = this.linePos + 1;
            if(levelData.size() > this.linePos){
                return getLevelFromData(levelData.subList(this.linePos,
                                        levelData.size()), levelArray);
            } else {
                return levelArray;
            }

        } catch(Exception e){
            System.out.println(e);
            return null;
        }
    }

    private String[][] getScoresFromData(List<String> levelData,
        String[][] scores, int numScores){

        for(int i=0; i < numScores; i++){
            scores[i] = getStringArrayFromData(levelData.get(linePos), " ");
        }
        return scores;
    }

    private List<String[]> getEntitiesFromData(List<String> levelData,
        int numEntities, int width, int height) throws Exception{

        List<String[]> entities = new ArrayList<String[]>();
        for(int i=0; i < numEntities; i++){
            String[] splitEntities = getStringArrayFromData(
                levelData.get(linePos), " ");
            entities.add(splitEntities);
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
            int yData = linePos+y;
            if(yData >= levelData.size() || yData < 0)
                throw new LevelFormatException(
                    "Tile missing in the y plane");
            String[] splitRow = getStringArrayFromData(
                levelData.get(linePos), " ");
            for(int x=0; x < width; x++){
                if(x >= splitRow.length || x < 0)
                    throw new LevelFormatException(
                        "Tile missing in the y plane");
                tiles[x][y] = parseTile(splitRow[x], x, y);
            }
        }
        return tiles;
    }

    private String getStringFromData(String data){
        linePos = linePos + 1;
        return data;
    }

    private String[] getStringArrayFromData(String data, String splitOn){
        linePos = linePos + 1;
        return data.split(splitOn);
    }

    private int[] getIntArrayFromData(String data, String splitOn){
        linePos = linePos + 1;
        int[] iArray = Arrays.stream(data.split(splitOn)).mapToInt(
            Integer::parseInt).toArray();
        return iArray;
    }

    private int getIntFromData(String data){
        linePos = linePos + 1;
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

    public File getLevelFile(){
        return new File(levelFile);
    }

    /**
     * Reads the levels.txt file, calls getLevelFromData on
     * the data to set this.levels to an ArrayList of levels.
     */
    public void setUp(){
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
        this.levels = getLevelFromData(dataArray, new ArrayList<Level>());

    }

}
