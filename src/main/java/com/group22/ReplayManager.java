package com.group22;

import java.io.File;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * The {@code ReplayManager} class provides methods which allow the saving
 * and loading of replays/scores to and from the file.
 * 
 * @author Sam Austin
 * @version 1.1
 */
public class ReplayManager {
    //private Level level;
    private static final String REPLAY_FOLDER = 
        "src/main/resources/com/group22/replays/";
    private FileManager fileManager;

    /**
     * Create an instance of ReplayManager, create the replays
     * folder if it doesn't exist
     */
    public ReplayManager() {
        this.fileManager = new FileManager();
        File replayFolder = new File(REPLAY_FOLDER);
        replayFolder.mkdir();
    }

    
    /** 
     * Gets the {@link Replay} using the index of the {@link Level} in the
     * level's file.
     * @param levelIndex
     * @return {@link ArrayList}{@link Replay}
     */
    public ArrayList<Replay> getReplaysFromLevelIndex(int levelIndex){
        Level level = LevelManager.getInstance().getAllLevels().get(levelIndex);
        return getReplaysFromLevelTitle(level.getTitle());
    }


    
    /** 
     * Gets all {@link Replay}'s returned in a {@link ArrayList} based on the
     * title of a level.
     * @param levelTitle    The title of the level as a String
     * @return {@link ArrayList} {@link Replay}
     */
    public ArrayList<Replay> getReplaysFromLevelTitle(String levelTitle){
        // Create a file name friendly string by replacing spaces with hyphens
        String levelTitleForFilePath = levelTitle.replace(" ", "-");
        File[] matchingFiles = fileManager.getMatchingFiles(
            REPLAY_FOLDER, levelTitleForFilePath, ".txt");
        
        ArrayList<Replay> replays = new ArrayList<Replay>();

        if (matchingFiles != null) {
            for (File file : matchingFiles) {
                ArrayList<String> dataArray = 
                    this.fileManager.getDataFromFile(file);
    
                // get the metadata for the replay
                String username = dataArray.get(1);
                LocalDateTime timeOfSave = 
                    LocalDateTime.parse(dataArray.get(2));
                int score = Integer.parseInt(dataArray.get(3));
                
                // get the frames for the replay
                ArrayList<ReplayFrame> replayFrames = 
                    parseFramesFromData(dataArray.subList(4, dataArray.size()));
                Replay replay = new Replay(
                        levelTitle, username, timeOfSave, replayFrames, score);
                replays.add(replay);
            }
            // sort the replays by descending score
            replays.sort((Replay r1, Replay r2) -> {
                if (r1.getScore() < r2.getScore()) {
                  return 1;
                }

                if (r1.getScore() > r2.getScore()) {
                  return -1;
                }

                return 0;
            });
        }

        return new ArrayList<>(replays);
    }

    
    /** 
     * Remove the lowest scoring replay from the folder for a specific level.
     * @param levelTitle    The level title as a String
     */
    private void deleteWorstReplay(String levelTitle) {
        // Create a file name friendly string by replacing spaces with hyphens
        String levelTitleForFilePath = levelTitle.replace(" ", "-");
        File[] matchingFiles = fileManager.getMatchingFiles(
            REPLAY_FOLDER, levelTitleForFilePath, ".txt");
        int worstScoreIndex = 0;

        // loop all matched files
        for (int i = 0; i < matchingFiles.length; i++) {
            // get the score of the replay
            int currentScore = Integer.parseInt(
                matchingFiles[i].getName().split("_")[1]);
            
            // get the current worst score
            int worstScore = Integer.parseInt(
                matchingFiles[worstScoreIndex].getName().split("_")[1]);

            // update current score
            if (currentScore < worstScore) {
                worstScoreIndex = i;
            }
        }
        // delete the replay with the worst score
        matchingFiles[worstScoreIndex].delete();
    }

    
    /** 
     * Get the number of replay files for a specific level
     * @param levelTitle    The level title as a String
     * @return int
     */
    private int getNumReplays(String levelTitle) {
        // Create a file name friendly string by replacing spaces with hyphens
        String levelNameForFilePath = levelTitle.replace(" ", "-");
        File[] matchingFiles = fileManager.getMatchingFiles(
            REPLAY_FOLDER, levelNameForFilePath, ".txt");
        return matchingFiles.length;

    }
    
    
    /** 
     * Create {@link ReplayFrame}'s from file data
     * @param framesData
     * @return {@link ArrayList}{@link ReplayFrame}
     */
    private ArrayList<ReplayFrame> parseFramesFromData(
        List<String> framesData) {
        ArrayList<ReplayFrame> replayFrames = 
            new ArrayList<ReplayFrame>();
        
        // Loop all lines in the file with frame data
        for (String frameString : framesData) {
            String[] splitFrameString = frameString.split(" ");
            ReplayFrame currentFrame = 
                new ReplayFrame(Integer.parseInt(splitFrameString[0]),
                Integer.parseInt(splitFrameString[1]), 
                Double.parseDouble(splitFrameString[2]));
            replayFrames.add(currentFrame);
        }

        return replayFrames;
    }

    
    /** 
     * Saves replay data to the file
     * @param level
     * @param replay
     * @param score
     */
    public void saveReplay(Level level, Replay replay, int score){
        // if 10 replays exist then delete the worst one
        if (this.getNumReplays(level.getTitle()) == 10) {
            this.deleteWorstReplay(level.getTitle());
        }

        // Create a file name friendly string by replacing spaces with hyphens
        String levelNameForFile = 
            replay.getLevelName().replaceAll(" ", "-");
        String timeForFile = 
            LocalDateTime.now().toString().replace(":", ",");
        // Append the level title, score, username and time for the file name
        String fileName = 
            levelNameForFile + "_" + String.valueOf(score) + "_" + 
            replay.getUsername() + "_" + timeForFile + ".txt" ;

        File replayFile = new File(REPLAY_FOLDER + fileName);
        ArrayList<String> replayData = new ArrayList<String>();

        replayData.add(replay.getLevelName());
        replayData.add(replay.getUsername());
        replayData.add(LocalDateTime.now().toString());
        replayData.add(String.valueOf(score));

        for (ReplayFrame frame : replay.getFrames()) {
            replayData.add(frame.getX() + " " + 
                frame.getY() + " " + frame.getKeyTime());
        }

        fileManager.saveFile(replayData, replayFile);
    }
}
