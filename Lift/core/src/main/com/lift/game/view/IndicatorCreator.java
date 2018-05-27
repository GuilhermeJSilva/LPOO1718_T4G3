package main.com.lift.game.view;

import static main.com.lift.game.view.GameView.PIXEL_TO_METER;

import com.badlogic.gdx.graphics.g2d.PolygonSpriteBatch;
import com.badlogic.gdx.math.Vector2;

import main.com.lift.game.model.entities.person.PersonType;
import main.com.lift.game.view.actors.polygon_actor.BasePolyActor;
import main.com.lift.game.view.actors.polygon_actor.DiamondPoly;
import main.com.lift.game.view.actors.polygon_actor.OctagonPoly;

public class IndicatorCreator {

    public static final float INDICATOR_WIDTH = 2.5f / PIXEL_TO_METER;

    private static final float INDICATOR_HEIGHT = (3 / PIXEL_TO_METER);

    public static BasePolyActor createIndicator(Vector2 pos, PersonType personType, int color, PolygonSpriteBatch polygonSpriteBatch) {
        switch (personType) {

            case Regular:
                return  new DiamondPoly(pos.x, pos.y, (int) INDICATOR_WIDTH, (int) INDICATOR_HEIGHT, color, polygonSpriteBatch);
            case Drunken:
                return new OctagonPoly(pos.x, pos.y, (int) INDICATOR_WIDTH, (int) INDICATOR_HEIGHT, color, polygonSpriteBatch);
        }
        return new DiamondPoly(pos.x, pos.y, (int) INDICATOR_WIDTH, (int) INDICATOR_HEIGHT, 0x000000f, polygonSpriteBatch);
    }
}
