package com.group22;

import java.util.ArrayList;

/**
 * The {@code ReplayPlayer} represents a player object for replays.
 * 
 * @author Sam Austin
 * @version 1.1
 */
public class ReplayPlayer extends Player {
    private ArrayList<ReplayFrame> frames;

    /**
     * Create an instance of a ReplayPlayer
     * @param x
     * @param y
     * @param frames
     */
    public ReplayPlayer(int x, int y, Boolean hasTorch, ArrayList<ReplayFrame> frames) {
        super(x, y, hasTorch);
        this.frames = frames;
    }

    @Override
    protected void updateMovement() {
        this.getSprite().setImageSet("idle");
    }

    @Override
    protected void update() {
        super.update();

        /**
         * if frames exists, and the current frame's time is less than
         * elapsed level time, call move on the frame's translation
         */
        if (frames.size() > 0 && frames.get(0).getKeyTime() < this.time) {
            this.move(
                frames.get(0).getX(),
                frames.get(0).getY()
            );

            frames.remove(0);

            this.resetMovementUpdate();
        }
    }
}
