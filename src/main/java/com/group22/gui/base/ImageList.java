package com.group22.gui.base;

import java.util.ArrayList;

import com.group22.gui.base.ListButton.OnClickEvent;

import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Rectangle;

/**
 * 
 * The class {@code ImageList} is a horizontal List gui element which extends 
 * the ScrollPane JavaFX scene element. It contains an image, onClick event and
 * some footer buttons.
 * 
 * @author Liam Clegg
 * @version 1.0
 */
public class ImageList extends ScrollPane {
    /** Duration of hover animation in seconds. */
    private static final double duration = 0.3;
    /** Keeps current time in hover animation. */
    private double hoverTimer = 0;
    /** Index of image in {@link #stackPanes} that is being hovered. */
    private int hoverImage = -1;
    /** Keeps current time in unhover animation. */
    private double unhoverTimer = 0;
    /** Index of image in {@link #stackPanes} that is being unhovered. */
    private int unhoverImage = -1;
    /** GridPane that contains each element in list. */
    private GridPane grid;
    /** Array list of stack panes that contains the item images. */
    private ArrayList<StackPane> stackPanes;

    /**
     * Creates an {@code ImageList} by setting up a grid pane and outer 
     * scrollPane.
    */
    public ImageList() {
        this.stackPanes = new ArrayList<>();
        this.getStyleClass().add("image-list");

        this.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);

        this.grid = new GridPane();
        this.setFitToHeight(true);
        
        this.setContent(this.grid);

        this.grid.setHgap(40);
        this.grid.setVgap(0);
    }

    /**
     * Gets the list of current {@link #stackPanes}.
     * 
     * @return A list of stackPanes containing images and overlays.
     */
    public ArrayList<StackPane> getStackPanes() {
        return this.stackPanes;
    }

    /**
     * Adds an element to the image list contained in {@link #grid}.
     * 
     * @param text Overlay text when image is hovered.
     * @param path Path to the image in the element.
     * @param onClickEvent Function thats called when elemnet is clicked.
     * @param footerButtons Array of buttons in the footer of the element.
     */
    public void addImage(
        String text, 
        String path, 
        OnClickEvent onClickEvent, 
        Button[] footerButtons) {
            
        BorderPane outer = new BorderPane();

        StackPane hoverImage = createHoverImage(text, path);
        GridPane footer = createFooter(footerButtons);

        hoverImage.setOnMouseClicked(e -> {
            if (e.getButton() == MouseButton.PRIMARY) {
                onClickEvent.run();
            }
        });

        outer.setCenter(hoverImage);
        outer.setBottom(footer);

        outer.prefHeightProperty().bind(this.grid.heightProperty());

        this.grid.add(outer, this.grid.getColumnCount(), 0);
    }

    /**
     * Gets the column count of {@link #grid}.
     * 
     * @return The number of elements in the {@code ImageList}.
     */
    public int getLength() {
        return this.grid.getColumnCount();
    }

    /**
     * Update method which allows hover animations in the game loop.
     * 
     * @param delta Time passed since last frame.
     */
    public void update(double delta) {

        if (this.hoverImage >= 0) {
            double amount = this.hoverTimer / duration;
            amount = amount > 1 ? 1 : amount;

            StackPane stackPane = this.stackPanes.get(hoverImage);
            ImageView imageView = (ImageView)stackPane.getChildren().get(0);
            BorderPane overlay = (BorderPane)stackPane.getChildren().get(1);
            GaussianBlur gaussianBlur = new GaussianBlur(amount * 10);

            imageView.setClip(new Rectangle(
                stackPane.getWidth(), stackPane.getHeight()));
            imageView.setEffect(gaussianBlur);
            overlay.setOpacity(amount);

            if (this.hoverTimer > duration) {
                this.hoverImage = -1;
                this.hoverTimer = 0;
            } else {
                this.hoverTimer += delta;
            }
        }

        if (this.unhoverImage >= 0) {
            double amount = 1 - (this.unhoverTimer / duration);
            amount = amount < 0 ? 0 : amount;

            StackPane stackPane = this.stackPanes.get(unhoverImage);
            ImageView imageView = (ImageView)stackPane.getChildren().get(0);
            BorderPane overlay = (BorderPane)stackPane.getChildren().get(1);

            GaussianBlur gaussianBlur = new GaussianBlur(amount * 10);

            imageView.setClip(new Rectangle(
                stackPane.getWidth(), stackPane.getHeight()));
            imageView.setEffect(gaussianBlur);
            overlay.setOpacity(amount);

            if (this.unhoverTimer > duration) {
                this.unhoverImage = -1;
                this.unhoverTimer = 0;
            } else {
                this.unhoverTimer += delta;
            }
        }
    }

    /**
     * Creates a GridPane that contains the footer from an array of buttons.
     * 
     * @param buttons Array of buttons that are in the footer.
     * @return A footer made with a GridPane element.
     */
    private GridPane createFooter(Button[] buttons) {
        GridPane gridPane = new GridPane();

        gridPane.getStyleClass().add("footer");

        ColumnConstraints column = new ColumnConstraints();
        column.setHalignment(HPos.CENTER);
        column.setPercentWidth(100 / buttons.length);

        for (int i = 0; i < buttons.length; i++) {
            Button button = buttons[i];
            gridPane.addColumn(i, button);
            gridPane.getColumnConstraints().add(column);
        }

        gridPane.setHgap(10);
        gridPane.setVgap(10);

        gridPane.setAlignment(Pos.CENTER);

        return gridPane;
    }

    /**
     * Creates the image view and image overlay for the list element.
     * Adds the StackPane containing the image and overlay 
     * to {@link #stackPanes}.
     * 
     * @param text Overlay text shown on hover.
     * @param path Path to image in element.
     * @return StackPane incuding the image and overlay hidden on top.
     */
    private StackPane createHoverImage(String text, String path) {
        StackPane stackPane = new StackPane();
        stackPane.setMinHeight(0);

        ImageView imageView = new ImageView(path);
        imageView.setPreserveRatio(true);
        imageView.fitHeightProperty().bind(stackPane.heightProperty());

        BorderPane borderPane = new BorderPane();
        Label label = new Label(text);
        label.setWrapText(true);

        double ratio = imageView.getImage().getWidth() / 
            imageView.getImage().getHeight();

        label.prefWidthProperty().bind(
            imageView.fitHeightProperty().multiply(ratio));

        borderPane.setCenter(label);
        borderPane.getStyleClass().add("text-overlay");

        borderPane.setOpacity(0);

        stackPane.getChildren().add(imageView);
        stackPane.getChildren().add(borderPane);

        int i = this.stackPanes.size();

        stackPane.setOnMouseEntered(e -> {
            if (unhoverImage == i) {
                unhoverImage = -1;
                hoverTimer = duration - unhoverTimer;
                unhoverTimer = 0;
            }

            hoverImage = i;
        });

        stackPane.setOnMouseExited(e -> {
            if (hoverImage == i) {
                hoverImage = -1;
                unhoverTimer = duration - hoverTimer;
                hoverTimer = 0;
            }

            unhoverImage = i;
        });

        this.stackPanes.add(stackPane);

        return stackPane;
    }
}
