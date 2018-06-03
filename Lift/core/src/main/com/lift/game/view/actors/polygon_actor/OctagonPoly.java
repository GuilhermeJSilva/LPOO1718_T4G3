package com.lift.game.view.actors.polygon_actor;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.PolygonRegion;
import com.badlogic.gdx.graphics.g2d.PolygonSpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

/**
 * Represents an octagon.
 */
public class OctagonPoly extends BasePolyActor {
    /**
     * Static front of the polygon.
     */
    private PolygonRegion staticFront;

    /**
     * @param pos Position of the polygon
     * @param width Width.
     * @param height Height.
     * @param color Color.
     * @param polygonSpriteBatch Batch.
     */
    public OctagonPoly(Vector2 pos, int width, int height, int color, PolygonSpriteBatch polygonSpriteBatch) {
        super(pos, width, height, color, polygonSpriteBatch);
    }

    @Override
    protected PolygonRegion getRegion(Texture texture) {
        if (staticFront == null) {
            staticFront = new PolygonRegion(new TextureRegion(texture), new float[]{      // Four vertices
                    width /2, 0,
                    (float) Math.sqrt(2) / 2f * width /2, (float) Math.sqrt(2) / 2f * height /2,
                    0, height /2,
                    - (float) Math.sqrt(2) / 2f * width /2, (float) Math.sqrt(2) / 2f * height /2,
                    -width /2, 0,
                    - (float) Math.sqrt(2) / 2f * width /2, -(float) Math.sqrt(2) / 2f * height /2,
                    0, -height /2,
                    (float) Math.sqrt(2) / 2f * width /2, -(float) Math.sqrt(2) / 2f * height /2,
                    0,0

            }
                    , new short[]{
                    0, 1, 8,
                    1, 2, 8,
                    2, 3, 8,
                    3, 4, 8,
                    4, 5, 8,
                    5, 6, 8,
                    6, 7, 8,
                    7, 0, 8
            });
        }
        return staticFront;
    }
}
