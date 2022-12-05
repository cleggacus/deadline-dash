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


        stackPane.getChildren().add(imageView);
        stackPane.getChildren().add(borderPane);


        stackPane.setOnMouseEntered(e -> {
            GaussianBlur gaussianBlur = new GaussianBlur(10);
            imageView.setClip(new Rectangle(stackPane.getWidth(), stackPane.getHeight()));
            imageView.setEffect(gaussianBlur);
        });

        stackPane.setOnMouseExited(e -> {
            imageView.setEffect(null);
        });

        this.stackPanes.add(stackPane);

        return stackPane;
    }

    public int getLength() {
        return this.grid.getColumnCount();
    }

    public void removeImages(){
        this.grid.getChildren().clear();
        this.stackPanes.clear();
    }

    public Node getImage(int i){
        return this.grid.getChildren().get(i);
    }

}
