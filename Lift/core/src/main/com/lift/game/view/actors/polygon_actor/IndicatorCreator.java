package com.lift.game.view.actors.polygon_actor;

import com.badlogic.gdx.graphics.g2d.PolygonSpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.lift.game.model.entities.person.PersonType;
import com.lift.game.view.actors.polygon_actor.BasePolyActor;
import com.lift.game.view.actors.polygon_actor.DiamondPoly;
import com.lift.game.view.actors.polygon_actor.OctagonPoly;

import static com.lift.game.view.GameView.PIXEL_TO_METER;

/**
 * Creates an indicator accordingly to the person type.
 */
public class IndicatorCreator {

    /**
     * Width of the indicator.
     */
    public static final float INDICATOR_WIDTH = 2.5f / PIXEL_TO_METER;

    /**
     * Height of the indicator.
     */
    public static final float INDICATOR_HEIGHT = (3 / PIXEL_TO_METER);

    /**
     * Creates the indicator.
     * @param pos Position of the indicator.
     * @param personType Type of person.
     * @param color Color of the indicator.
     * @param polygonSpriteBatch Destination of the polygon print.
     * @return Created polygon actor.
     */
    public static BasePolyActor createIndicator(Vector2 pos, PersonType personType, int color, PolygonSpriteBatch polygonSpriteBatch) {
        System.out.println((int) INDICATOR_WIDTH + " " +  (int) INDICATOR_HEIGHT);
        switch (personType) {

            case Regular:
                return  new DiamondPoly(pos.x, pos.y, (int) INDICATOR_WIDTH, (int) INDICATOR_HEIGHT, color, polygonSpriteBatch);
            case Drunken:
                return new OctagonPoly(pos.x, pos.y, (int) INDICATOR_WIDTH, (int) INDICATOR_HEIGHT, color, polygonSpriteBatch);
        }
        return new DiamondPoly(pos.x, pos.y, (int) INDICATOR_WIDTH, (int) INDICATOR_HEIGHT, 0x000000f, polygonSpriteBatch);
    }
}
