package com.lift.game.view.actors.polygon_actor;

import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.scenes.scene2d.Actor;

public abstract class BasePolyActor extends Actor {

    protected float percentage = 1;

    private PolygonSprite poly;
    protected int width;
    protected int height;
    protected Texture textureSolid;
    private PolygonSpriteBatch polyBatch;


    BasePolyActor(float x, float y, int width, int height, int color) {
        this.width = width;
        this.height = height;
        Pixmap pix = new Pixmap(width, height, Pixmap.Format.RGBA8888);
        pix.setColor(color); // DE is red, AD is green and BE is blue.
        pix.fill();
        textureSolid = new Texture(pix);

        poly = new PolygonSprite(getRegion());
        poly.setPosition(x, y);

        polyBatch = new PolygonSpriteBatch();
    }

    public void setPercentage(float percentage) {
        this.percentage = percentage;
    }

    public void decPercentage(float delta) {
        this.percentage -= delta;
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        if (percentage > 0f) {
            poly.setRegion(getRegion());
            polyBatch.begin();
            poly.draw(polyBatch);
            polyBatch.end();
        }
    }

    protected abstract PolygonRegion getRegion();
}
