package com.group22;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class ReplayManager {
    private ArrayList<ReplayFrame> frames;
    private Level level;
    private static final String replayFolder = "src/main/resources/com/group22/replays/";
    public ReplayManager(Level level){
        this.level = level;
        this.frames = new ArrayList<ReplayFrame>();
    }

    public void storeFrame(ReplayFrame frame){
        this.frames.add(frame);
    }

    public void saveReplay(){
        try {
            String levelNameForFile = level.getTitle().replaceAll(" ", "-");
            String timeForFile = LocalDateTime.now().toString().replace(":", ",");
            String fileName = levelNameForFile + "_" + Game.getInstance().getUsername() + "_" + timeForFile + ".txt" ;
            File replayFile = new File(replayFolder + fileName);
            FileWriter replayWriter = new FileWriter(replayFile, true);
            replayWriter.append(level.getTitle() + "\n");
            replayWriter.append(Game.getInstance().getUsername() + "\n");
            replayWriter.append(LocalDateTime.now().toString() + "\n");
            for(ReplayFrame frame : this.frames){
                replayWriter.append(frame.getKey() + " " + frame.getKeyTime() + " " + frame.getKeyDown() + "\n");
            }
            replayWriter.close();
        } catch (IOException e) {
            System.out.println("An error occurred writing to the replays file");
            e.printStackTrace();
        }
    }
}
