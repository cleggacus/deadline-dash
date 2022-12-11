package com.group22.gui;

import com.group22.Game;
import com.group22.GameState;
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
    private Label scoreLabel;
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
     * @param score integer for the score to be displayed.
     * @param time double for the time to be displayed.
     */
    public void setStats(int score, double time) {
        this.scoreLabel.setText("SCORE: " + score);
        this.timeLabel.setText("TIME: " + time);
    }
}
