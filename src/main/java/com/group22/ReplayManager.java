package com.group22;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class ReplayManager {
    //private Level level;
    private static final String replayFolder = "src/main/resources/com/group22/replays/";
    private FileManager fileManager;

    public ReplayManager(){
        this.fileManager = new FileManager();
    }

    public ArrayList<Replay> getReplaysFromLevelIndex(int levelIndex){
        Level level = LevelManager.getInstance().getAllLevels().get(levelIndex);
        return getReplaysFromLevelTitle(level.getTitle());
    }


    public ArrayList<Replay> getReplaysFromLevelTitle(String levelTitle){
        String levelTitleForFilePath = levelTitle.replace(" ", "-");
        File[] matchingFiles = fileManager.getMatchingFiles(replayFolder, levelTitleForFilePath, ".txt");
        
        ArrayList<Replay> replays = new ArrayList<Replay>();
        if (matchingFiles != null){
            for (File file : matchingFiles){
                ArrayList<String> dataArray = this.fileManager.getDataFromFile(file);
    
                String username = dataArray.get(1);
                LocalDateTime timeOfSave = LocalDateTime.parse(dataArray.get(2));
                int score = Integer.parseInt(dataArray.get(3));
    
                ArrayList<ReplayFrame> replayFrames = parseFramesFromData(
                    dataArray.subList(4, dataArray.size()));
                Replay replay = new Replay(levelTitle, username,
                    timeOfSave, replayFrames, score);
                replays.add(replay);
            }
            replays.sort((Replay r1, Replay r2) -> {
                if (r1.getScore() < r2.getScore())
                  return 1;
                if (r1.getScore() > r2.getScore())
                  return -1;
                return 0;
             });
            return replays;
        }
        return null;
    }

    private void deleteWorstReplay(String levelTitle){
        String levelTitleForFilePath = levelTitle.replace(" ", "-");
        File[] matchingFiles = fileManager.getMatchingFiles(replayFolder, levelTitleForFilePath, ".txt");
        int worstScoreIndex = 0;
        for(int i = 0; i < matchingFiles.length; i++){
            int currentScore = Integer.parseInt(matchingFiles[i].getName().split("_")[1]);
            int worstScore = Integer.parseInt(matchingFiles[worstScoreIndex].getName().split("_")[1]);
            if(currentScore < worstScore)
                worstScoreIndex = i;
        }
        matchingFiles[worstScoreIndex].delete();
    }

    private int getNumReplays(String levelTitle){
        String levelNameForFilePath = levelTitle.replace(" ", "-");
        File[] matchingFiles = fileManager.getMatchingFiles(replayFolder, levelNameForFilePath, ".txt");
        return matchingFiles.length;

    }
    
    private ArrayList<ReplayFrame> parseFramesFromData(List<String> framesData){
        ArrayList<ReplayFrame> replayFrames = new ArrayList<ReplayFrame>();
        for(String frameString : framesData){
            String[] splitFrameString = frameString.split(" ");
            ReplayFrame currentFrame = new ReplayFrame(Integer.parseInt(splitFrameString[0]),
            Integer.parseInt(splitFrameString[1]), Double.parseDouble(splitFrameString[2]));
            replayFrames.add(currentFrame);
        }
        return replayFrames;
    }

    public void saveReplay(Level level, Replay replay, int score){
        if(this.getNumReplays(level.getTitle()) == 10){
            this.deleteWorstReplay(level.getTitle());
        }
        try {
            String levelNameForFile = replay.getLevelName().replaceAll(" ", "-");
            String timeForFile = LocalDateTime.now().toString().replace(":", ",");
            String fileName = levelNameForFile + "_" + String.valueOf(score) + "_" 
            + replay.getUsername() + "_" + timeForFile + ".txt" ;

            File replayFile = new File(replayFolder + fileName);
            FileWriter replayWriter = new FileWriter(replayFile, true);

            replayWriter.append(replay.getLevelName() + "\n");
            replayWriter.append(replay.getUsername() + "\n");
            replayWriter.append(LocalDateTime.now().toString() + "\n");
            replayWriter.append(score + "\n");

            for(ReplayFrame frame : replay.getFrames()){
                replayWriter.append(frame.getX() + " " + frame.getY() + " " + frame.getKeyTime() + "\n");
            }

            replayWriter.close();

        } catch (IOException e) {
            System.out.println("An error occurred writing to the replays file");
            e.printStackTrace();
        }
    }
}
