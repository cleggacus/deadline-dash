package com.gorup22;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public abstract class Entity {
    protected int x;
    protected int y;

    private Image sprite;

    public Entity() {

    }

    public abstract void update();

    public void draw(GraphicsContext ctx) {
        if(this.sprite == null)
            return;

        ctx.drawImage(this.sprite, x, y);
    }

}
