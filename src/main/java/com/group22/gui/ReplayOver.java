package com.group22.gui;

import com.group22.Game;
import com.group22.GameState;
import com.group22.TimeUtil;
import com.group22.gui.base.MenuPane;

import javafx.scene.control.Label;

/**
 * The class {@code ReplayOver} is the pane which is shown when a 
 * replay has ended.
 * 
 * @author Liam Clegg
 * @version 1.0
 */
public class ReplayOver extends MenuPane {
    /** Label which contains score text. */
    private Label scoreLabel;
    /** Label which contains time text. */
    private Label timeLabel;

    /**
     * Creates ReplayOver instance.
     */
    public ReplayOver() {
        this.addH1("REPLAY COMPLETE");
        this.addButton("EXIT", 
            () -> Game.getInstance().setGameState(GameState.LEVEL_SELECTOR));
        this.addParagraph("");
        this.scoreLabel = this.addParagraph("");
        this.timeLabel = this.addParagraph("");
        this.getStyleClass().add("gameover-menu");
    }

    /**
     * Sets the score and time gui elements.
     * 
     * @param score Sets {@link #scoreLabel} text to score value.
     * @param time Sets {@link #timeLabel} text to score value.
     */
    public void setStats(int score, double time) {
        this.timeLabel.setText("Time: " + TimeUtil.getLevelTimeLeft(time));
        this.scoreLabel.setText("Score: " + String.valueOf(score));
    }
}
