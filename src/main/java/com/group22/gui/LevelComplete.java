package com.group22.gui;

import com.group22.Game;
import com.group22.GameState;
import com.group22.gui.base.MenuPane;

public class LevelComplete extends MenuPane {
    private GamePane gamePane;
    public LevelComplete(GamePane gamePane) {
        this.gamePane = gamePane;

        setUpLevelComplete();

        this.getStyleClass().add("levelcomplete-menu");
    }
    private void setUpLevelComplete(){
        this.getChildren().clear();
        this.addH1("FIN");
        this.addButton("RESTART", () -> Game.getInstance().setGameState(GameState.Playing));
        this.addButton("EXIT", () -> gamePane.setState(GameState.Start));
    }

    public void setStats(int score, double time) {
        setUpLevelComplete();
        String timeFinished = String.format("Time: %f", time);
        this.addParagraph(timeFinished + " Score: " + String.valueOf(score));
    }
}
