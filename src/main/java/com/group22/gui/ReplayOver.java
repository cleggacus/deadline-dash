package com.group22.gui;

import com.group22.Game;
import com.group22.GameState;
import com.group22.TimeUtil;
import com.group22.gui.base.MenuPane;

import javafx.scene.control.Label;

public class ReplayOver extends MenuPane {
    private Label scoreLabel;
    private Label timeLabel;

    public ReplayOver() {
        this.addH1("REPLAY COMPLETE");
        this.addButton("EXIT", 
            () -> Game.getInstance().setGameState(GameState.ReplaysBrowser));
        this.addParagraph("");
        this.scoreLabel = this.addParagraph("");
        this.timeLabel = this.addParagraph("");
        this.getStyleClass().add("gameover-menu");
    }

    public void setStats(int score, double time){
        this.timeLabel.setText("Time: " + TimeUtil.getLevelTimeLeft(time));
        this.scoreLabel.setText("Score: " + String.valueOf(score));
    }
}
