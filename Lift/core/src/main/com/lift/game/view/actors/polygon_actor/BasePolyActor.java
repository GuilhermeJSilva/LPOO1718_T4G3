package com.lift.game.view.actors.polygon_actor;

import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.PolygonRegion;
import com.badlogic.gdx.graphics.g2d.PolygonSprite;
import com.badlogic.gdx.graphics.g2d.PolygonSpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;

/**
 * Used to draw polygons on the screen.
 */
public abstract class BasePolyActor extends Actor {

    /**
     * Percentage filled of the front polygon.
     */
    float percentage = 1;

    /**
     * Front polygon.
     */
    private final PolygonSprite poly;

    /**
     * Back polygon.
     */
    private final PolygonSprite back;

    /**
     * Width of polygons.
     */
    final int width;

    /**
     * Height of polygons.
     */
    final int height;

    /**
     * Texture of the front polygon.
     */
    private final Texture front;

    /**
     * The polygons are drawn in this batch.
     */
    private final PolygonSpriteBatch polyBatch;


    /**
     * @param pos Position of the polygon
     * @param width Width of the polygon.
     * @param height Height of the polygon.
     * @param color Color of the front polygon.
     * @param polygonSpriteBatch Draw destination.
     */
    BasePolyActor(Vector2 pos, int width, int height, int color, PolygonSpriteBatch polygonSpriteBatch) {
        this.width = width;
        this.height = height;
        Pixmap pix = new Pixmap(width, height, Pixmap.Format.RGBA8888);
        pix.setColor(color);
        pix.fill();
        front = new Texture(pix);

        pix.setColor(0x000000FF);
        pix.fill();
        Texture backTexture = new Texture(pix);

        poly = new PolygonSprite(getRegion(front));
        back = new PolygonSprite(getRegion(backTexture));
        poly.setPosition(pos.x ,pos.y);
        back.setPosition(pos.x ,pos.y);

        polyBatch = polygonSpriteBatch;
    }

    /**
     * Changes the percentage that is shown of the front polygon.
     * @param percentage New value for the percentage.
     */
    public void setPercentage(float percentage) {
        this.percentage = percentage;
    }


    /**
     * Sets the position of the actor's bottom left corner.
     *
     * @param x Position in the x coordinate.
     * @param y Position in the y coordinate.
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

    /**
     * Returns the region to be displayed according to the percentage.
     * @param texture Texture to be displayed as the front polygon.
     * @return Region to be displayed.
     */
    protected abstract PolygonRegion getRegion(Texture texture);
}
