package com.group22;

import java.util.ArrayList;

/**
 * 
 */
public class ReplayPlayer extends Player {
    private ArrayList<ReplayFrame> frames;

    /**
     * 
     * @param x
     * @param y
     * @param frames
     */
    public ReplayPlayer(int x, int y, ArrayList<ReplayFrame> frames) {
        super(x, y, true);
        this.frames = frames;
    }

    @Override
    protected void updateMovement() {
        while (frames.size() > 0 && frames.get(0).getKeyTime() < this.time) {
            this.move(
                frames.get(0).getX(),
                frames.get(0).getY()
            );

            frames.remove(0);
        }
    }

    @Override
    protected void update() {
        super.update();
    }
}
