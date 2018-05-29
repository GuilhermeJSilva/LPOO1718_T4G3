package com.lift.game.controller.entities;

import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.World;

/**
 * Creates a screen boundary sensor.
 */
public class ScreenSensorBody extends EntityBody{
    /**
     * Creates an entity Body.
     *
     * @param world    World the body is going to be inserted in.
     * */
    protected ScreenSensorBody(World world, int min, int max) {
        super(world, null, BodyDef.BodyType.StaticBody);

    }
}
