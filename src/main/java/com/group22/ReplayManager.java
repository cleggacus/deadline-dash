package com.group22;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class ReplayManager {
    private ArrayList<ReplayFrame> frames;
    private Level level;
    private static final String replayFile = "src/main/resources/com/group22/replays.txt";
    public ReplayManager(Level level){
        this.level = level;
        this.frames = new ArrayList<ReplayFrame>();
    }

    public void storeFrame(ReplayFrame frame){
        this.frames.add(frame);
    }

    public void saveReplay(){
        try {
            FileWriter myWriter = new FileWriter(replayFile, true);
            myWriter.append(level.getTitle() + " " + Game.getInstance().getUsername() + "\n");
            for(ReplayFrame frame : this.frames){
                myWriter.append(frame.getPlayerInput().toString() + " " + frame.getTimeOfFrame() + "\n");
            }
            myWriter.close();
        } catch (IOException e) {
            System.out.println("An error occurred writing to the replays.txt file");
            e.printStackTrace();
        }
    }
}
