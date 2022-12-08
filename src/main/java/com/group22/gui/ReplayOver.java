package com.group22.gui;

import com.group22.Game;
import com.group22.GameState;
import com.group22.gui.base.MenuPane;

public class ReplayOver extends MenuPane {
    private GamePane gamePane;
    public ReplayOver(GamePane gamePane) {
        this.gamePane = gamePane;

        setUpReplayOver();

        this.getStyleClass().add("gameover-menu");
    }
    private void setUpReplayOver(){
        this.getChildren().clear();
        this.addH1("FIN");
        this.addButton("REPLAY", () -> Game.getInstance().setGameState(GameState.Playing));
        this.addButton("EXIT", () -> gamePane.setState(GameState.LevelSelector));
    }

    public void setStats(int score, double time){
        String timeFinished = String.format("Time: %f", time);
        this.addParagraph("Score: " + score);
        this.addParagraph("Time: " + timeFinished);
    }
}
