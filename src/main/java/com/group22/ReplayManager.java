package com.group22;

import java.io.File;
import java.io.FileWriter;
import java.io.FilenameFilter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Scanner;

import javafx.scene.input.KeyCode;

public class ReplayManager {
    //private Level level;
    private static final String replayFolder = "src/main/resources/com/group22/replays/";

    public ReplayManager(){
    }

    public ArrayList<String> getDataFromFile(File file){
        ArrayList<String> dataArray = new ArrayList<String>();
        
        try {
            Scanner sc = new Scanner(file);

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

    public ArrayList<Replay> getReplaysFromLevelIndex(int levelIndex){
        LevelLoader levelLoader = new LevelLoader();
        Level level = levelLoader.getAllLevels().get(levelIndex);
        return getReplaysFromLevel(level);
    }

    public ArrayList<Replay> getReplaysFromLevel(Level level){
        File f = new File(replayFolder);
        String levelNameForFilePath = level.getTitle().replace(" ", "-");
        File[] matchingFiles = f.listFiles(new FilenameFilter() {
            public boolean accept(File dir, String name) {
                return name.startsWith(levelNameForFilePath) && name.endsWith("txt");
            }
        });

        ArrayList<Replay> replays = new ArrayList<Replay>();
        for(File file : matchingFiles){
            ArrayList<String> dataArray = getDataFromFile(file);

            String username = dataArray.get(1);
            LocalDateTime timeOfSave = LocalDateTime.parse(dataArray.get(2));

            ArrayList<ReplayFrame> replayFrames = parseFramesFromData(dataArray);
            Replay replay = new Replay(level.getTitle(), username, timeOfSave, replayFrames);
            replays.add(replay);
        }
        return replays;
    }
    
    private ArrayList<ReplayFrame> parseFramesFromData(ArrayList<String> framesData){
        ArrayList<ReplayFrame> replayFrames = new ArrayList<ReplayFrame>();
        for(String frameString : framesData){
            String[] splitFrameString = frameString.split(" ");
            System.out.println(splitFrameString[0]);
            if(splitFrameString[0].equals("W") ||
                splitFrameString[0].equals("A") ||
                splitFrameString[0].equals("S") ||
                splitFrameString[0].equals("D")){
                    System.out.println(splitFrameString[0]);
                    ReplayFrame currentFrame = new ReplayFrame(KeyCode.getKeyCode(splitFrameString[0]),
                    Double.parseDouble(splitFrameString[1]), Boolean.parseBoolean(splitFrameString[2]));
                    replayFrames.add(currentFrame);
            }
        }
        //System.out.println(replayFrames.get(0));
        return replayFrames;

    }

    public void saveReplay(Replay replay){
        try {
            String levelNameForFile = replay.getLevelName().replaceAll(" ", "-");
            String timeForFile = LocalDateTime.now().toString().replace(":", ",");
            String fileName = levelNameForFile + "_" + replay.getUsername() + "_" + timeForFile + ".txt" ;

            File replayFile = new File(replayFolder + fileName);
            FileWriter replayWriter = new FileWriter(replayFile, true);

            replayWriter.append(replay.getLevelName() + "\n");
            replayWriter.append(replay.getUsername() + "\n");
            replayWriter.append(LocalDateTime.now().toString() + "\n");

            for(ReplayFrame frame : replay.getFrames()){
                replayWriter.append(frame.getKey() + " " + frame.getKeyTime() + " " + frame.getKeyDown() + "\n");
            }

            replayWriter.close();

        } catch (IOException e) {
            System.out.println("An error occurred writing to the replays file");
            e.printStackTrace();
        }
    }
}
