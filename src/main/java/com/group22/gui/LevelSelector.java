package com.group22.gui;

import java.util.List;

import com.group22.Game;
import com.group22.GameState;
import com.group22.gui.base.ImageList;
import com.group22.gui.base.MenuPane;

public class LevelSelector extends MenuPane {
    private ImageList imageList;

    public LevelSelector(GamePane gamePane) {
        this.imageList = new ImageList();

        this.addH1("LEVELS");

        this.add(imageList);

        this.addButton("GO BACK", () -> gamePane.setState(GameState.Start));

        this.imageList.prefHeightProperty().bind(this.heightProperty().multiply(0.5));
        this.imageList.prefWidthProperty().bind(this.widthProperty());
    }

    public void addLevels(List<String> levels) {
        for(String level : levels) {
            this.addLevel(level);
        }
    }

    public void addLevel(String level) {
        String path = "thumb/" + level.toLowerCase().replace(" ", "_") + ".png";
        int i = this.imageList.getLength();

        this.imageList.addImage(
            level,
            getClass().getResource(path).toString(),
            () -> Game.getInstance().startFromLevel(i)
        );
    }
}
