package com.group22.gui;

import java.util.List;

import com.group22.Game;
import com.group22.GameState;
import com.group22.Level;
import com.group22.Profile;
import com.group22.gui.base.ImageList;
import com.group22.gui.base.MenuPane;

public class LevelSelector extends MenuPane {
    private ImageList imageList;
    private Profile currentProfile;
    private GamePane gamePane;

    public LevelSelector(GamePane gamePane) {
        this.gamePane = gamePane;
        this.imageList = new ImageList();

        this.addH1("LEVELS");

        this.add(imageList);

        this.addButton("GO BACK", () -> gamePane.setState(GameState.Start));

        this.imageList.prefHeightProperty().bind(this.heightProperty().multiply(0.5));
        this.imageList.prefWidthProperty().bind(this.widthProperty());
    }

    public void addLevels(List<Level> levels) {
        for(Level level : levels) {
            this.addLevel(level);
        }
    }

    public void clearLevels(){
        this.imageList.removeImages();
    }

    public void setProfile(Profile profile){
        this.currentProfile = profile;
        /*int u = profile.getMaxUnlockedLevelIndex();
        int l = this.imageList.getLength();
        for(int i = 0; i < l; i++){
            if(i >= u){
                this.imageList.get
            }
        }*/
    }

    public void addLevel(Level level) {
        int i = this.imageList.getLength();
        int l = this.currentProfile.getMaxUnlockedLevelIndex();
        String path = "thumb/" + level.getTitle().toLowerCase().replace(" ", "_") + ".png";

        if(i < l){ //unlocked
            this.imageList.addImage(
                level.getTitle(),
                getClass().getResource(path).toString(),
                () -> Game.getInstance().startFromLevel(i),
                "🎓",
                () -> {this.gamePane.getScoresBrowser().setLevel(level.toString(), level.getHighscores());
                    this.gamePane.setState(GameState.ScoresBrowser);
                }
            );

        } else { //locked
            this.imageList.addImage(
                "🔒",
                getClass().getResource(path).toString(),
                () -> this.lockedNotif(level.getTitle()),
                "🎓",
                () -> this.gamePane.getScoresBrowser().setLevel(level.toString(), level.getHighscores())
            );
        }
    }

    private void lockedNotif(String i){
        System.out.println("Level '" + i + "' locked");
        return;
    }
}
