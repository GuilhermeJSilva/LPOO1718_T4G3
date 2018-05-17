package com.lift.game.view.actors.polygon_actor;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.PolygonRegion;
import com.badlogic.gdx.graphics.g2d.PolygonSpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class DiamondPoly extends BasePolyActor{
    public DiamondPoly(float x, float y, int width, int height, int color, PolygonSpriteBatch polygonSpriteBatch) {
        super(x, y, width, height, color, polygonSpriteBatch);
    }

    @Override
    protected PolygonRegion getRegion(Texture texture) {
        float p1 = Math.max((percentage - 0.5f) * 2f, 0);
        float p2 = Math.min(percentage * 2f, 1);
        float w1 = width / 2 * (1 - p1);
        float h1 = height / 2 * p1;
        float h2 = -height / 2 * (1 - p2);
        float w2 = width / 2 * p2;

        PolygonRegion polyReg = new PolygonRegion(new TextureRegion(texture), new float[]{      // Four vertices
                -width / 2, 0,
                -w1, h1,
                0, 0,
                w1, h1,
                width / 2, 0,
                -w2, h2,
                w2, h2,
                0, -height / 2,
                0, h2
        }
                , new short[]{
                0, 1, 2,
                1, 2, 3,
                2, 3, 4,
                5, 7, 8,
                7, 8, 6
        });
        return polyReg;
    }
}
