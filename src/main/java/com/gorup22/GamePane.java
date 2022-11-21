package com.gorup22;

import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;

enum State {
    Start, Paused
}

public class GamePane extends StackPane {
    private MenuPane startMenu;
    private MenuPane pauseMenu = new MenuPane();
    private Pane canvasPane = new Pane();
    
    public GamePane() {
        this.setUpStartMenu();
        this.setUpPauseMenu();

        this.setState(State.Start);
    }


    public void setState(State state) {
        System.out.println(state);
        switch (state) {
            case Start:
                this.startMenu.setVisible(true);
                this.pauseMenu.setVisible(false);
                break;
            case Paused:
                this.startMenu.setVisible(false);
                this.pauseMenu.setVisible(true);
                break;
        }
    }

    private void setUpStartMenu() {
        this.startMenu = new MenuPane();
             
        this.startMenu.addItem("Pause", () -> { this.setState(State.Paused); });

        this.getChildren().add(startMenu);
    }

    private void setUpPauseMenu() {
        this.pauseMenu = new MenuPane();
             
        this.pauseMenu.addItem("Resume", () -> { this.setState(State.Start); });

        this.getChildren().add(this.pauseMenu);
    }
}
