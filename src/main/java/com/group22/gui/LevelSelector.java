package com.group22.gui;

import java.util.List;

import com.group22.Game;
import com.group22.GameState;
import com.group22.Level;
import com.group22.Profile;
import com.group22.gui.base.ImageList;
import com.group22.gui.base.MenuPane;
import com.group22.gui.base.ListButton.OnClickEvent;

import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;

/** LevelSelector is the menu in which users pick which level they want to play.
 *  It allows the user to navigate to the replays and saves browsers for each
 *  level. It also displays levels that the user hasn't unlocked yet.
 * @author Sam Austin
 * @version 1.1
*/
public class LevelSelector extends MenuPane {
    private double shakeTimer = 0;
    private int lockClicked = -1;
    private ImageList imageList;
    private Profile currentProfile;
    private GamePane gamePane;

    /**
     * Initialiser for the {@code LevelSelector}
     * @param gamePane The current {@link GamePane} object
     */
    public LevelSelector(GamePane gamePane) {
        this.gamePane = gamePane;
        this.imageList = new ImageList();

        this.addH1("LEVELS");
        this.add(imageList);
        this.addButton("GO BACK", 
            () -> Game.getInstance().setGameState(GameState.START));

        this.imageList.prefHeightProperty().bind(
            this.heightProperty().multiply(0.5));
        this.imageList.prefWidthProperty().bind(this.widthProperty());
    }

    /**
     * This method handles image shaking when a locked level is clicked by the
     * user. If the lockClicked variable is greater than or equal to 0, the
     * shakeTimer is started, the stackPane is translated using a sine wave,
     * and if the shakeTimer is greater than or equal to 0.5, then the
     * shakeTimer is set to 0 and the animation is stopped.
     */
    public void update() {
        this.imageList.update(Game.getInstance().getDelta());
        if (lockClicked >= 0) {
            shakeTimer += Game.getInstance().getDelta();
            StackPane stackPane = this.imageList.getStackPanes().get(
                lockClicked);
            stackPane.setTranslateX(5 * Math.sin(40*shakeTimer));

            if (shakeTimer >= 0.5) {
                shakeTimer = 0;
                stackPane.setTranslateX(0);
                this.lockClicked = -1;
            }
        }
    }

    /**
     * Passes each level to {@link #addLevel(Level) addLevel}
     * @param levels A {@link List} of {@link Level}'s
     */
    public void addLevels(List<Level> levels) {
        for (Level level : levels) {
            this.addLevel(level);
        }
    }

    /**
     * This function clears the imageList of all items
     */
    public void clearLevels() {
        this.imageList = new ImageList();
        this.replace(this.imageList, 1);

    }

    /**
     * Sets the current profile so the level selector has access to 
     * whos playing.
     * 
     * @param profile profile that is set.
     */
    public void setProfile(Profile profile) {
        this.currentProfile = profile;
    }

    /**
     * Creates an {@link ImageList} object with the details of the {@link Level}
     * @param level The level to add to the {@link ImageList}
     */
    public void addLevel(Level level) {
        int currentLevelIndex = this.imageList.getLength();
        int maxUnlockedLevel = this.currentProfile
            .getMaxUnlockedLevelIndex() + 1;

        // set path to the current levels thumbnail image path
        String path = "thumb/" + level.getTitle().toLowerCase()
        .replace(" ", "_") + ".png";

        if (currentLevelIndex < maxUnlockedLevel) { // level is unlocked
            this.imageList.addImage(
                // add image
                level.getTitle(),
                getClass().getResource(path).toString(),
                () -> Game.getInstance().startFromLevel(currentLevelIndex),
                new Button[] {
                    createButton("🔁", () -> {
                        // set replays in the replays browser for this level
                        this.gamePane.getReplaysBrowser().setReplays(
                            level.getTitle(), currentLevelIndex);
                        Game.getInstance()
                            .setGameState(GameState.REPLAYS_BROWSER);
                    }),
                    createButton("💾", () -> {
                        /*  set saved states in the saves
                            browser for this level and user */
                        this.gamePane.getSavesBrowser().setSavedStates(
                            level, currentLevelIndex);
                        Game.getInstance()
                            .setGameState(GameState.SAVES_BROWSER);
                    }),
                    createButton("⏵", () -> 
                        Game.getInstance().startFromLevel(currentLevelIndex)
                    )
                }
            );

        } else { // level is locked
            this.imageList.addImage(
                // add image
                "🔒",
                getClass().getResource(path).toString(),
                () -> lockClicked = currentLevelIndex,
                new Button[] {
                    createButton("🔁", () -> {
                        // set replays in the replays browser for this level
                        this.gamePane.getReplaysBrowser().setReplays(
                            level.getTitle(), currentLevelIndex);
                        Game.getInstance()
                            .setGameState(GameState.REPLAYS_BROWSER);
                    }),
                    createButton("💾", () -> {
                        /*  set saved states in the saves
                            browser for this level and user */
                        this.gamePane.getSavesBrowser().setSavedStates(
                            level, currentLevelIndex);
                        Game.getInstance()
                            .setGameState(GameState.SAVES_BROWSER);
                    }),
                    createButton("🔒", () -> 
                        lockClicked = currentLevelIndex
                    )
                }
            );
        }
    }

    /**
     * Helper to create a button for the footer buttons.
     * 
     * @param name Text in button.
     * @param onClick Called when button is clicked.
     * @return The button which is created.
     */
    private Button createButton(String name, OnClickEvent onClick) {
        Button button = new Button(name);
        button.setOnAction(e -> onClick.run());
        return button;
    }
}
