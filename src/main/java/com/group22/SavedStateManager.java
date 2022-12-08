package com.group22;

import java.io.File;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class SavedStateManager {
    private String statesFolder = "src/main/resources/com/group22/states/";
    private FileManager fileManager;
    public SavedStateManager(){
        fileManager = new FileManager();
    }

    public void createState(String levelTitle, String username, ArrayList<Entity> entities, int score, double time){
        ArrayList<String> data = new ArrayList<String>();

        data.add(levelTitle);
        data.add(username);
        data.add(String.valueOf(score));
        data.add(String.valueOf(time));

        for(Entity entity : entities){
            if(!(entity instanceof Tile)){
                String entityString = entity.toString();
                data.add(entityString);
            }
        }

        String fileName = levelTitle.replace(" ", "-") + "_" + username + "_" +
        LocalDateTime.now().toString().replace(":", ",");
        File file = new File(statesFolder + fileName);

        fileManager.saveFile(data, file);
    }
    public ArrayList<SavedState> getStates(Level level, String username){
        String startsWith = level.getTitle().replace(" ", "-") + "_" + username;
        File[] files = fileManager.getMatchingFiles(statesFolder, startsWith, ".txt");
        ArrayList<SavedState> savedStates = new ArrayList<SavedState>();
        for(File file : files){
            ArrayList<String> data = fileManager.getDataFromFile(file);
            int score = Integer.parseInt(data.get(2));
            double time = Double.parseDouble(data.get(3));

            ArrayList<Entity> entities = new ArrayList<Entity>();
            for(String entityString : data.subList(4, data.size())){
                entities.add(level.parseEntity(entityString.split("")));
            }

            SavedState savedState = new SavedState(level.getTitle(), entities, score, time, LocalDateTime.now());
            savedStates.add(savedState);
        }
        return savedStates;
    }

    //TODO
    /*
    private SavedState getStateFromData(ArrayList<String> data, String levelTitle){
        SavedState savedState = new SavedState(levelTitle, null, 0, 0);
    }*/
}
