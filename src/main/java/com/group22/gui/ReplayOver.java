package com.group22.gui;

import com.group22.Game;
import com.group22.GameState;
import com.group22.gui.base.MenuPane;

import javafx.scene.control.Label;

public class ReplayOver extends MenuPane {
    private Label stats;
    private GamePane gamePane;

    public ReplayOver() {
        this.addH1("REPLAY COMPLETE");
        this.addButton("EXIT", () -> Game.getInstance().setGameState(GameState.ReplaysBrowser));
        this.addParagraph("");
        this.stats = this.addParagraph("");

        this.getStyleClass().add("gameover-menu");
    }

    public void setStats(int score, double time){
        String timeFinished = String.format("Time: %f", time);
        this.stats.setText(timeFinished + " Score: " + String.valueOf(score));
    }
}
