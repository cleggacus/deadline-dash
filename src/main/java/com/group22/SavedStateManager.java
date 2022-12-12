package com.group22;

import java.io.File;
import java.time.LocalDateTime;
import java.util.ArrayList;


/**
 * The {@code SavedStateManager} class provides utilities for handling
 * saving and loading {@link SavedState}'s
 * 
 * @author Sam Austin
 * @version 1.1
 */
public class SavedStateManager {
    private String statesFolder = "src/main/resources/com/group22/states/";
    private FileManager fileManager;

    /**
     * Create an instance of the SavedStateManager, create the
     * states folder if it doesn't exist
     */
    public SavedStateManager() {
        fileManager = new FileManager();
        File statesFolderFile = new File(statesFolder);
        statesFolderFile.mkdir();
    }

    
    /** 
     * Takes the following parameters and creates a state and
     * writes it to the states folder.
     * @param levelTitle
     * @param username
     * @param entities
     * @param score
     * @param time
     * @param levelIndex
     */
    public void createState(Level level, String username, 
        ArrayList<Entity> entities, int score, double time) {
        ArrayList<String> data = new ArrayList<String>();

        // add metadata
        data.add(level.getTitle());
        data.add(username);
        data.add(String.valueOf(score));
        data.add(String.valueOf(time));
        data.add(String.valueOf(level.getIndex()));

        // Add each entity that isn't a tile to the data
        for (Entity entity : entities) {
            if (!(entity instanceof Tile)) {
                String entityString = entity.toString();
                data.add(entityString);
            }
        }

        // create a file name
        String fileName = level.getTitle().replace(" ", "-") +
            "_" + username + "_" +
            LocalDateTime.now().toString().replace(":", ",") + ".txt";
        File file = new File(statesFolder + fileName);

        fileManager.saveFile(data, file);
    }
    
    /** 
     * Getter for {@link SavedState}'s matching a {@link Level} and a username
     * @param level The {@link Level} to be matched with
     * @param username  The username to be matched with
     * @return {@link ArrayList} {@link SavedState}
     */
    public ArrayList<SavedState> getStates(Level level, String username){
        // compute what the file name would start with
        String startsWith = level.getTitle()
            .replace(" ", "-") + "_" + username + "_";
        // find matching files
        File[] files = 
            fileManager.getMatchingFiles(statesFolder, startsWith, ".txt");
        ArrayList<SavedState> savedStates = new ArrayList<SavedState>();

        // loop all files and create {@link SavedState} objects for them
        for (File file : files) {
            ArrayList<String> data = fileManager.getDataFromFile(file);
            int score = Integer.parseInt(data.get(2));
            double time = Double.parseDouble(data.get(3));

            String fileName = file.getName().split(startsWith)[1];
            String localString = fileName.split(".txt")[0].replace(",", ":");
            LocalDateTime localDateTime = LocalDateTime.parse(localString);

            ArrayList<Entity> entities = new ArrayList<Entity>();

            // add all tiles for the level to the entities array
            entities.addAll(level.getTilesAsEntities());
            
            // parse all entities and add them to the array
            for (String entityString : data.subList(5, data.size())) {
                try {
                    entities.add(level.parseEntity(entityString.split(" ")));
                } catch (LevelFormatException e) {
                    e.printStackTrace();
                }
            }

            SavedState savedState = new SavedState(level, 
                entities, score, time, localDateTime);
            savedStates.add(savedState);
        }

        // Sort by time of save
        savedStates.sort(
            (a, b) -> b.getTimeOfSave().compareTo(a.getTimeOfSave()));

        return savedStates;
    }
}
