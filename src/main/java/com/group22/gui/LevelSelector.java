package com.group22.gui;

import java.util.List;

import com.group22.Game;
import com.group22.GameState;
import com.group22.Level;
import com.group22.Profile;
import com.group22.ReplayManager;
import com.group22.gui.base.ImageList;
import com.group22.gui.base.MenuPane;

import javafx.scene.layout.StackPane;

public class LevelSelector extends MenuPane {
    private double shakeTimer = 0;
    private int lockNotify = -1;
    private ImageList imageList;
    private Profile currentProfile;
    private GamePane gamePane;
    private ReplayManager replayManager;

    public LevelSelector(GamePane gamePane) {
        this.gamePane = gamePane;
        this.imageList = new ImageList();
        this.replayManager = new ReplayManager();

        this.addH1("LEVELS");

        this.add(imageList);

        this.addButton("GO BACK", () -> gamePane.setState(GameState.Start));

        this.imageList.prefHeightProperty().bind(this.heightProperty().multiply(0.5));
        this.imageList.prefWidthProperty().bind(this.widthProperty());
    }

    public void update() {
        this.imageList.update(Game.getInstance().getDelta());
        if(lockNotify >= 0) {
            shakeTimer += Game.getInstance().getDelta();
            StackPane stackPane = this.imageList.getStackPanes().get(lockNotify);
            stackPane.setTranslateX(5 * Math.sin(40*shakeTimer));

            if(shakeTimer >= 0.5) {
                shakeTimer = 0;
                stackPane.setTranslateX(0);
                this.lockNotify = -1;
            }
        }
    }

    public void addLevels(List<Level> levels) {
        for(Level level : levels) {
            this.addLevel(level);
        }
    }

    public void clearLevels(){
        this.imageList = new ImageList();
        this.replace(this.imageList, 1);

    }

    public void setProfile(Profile profile){
        this.currentProfile = profile;
    }

    public void addLevel(Level level) {
        int i = this.imageList.getLength();
        int l = this.currentProfile.getMaxUnlockedLevelIndex() + 1;
        String path = "thumb/" + level.getTitle().toLowerCase().replace(" ", "_") + ".png";

        if(i < l){ //unlocked
            this.imageList.addImage(
                level.getTitle(),
                getClass().getResource(path).toString(),
                () -> Game.getInstance().startFromLevel(i),
                "▶",
                () -> {this.gamePane.getReplaysBrowser().setReplays(replayManager.getReplaysFromLevel(level), level.getTitle(), i);
                    this.gamePane.setState(GameState.ReplaysBrowser);
                }
            );

        } else { //locked
            this.imageList.addImage(
                "🔒",
                getClass().getResource(path).toString(),
                () -> {
                    lockNotify = i;
                },
                "👨‍🎓",
                () -> {this.gamePane.getScoresBrowser().setLevel(level.toString(), level.getHighscores());
                    this.gamePane.setState(GameState.ScoresBrowser);}
                    );
        }
    }
}
