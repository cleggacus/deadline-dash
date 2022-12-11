package com.group22.gui;

import com.group22.Game;
import com.group22.GameState;
import com.group22.gui.base.MenuPane;

import javafx.scene.control.Button;
import javafx.scene.control.Label;

/**
 * The class {@code LevelComplete} extends MenuPane and shows when a 
 * level is completed.
 * 
 * @author Liam Clegg
 * @version 1.0
 */
public class LevelComplete extends MenuPane {
    /** Label which holds the score value. */
    private Label scoreLabel;
    /** Label which holds the time value. */
    private Label timeLabel;
    /** Button which either links to credits or the next level. */
    private Button nextLevelButton;

    /**
     * Creates a LevelComplete pane.
     */
    public LevelComplete() {
        this.addH1("FIN");
        this.addButton("RESTART", 
            () -> Game.getInstance().setGameState(GameState.Playing));
        this.nextLevelButton = this.addButton("NEXT LEVEL", () -> {});

        this.addButton("EXIT", 
            () -> Game.getInstance().setGameState(GameState.Start));

        // Used as a gap.
        this.addParagraph("");

        this.scoreLabel = this.addParagraph("");
        this.timeLabel = this.addParagraph("");

        this.getStyleClass().add("levelcomplete-menu");
    }

    /**
     * Sets the score and time label in the pane.
     * 
     * @param score Value of the score.
     * @param time Time taken to complete the level.
     */
    public void setStats(int score, double time) {
        String timeFinished = String.format("Time: %f", time);
        this.timeLabel.setText(timeFinished );
        this.scoreLabel.setText("Score: " + String.valueOf(score));
    }

    /**
     * Lets the object know if this is the last level.
     * Called to let pane know wheather to link to next level of credits.
     * 
     * @param isLast true if its the last level.
     */
    public void setIsLastLevel(boolean isLast) {
        this.nextLevelButton.setText(isLast ? "CREDITS" : "NEXT LEVEL");

        this.nextLevelButton.setOnMouseClicked(e -> {
            if (isLast) {
                Game.getInstance().setGameState(GameState.CREDITS);
            } else {
                Game.getInstance().startFromNextLevel();
            }
        });
    }
}
