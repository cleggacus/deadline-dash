package com.group22;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * The {@code LevelManager} class has methods which ease the loading of
 * {@link Level}'s, and holds these levels as a static {@link ArrayList}
 * of {@link Level}'s.
 * 
 * @author Sam Austin
 * @version 1.1
 */
public class LevelManager {
    private static LevelManager instance;
    private FileManager fileManager;
    private ReplayManager replayManager;
    private int linePos;
    private static ArrayList<Level> levels;
    private static final String LEVEL_FILE_PATH =
        "src/main/resources/com/group22/levels.txt";

    private LevelManager() {}

    /**
     * If the instance is null, create a new instance and
     * call the {@link #onInitialized()} function
     * 
     * @return The instance of the {@link LevelManager} class.
     */
    public static synchronized LevelManager getInstance() {
        if (LevelManager.instance == null) {
            LevelManager.instance = new LevelManager();
            LevelManager.instance.onInitialized();
        }

        return LevelManager.instance;
    }

    /**
     * Set up the LevelManager instance.
     */
    private void onInitialized() {
        this.fileManager = new FileManager();
        this.replayManager = new ReplayManager();
        File levelFile = new File(LEVEL_FILE_PATH);
        ArrayList<String> data = fileManager.getDataFromFile(levelFile);
        this.linePos = 0;
        levels = new ArrayList<Level>();
        levels = setUpLevels(levels, data);
    }

    
    /** 
     * Create the Level class from an ArrayList of String's.
     * Steps through each line using linePos++.
     * Recursively called for each level in the data.
     * @param levels
     * @param data
     * @return {@link ArrayList}{@link Level}
     */
    private ArrayList<Level> setUpLevels(
        ArrayList<Level> levels, ArrayList<String> data) {

        try{

            // reset linePos
            this.linePos = 0;

            final String TITLE = data.get(linePos);
            this.linePos++;

            final int TIME_TO_COMPLETE = Integer.parseInt(data.get(linePos));
            this.linePos++;

            // split this line to be an array of type integer
            final int[] WIDTH_HEIGHT_SPLIT = Arrays.stream(
                data.get(linePos).split(" ")).mapToInt(
                Integer::parseInt).toArray();
            this.linePos++;

            final int WIDTH = WIDTH_HEIGHT_SPLIT[0];
            final int HEIGHT = WIDTH_HEIGHT_SPLIT[1];

            Tile[][] tiles = new Tile[WIDTH][HEIGHT];
            ArrayList<String[]> entities = new ArrayList<String[]>();

            // loop through each row
            for (int y = 0; y < HEIGHT; y++) {
                final String[] SPLIT_ROW = data.get(linePos).split(" ");
                this.linePos++;

                // loop through each column in the row
                for (int x = 0; x < WIDTH; x++) {
                    tiles[x][y] = new Tile(x, y);
                    tiles[x][y].setTileLayout(SPLIT_ROW[x]);
                }
            }

            final int NUM_ENTITIES = Integer.parseInt(data.get(linePos));
            this.linePos++;

            for (int i = 0; i < NUM_ENTITIES; i++) {
                final String[] SPLIT_ENTITIES = data.get(linePos).split(" ");
                this.linePos++;
                entities.add(SPLIT_ENTITIES);
            }
            this.linePos++;

            ArrayList<Replay> replays = 
                this.replayManager.getReplaysFromLevelTitle(TITLE);
        
            // create the level
            final Level CURRENT_LEVEL = new Level(
                TITLE, TIME_TO_COMPLETE,
                tiles, entities, levels.size());

            CURRENT_LEVEL.setReplays(replays);

            levels.add(CURRENT_LEVEL);
            
            // if not end of file
            if (data.size() > this.linePos) {
                // recursively call this function
                return setUpLevels(levels, 
                new ArrayList<String>(data.subList(this.linePos, data.size())));
            } else {
                return levels;
            }

        } catch(Exception e) {
            e.printStackTrace();
        }

        return null;
        
    }

    
    /** 
     * Returns an ArrayList of all the levels
     * @return {@link ArrayList} {@link Level}
     */
    public ArrayList<Level> getAllLevels() {
        return levels;
    }
}