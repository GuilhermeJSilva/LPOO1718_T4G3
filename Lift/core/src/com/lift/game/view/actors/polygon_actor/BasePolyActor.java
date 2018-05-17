package com.lift.game.view.actors.polygon_actor;

import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.scenes.scene2d.Actor;

public abstract class BasePolyActor extends Actor {

    protected float percentage = 1;

    private PolygonSprite poly;
    private PolygonSprite back;
    protected int width;
    protected int height;
    protected Texture front;
    private PolygonSpriteBatch polyBatch;


    BasePolyActor(float x, float y, int width, int height, int color, PolygonSpriteBatch polygonSpriteBatch) {
        this.width = width;
        this.height = height;
        Pixmap pix = new Pixmap(width, height, Pixmap.Format.RGBA8888);
        pix.setColor(color); // DE is red, AD is green and BE is blue.
        pix.fill();
        front = new Texture(pix);

        pix.setColor(0x000000FF); // DE is red, AD is green and BE is blue.
        pix.fill();
        Texture backTexture = new Texture(pix);

        poly = new PolygonSprite(getRegion(front));
        back = new PolygonSprite(getRegion(backTexture));
        poly.setPosition(x ,y);
        back.setPosition(x ,y );

        polyBatch = polygonSpriteBatch;
    }

    public void setPercentage(float percentage) {
        this.percentage = percentage;
    }

    public void decPercentage(float delta) {
        this.percentage -= delta;
    }

    /**
     * Sets the position of the actor's bottom left corner.
     *
     * @param x
     * @param y
     */
    @Override
    public void setPosition(float x, float y) {
        poly.setPosition(x, y);
        back.setPosition(x,y);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch,parentAlpha);
        poly.setRegion(getRegion(front));
        polyBatch.begin();
        back.draw(polyBatch);
        if (percentage > 0f) {
            poly.draw(polyBatch);
        }
        polyBatch.end();

    }


    protected abstract PolygonRegion getRegion(Texture texture);
}
