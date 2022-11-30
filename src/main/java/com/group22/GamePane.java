package com.group22;

import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.util.List;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.CacheHint;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.TextField;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

/**
 * 
 * The class {@code GamePane} is a scene layout element which extends stack pane.
 * 
 * GamePane is used to abstract the gui into a contained gui element with all functionallity needed for a game. 
 * 
 * @author Liam Clegg
 * @version 1.0
 */
public class GamePane extends StackPane {
    public static final int MENU_BLUR_RADIUS = 20;
    public static final int INFO_BAR_HEIGHT = 50;
    public static final int INFO_BAR_SIZE_PADDING = 20;

    private String username = "";

    private GraphicsContext graphicsContext;
    private MenuPane profileSelectorPane;
    private MenuPane levelSelectorPane;
    private MenuPane startMenu;
    private MenuPane pauseMenu;
    private MenuPane gameOverMenu;
    private BorderPane loadingPane;
    private StackPane canvasPane;

    private Text loadingText;
    private double loadingTimer = 0;

    private BorderPane infoBar;
    private Text time;
    private Text level;
    private Text score;

    private List<String> levels;

    /**
     * Creates a GamePane.
     */
    public GamePane() {
        this.getStylesheets().add(
            getClass().getResource("/com/group22/menu.css").toExternalForm());

        this.getStyleClass().add("game-pane");

        this.setUpCanvasPane();
        this.setUpPauseMenu();
        this.setUpGameOverMenu();
        this.setUpStartMenu();
        this.setUpLoadingPane();
        this.setUpProfileSelector();
        this.setUpLevelSelectorMenu();

        this.setState(GameState.Start);
    }

    
    /** 
     * Changes visibility of child panes based on the game state.
     * Used when {@code Engine} sets its gameState
     * 
     * @param state
     */
    public void setState(GameState state) {
        switch (state) {
            case LevelSelector:
                this.levelSelectorPane.setVisible(true);
                this.profileSelectorPane.setVisible(false);
                this.startMenu.setVisible(false);
                this.pauseMenu.setVisible(false);
                this.canvasPane.setVisible(false);
                this.gameOverMenu.setVisible(false);
                this.loadingPane.setVisible(false);
                setBlurCanvas(false);
                break;
            case ProfileSelector:
                this.profileSelectorPane.setVisible(true);
                this.levelSelectorPane.setVisible(false);
                this.startMenu.setVisible(false);
                this.pauseMenu.setVisible(false);
                this.canvasPane.setVisible(false);
                this.gameOverMenu.setVisible(false);
                this.loadingPane.setVisible(false);
                setBlurCanvas(false);
                break;
            case Start:
                this.profileSelectorPane.setVisible(false);
                this.levelSelectorPane.setVisible(false);
                this.startMenu.setVisible(true);
                this.pauseMenu.setVisible(false);
                this.canvasPane.setVisible(false);
                this.gameOverMenu.setVisible(false);
                this.loadingPane.setVisible(false);
                setBlurCanvas(false);
                break;
            case Playing:
                this.profileSelectorPane.setVisible(false);
                this.levelSelectorPane.setVisible(false);
                this.startMenu.setVisible(false);
                this.pauseMenu.setVisible(false);
                this.canvasPane.setVisible(true);
                this.gameOverMenu.setVisible(false);
                this.loadingPane.setVisible(false);
                setBlurCanvas(false);
                break;
            case Paused:
                this.profileSelectorPane.setVisible(false);
                this.levelSelectorPane.setVisible(false);
                this.startMenu.setVisible(false);
                this.pauseMenu.setVisible(true);
                this.canvasPane.setVisible(true);
                this.gameOverMenu.setVisible(false);
                this.loadingPane.setVisible(false);
                setBlurCanvas(true);
                break;
            case GameOver:
                this.profileSelectorPane.setVisible(false);
                this.levelSelectorPane.setVisible(false);
                this.startMenu.setVisible(false);
                this.pauseMenu.setVisible(false);
                this.canvasPane.setVisible(true);
                this.gameOverMenu.setVisible(true);
                this.loadingPane.setVisible(false);
                setBlurCanvas(true);
                break;
            case Loading:
                this.startMenu.setVisible(false);
                this.levelSelectorPane.setVisible(false);
                this.pauseMenu.setVisible(false);
                this.canvasPane.setVisible(false);
                this.gameOverMenu.setVisible(false);
                this.loadingPane.setVisible(true);
                setBlurCanvas(false);
                this.loadingTimer = 0;
                break;
        }
    }

    public boolean canvasIsVisible() {
        return this.canvasPane.isVisible();
    }

    public void update(double delta) {
        double interval = 0.3;
        this.loadingTimer += delta;

        if(this.loadingTimer > 4*interval)
            this.loadingTimer = 0;
        else if(this.loadingTimer > 3*interval)
            this.loadingText.setText("   LOADING...");
        else if(this.loadingTimer > 2*interval)
            this.loadingText.setText("  LOADING..");
        else if(this.loadingTimer > 1*interval)
            this.loadingText.setText(" LOADING.");
        else
            this.loadingText.setText("LOADING");
    }

    public void setGameTime(double time) {
        DecimalFormat formatter = new DecimalFormat("000");
        this.time.setText("TIME: " + formatter.format(Math.ceil(time)));
    }

    public void setGameLevel(String level) {
        this.level.setText(level);
    }

    public void setGameScore(int score) {
        DecimalFormat formatter = new DecimalFormat("000");
        this.score.setText("SCORE: " + formatter.format(score));
    }

    public void setLevels(List<String> levels) {
        this.levels = levels;
        this.setUpLevelSelectorMenu();
    }
    
    /** 
     * Get GraphicsContext of canvas in pane.
     * 
     * @return GraphicsContext
     */
    public GraphicsContext getGraphicsContext() {
        return graphicsContext;
    }

    public String getUsername() {
        return username;
    }

    public void setGameOffesetX(double offset) {
        this.infoBar.setPadding(new Insets(0, offset, 0, offset));
    }

    private void setBlurCanvas(boolean blur) {
        this.canvasPane.setEffect(blur ? new GaussianBlur(MENU_BLUR_RADIUS) : null);
    }

    private void setUpScore() {
        this.score = new Text();
        this.score.setFont(Font.font("Monospaced", FontWeight.BOLD, 20));
        this.score.setTextAlignment(TextAlignment.CENTER);
        this.score.setFill(TileColor.LIGHT_RED.color);

        BorderPane.setAlignment(this.score, Pos.CENTER);

        this.setGameScore(0);
    }

    private void setUpLevel() {
        this.level = new Text();
        this.level.setFont(Font.font("Monospaced", FontWeight.BOLD, 20));
        this.level.setFill(TileColor.LIGHT_RED.color);

        BorderPane.setAlignment(this.level, Pos.CENTER);
    }

    private void setUpTime() {
        this.time = new Text();
        this.time.setFont(Font.font("Monospaced", FontWeight.BOLD, 20));
        this.time.setFill(TileColor.LIGHT_RED.color);

        BorderPane.setAlignment(this.time, Pos.CENTER);

        this.setGameTime(0);
    }

    private Pane createCanvas() {
        Canvas canvas = new Canvas();
        Pane canvasOuter = new Pane(canvas);

        this.graphicsContext = canvas.getGraphicsContext2D();

        canvas.setCache(true);
        canvas.setCacheHint(CacheHint.SPEED);

        canvas.widthProperty().bind(canvasOuter.widthProperty());
        canvas.heightProperty().bind(canvasOuter.heightProperty());

        return canvasOuter;
    }

    private void setUpInfoBar() {
        this.infoBar = new BorderPane();
        BorderPane innerInfoBar = new BorderPane();

        this.setUpTime();
        this.setUpScore();
        this.setUpLevel();

        HBox right = new HBox();
        right.getChildren().add(this.score);
        right.getChildren().add(this.time);
        right.setAlignment(Pos.CENTER);
        right.setSpacing(INFO_BAR_SIZE_PADDING);

        innerInfoBar.setPadding(new Insets(0, INFO_BAR_SIZE_PADDING, 0, INFO_BAR_SIZE_PADDING));
        innerInfoBar.setPrefHeight(INFO_BAR_HEIGHT);
        innerInfoBar.setRight(right);
        innerInfoBar.setLeft(this.level);

        this.infoBar.setTop(innerInfoBar);
    }

    private void setUpCanvasPane() {
        Pane canvas = createCanvas();
        this.setUpInfoBar();

        this.canvasPane = new StackPane();
        this.canvasPane.getChildren().add(canvas);
        this.canvasPane.getChildren().add(this.infoBar);

        this.getChildren().add(this.canvasPane);
    }



    private void setUpGameOverMenu() {
        this.gameOverMenu = new MenuPane();

        this.gameOverMenu.addTitle("GAME OVER");
        this.gameOverMenu.addItem("Restart", () -> { Game.getInstance().setGameState(GameState.Playing); });
        this.gameOverMenu.addItem("Exit", () -> { Game.getInstance().setGameState(GameState.Start); });

        this.getChildren().add(gameOverMenu);
    }

    private void setUpLevelSelectorMenu() {
        if(this.levelSelectorPane == null) {
            this.levelSelectorPane = new MenuPane();
            this.getChildren().add(levelSelectorPane);
        }

        this.levelSelectorPane.getChildren().clear();
        this.levelSelectorPane.addTitle("LEVELS");

        if(this.levels != null) {
            for(int i = 0; i < levels.size(); i++) {
                final int levelIndex = i;
                this.levelSelectorPane.addCarouselItem(this.levels.get(levelIndex), () -> {
                    this.setGameLevel(this.levels.get(levelIndex));
                    Game.getInstance().startFromLevel(levelIndex);
                });
            }
        }
        this.levelSelectorPane.addItem("BACK", () -> { Game.getInstance().setGameState(GameState.Start); });

        this.levelSelectorPane.maxWidthProperty().bind(this.widthProperty());

    }

    private void setUpStartMenu() {
        this.startMenu = new MenuPane();
             
        this.startMenu.addSubTitle("Hey, " + this.username);
        this.startMenu.addTitle("DEADLINE DASH");
        // will add a continue button in the future once profiles are finised
        this.startMenu.addItem("LEVELS", () -> { Game.getInstance().setGameState(GameState.LevelSelector); });
        this.startMenu.addItem("CHANGE USER", () -> { Game.getInstance().setGameState(GameState.ProfileSelector); });

        this.startMenu.maxWidthProperty().bind(this.widthProperty());

        this.getChildren().add(startMenu);
    }

    private void setUpPauseMenu() {
        this.pauseMenu = new MenuPane();
             
        this.pauseMenu.addItem("RESUME", () -> { Game.getInstance().setGameState(GameState.Playing); });
        this.pauseMenu.addItem("EXIT", () -> { Game.getInstance().setGameState(GameState.Start); });

        this.getChildren().add(this.pauseMenu);
    }

    private void setUpLoadingPane() {
        this.loadingPane = new BorderPane();

        this.loadingText = new Text();
        this.loadingText.setFont(Font.font("Monospaced", FontWeight.BOLD, 40));
        this.loadingText.setFill(TileColor.LIGHT_RED.color);
        this.loadingText.setTextAlignment(TextAlignment.CENTER);
        this.loadingText.wrappingWidthProperty().bind(this.loadingPane.widthProperty());

        this.loadingPane.setCenter(this.loadingText);

        this.getChildren().add(this.loadingPane);
    }

    private void setProfileFromTextField(String username) {
        if(username.replace(" ", "").isEmpty())
            return;

        Profile profile = new Profile(username, LocalDateTime.now());

        if(!profile.exists()){
            profile.saveToFile();
        } else {
            profile.updateTimeActive();
        }
        //this.profileSelectorPane.addItem(username, () -> setProfile(profile));

        this.username = username;
        this.setUpStartMenu();
        Game.getInstance().setGameState(GameState.Start);
    }

    private void setProfile(Profile profile){
        profile.updateTimeActive();
        this.username = profile.getName();
        this.setUpStartMenu();
        Game.getInstance().setGameState(GameState.Start);
    }

    private void setUpProfileSelector() {
        this.profileSelectorPane = new MenuPane();
        
        this.profileSelectorPane.addTitle("SELECT A PROFILE");


        Profile checkProfiles = new Profile();
        List<Profile> profiles = checkProfiles.getAllProfiles();
        for(int i=0; i<profiles.size(); i++){
            final int fi = i;
            this.profileSelectorPane.addItem(profiles.get(i).getName(), () -> setProfile(profiles.get(fi)));
        }
        this.profileSelectorPane.addSubTitle("OR CREATE ONE BELOW");
        TextField field = this.profileSelectorPane.addInput("TYPE USERNAME HERE");
        this.profileSelectorPane.addItem("CREATE", () -> setProfileFromTextField(field.getText()));

        
        this.getChildren().add(this.profileSelectorPane);
    }
}
