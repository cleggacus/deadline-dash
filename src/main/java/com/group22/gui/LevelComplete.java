package com.group22.gui;

import com.group22.Game;
import com.group22.GameState;
import com.group22.gui.base.MenuPane;

import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class LevelComplete extends MenuPane {
    private Label stats;
    private Button nextLevelButton;

    public LevelComplete() {
        this.addH1("FIN");
        this.addButton("RESTART", 
            () -> Game.getInstance().setGameState(GameState.Playing));
        this.nextLevelButton = this.addButton("NEXT LEVEL", () -> {});

        this.addButton("EXIT", 
            () -> Game.getInstance().setGameState(GameState.Start));

        this.addParagraph("");
        this.stats = this.addParagraph("");

        this.getStyleClass().add("levelcomplete-menu");
    }

    public void setStats(int score, double time) {
        String timeFinished = String.format("Time: %f", time);
        this.stats.setText(timeFinished + " Score: " + String.valueOf(score));
    }

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
