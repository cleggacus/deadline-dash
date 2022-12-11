package com.group22.gui;

import com.group22.Game;
import com.group22.GameState;
import com.group22.TimeUtil;
import com.group22.gui.base.MenuPane;

import javafx.scene.control.Label;

/**
 * The class {@code GameOver} is a menu for when someone loses the game 
 * which extends MenuPane.
 * 
 * @author Liam Clegg
 * @version 1.0
 */
public class GameOver extends MenuPane {
    /** The label element that contains the score. */
    private Label scoreLabel;
    /** The label element that contains the time. */
    private Label timeLabel;

    /**
     * Creates GameOver pane.
     */
    public GameOver() {
        this.getChildren().clear();
        this.addH1("GAMEOVER");
        this.addButton("RESTART", 
            () -> Game.getInstance().setGameState(GameState.Playing));
        this.addButton("EXIT", 
            () -> Game.getInstance().setGameState(GameState.Start));

        this.scoreLabel = this.addParagraph("");
        this.timeLabel = this.addParagraph("");

        this.getStyleClass().add("gameover-menu");
    }

    /**
     * Sets the stats in the GameOver pane.
     * 
     * @param score value to be set in {@link #scoreLabel} text.
     * @param time value to be set in {@link #timeLabel} text.
     */
    public void setStats(int score, double time) {
        this.scoreLabel.setText("SCORE: " + score);
        this.timeLabel.setText("TIME: " + TimeUtil.getLevelTimeLeft(time));
    }
}
