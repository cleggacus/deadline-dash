package com.group22.gui.base;

import java.util.ArrayList;

import com.group22.gui.base.ListButton.OnClickEvent;

import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Rectangle;

public class ImageList extends ScrollPane {
    private double duration = 0.3;
    private double hoverTimer = 0;
    private int hoverImage = -1;

    private double unhoverTimer = 0;
    private int unhoverImage = -1;

    private GridPane grid;
    private ArrayList<StackPane> stackPanes;

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

    public ArrayList<StackPane> getStackPanes() {
        return stackPanes;
    }

    public void addImage(String text, String path, OnClickEvent onClickEvent, String footerText, OnClickEvent onFooterClickEvent) {
        BorderPane outer = new BorderPane();

        StackPane hoverImage = createHoverImage(text, path);
        BorderPane footer = createFooter(footerText);

        hoverImage.setOnMouseClicked(e -> {
            if(e.getButton() == MouseButton.PRIMARY) {
                onClickEvent.run();
            }
        });

        footer.setOnMouseClicked(e -> {
            if(e.getButton() == MouseButton.PRIMARY) {
                onFooterClickEvent.run();
            }
        });

        outer.setCenter(hoverImage);
        outer.setBottom(footer);

        outer.prefHeightProperty().bind(this.grid.heightProperty());

        this.grid.add(outer, this.grid.getColumnCount(), 0);
    }

    private BorderPane createFooter(String text) {
        BorderPane borderPane = new BorderPane();
        Label label = new Label(text);
        
        borderPane.getStyleClass().add("footer");

        borderPane.setCenter(label);

        return borderPane;
    }

    private StackPane createHoverImage(String text, String path) {
        StackPane stackPane = new StackPane();
        stackPane.setMinHeight(0);


        ImageView imageView = new ImageView(path);
        imageView.setPreserveRatio(true);
        imageView.fitHeightProperty().bind(stackPane.heightProperty());


        BorderPane borderPane = new BorderPane();
        Label label = new Label(text);
        label.setWrapText(true);

        double ratio = imageView.getImage().getWidth() / imageView.getImage().getHeight();
        label.prefWidthProperty().bind(imageView.fitHeightProperty().multiply(ratio));

        borderPane.setCenter(label);
        borderPane.getStyleClass().add("text-overlay");

        borderPane.setOpacity(0);

        stackPane.getChildren().add(imageView);
        stackPane.getChildren().add(borderPane);

        int i = this.stackPanes.size();

        stackPane.setOnMouseEntered(e -> {
            if(unhoverImage == i) {
                unhoverImage = -1;
                hoverTimer = duration - unhoverTimer;
                unhoverTimer = 0;
            }

            hoverImage = i;
        });

        stackPane.setOnMouseExited(e -> {
            if(hoverImage == i) {
                hoverImage = -1;
                unhoverTimer = duration - hoverTimer;
                hoverTimer = 0;
            }

            unhoverImage = i;
        });

        this.stackPanes.add(stackPane);

        return stackPane;
    }

    public int getLength() {
        return this.grid.getColumnCount();
    }

    public Node getImage(int i){
        return this.grid.getChildren().get(i);
    }

    public void update(double delta) {

        if(this.hoverImage >= 0) {
            double amount = this.hoverTimer / duration;
            amount = amount > 1 ? 1 : amount;

            StackPane stackPane = this.stackPanes.get(hoverImage);
            ImageView imageView = (ImageView)stackPane.getChildren().get(0);
            BorderPane overlay = (BorderPane)stackPane.getChildren().get(1);
            GaussianBlur gaussianBlur = new GaussianBlur(amount * 10);

            imageView.setClip(new Rectangle(stackPane.getWidth(), stackPane.getHeight()));
            imageView.setEffect(gaussianBlur);
            overlay.setOpacity(amount);

            if(this.hoverTimer > duration) {
                this.hoverImage = -1;
                this.hoverTimer = 0;
            } else {
                this.hoverTimer += delta;
            }
        }

        if(this.unhoverImage >= 0) {
            double amount = 1 - (this.unhoverTimer / duration);
            amount = amount < 0 ? 0 : amount;

            StackPane stackPane = this.stackPanes.get(unhoverImage);
            ImageView imageView = (ImageView)stackPane.getChildren().get(0);
            BorderPane overlay = (BorderPane)stackPane.getChildren().get(1);

            GaussianBlur gaussianBlur = new GaussianBlur(amount * 10);

            imageView.setClip(new Rectangle(stackPane.getWidth(), stackPane.getHeight()));
            imageView.setEffect(gaussianBlur);
            overlay.setOpacity(amount);

            if(this.unhoverTimer > duration) {
                this.unhoverImage = -1;
                this.unhoverTimer = 0;
            } else {
                this.unhoverTimer += delta;
            }
        }
    }
}
