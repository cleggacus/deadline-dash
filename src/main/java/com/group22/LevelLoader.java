package com.group22;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class LevelLoader {

    private boolean playerPresent;
    private boolean doorPresent;
    private int linePos = 0;
    private List<Level> levels;
    public LevelLoader(){
    }
    private static final String levelFile = "src/main/resources/com/group22/levels.txt";
    /**
     * A recursive method that takes an ArrayList of type strings and an empty ArrayList 
     * of type Level, and returns a list of levels.
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

            this.playerPresent = false;
            this.doorPresent = false;
            this.linePos = 0;

            title = getStringFromData(levelData.get(linePos));
            timeToComplete = getIntFromData(levelData.get(linePos));
            int[] widthHeightSplit = getIntArrayFromData(levelData.get(linePos), " ");
            width = widthHeightSplit[0];
            height = widthHeightSplit[1];

            Tile[][] tiles = new Tile[width][height];
            ArrayList<Entity> entities = new ArrayList<Entity>();
            tiles = getTilesFromData(levelData, width, height, tiles);
            entities.addAll(tilesToEntities(tiles));


            int numEntities = getIntFromData(levelData.get(linePos));
            entities.addAll(getEntitiesFromData(levelData, numEntities, width, height));

            int numScores = getIntFromData(levelData.get(linePos));
            String[][] scores = new String[numScores][2];
            for(int i=0; i < numScores; i++){
                scores[i] = getStringArrayFromData(levelData.get(linePos), " ");
            }
        
            isValidLevel(title);
            Level currentLevel = new Level(title, timeToComplete, height, width, 
            tiles, entities, scores);
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

    private List<Entity> getEntitiesFromData(List<String> levelData, int numEntities, int width, int height) throws Exception{
        List<Entity> entities = new ArrayList<Entity>();
        for(int i=0; i < numEntities; i++){
            String[] splitEntities = getStringArrayFromData(levelData.get(linePos), " ");
            Entity entity = parseEntity(splitEntities);
            isValidEntity(entity, width, height);
            entities.add(entity);
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
    private Tile[][] getTilesFromData(List<String> levelData, int width, int height, Tile[][] tiles) throws LevelFormatException{
        for(int y=0; y < height; y++){
            int yData = linePos+y;
            if(yData >= levelData.size() || yData < 0)
                throw new LevelFormatException("Tile missing in the y plane");
            String[] splitRow = getStringArrayFromData(levelData.get(linePos), " ");
            for(int x=0; x < width; x++){
                if(x >= splitRow.length || x < 0)
                    throw new LevelFormatException("Tile missing in the y plane");
                tiles[x][y] = parseTile(splitRow[x], x, y);
            }
        }
        return tiles;
    }
    
    private List<Entity> tilesToEntities(Tile[][] tiles){
        List<Entity> entities = new ArrayList<Entity>();
        for(Tile[] yTiles : tiles){
            for(Tile xTiles : yTiles){
                entities.add(xTiles);
            }
        }
        return entities;
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
        int[] iArray = Arrays.stream(data.split(splitOn)).mapToInt(Integer::parseInt).toArray();
        return iArray;
    }

    private int getIntFromData(String data){
        linePos = linePos + 1;
        return Integer.parseInt(data);
    }

    private void isValidLevel(String level) throws LevelFormatException{
        if(!this.playerPresent)
            throw new LevelFormatException("Player not present for level " + level);
        if(!this.doorPresent)
            throw new LevelFormatException("Door not present for level " + level);
    }

    private void isValidEntity(Entity entity, int width, int height) throws Exception{
        if(entity instanceof Player){
            this.playerPresent = true;
        }
        if(entity instanceof Door){
            this.doorPresent = true;
        }
        if(entity.getX() > width)
            throw new LevelFormatException("Entity out of bounds in x");
        if(entity.getY() > height)
            throw new LevelFormatException("Entity out of bounds in y");
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
     * @throws LevelFormatException
     */
    private Entity parseEntity(String[] entity) throws LevelFormatException{
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

        }

        throw new LevelFormatException("Unrecognised entity in level file");
    }


    /**
     * This function returns a list of all the levels in the game.
     * Reads the {@code level.txt} file and get results of {@code getLevelFromData()}
     * using the data retrieved from the file.
     * 
     * @return The method getLevelData is being returned.
     */
    public List<Level> getAllLevels(){
        return this.levels;
    }

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
