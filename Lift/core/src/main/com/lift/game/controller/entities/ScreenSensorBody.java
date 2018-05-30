package com.lift.game.controller.entities;

import com.badlogic.gdx.physics.box2d.World;
import com.lift.game.controller.utils.PhysicalVariables;

import static com.lift.game.controller.entities.PlatformBody.PLATFORM_MASK;

/**
 * Creates a screen boundary sensor.
 */
public class ScreenSensorBody extends EntityBody {

    /**
     * Screen ELEVATOR_WIDTH in meters.
     */
    public static final float SCREEN_WIDTH = 45;

    /**
     * Screen ELEVATOR_HEIGHT in meters.
     */
    public static final float SCREEN_HEIGHT = 80;

    /**
     * Sensor ELEVATOR_HEIGHT.
     */
    public static final float SENSOR_HEIGHT = 1;

    /**
     * Bottom sensor offset.
     */
    public static final float TOP_OFFSET = 1;

    /**
     * Top sensor offset.
     */
    public static final float BOTTOM_OFFSET = 2;

    /**
     * Top sensor collision mask.
     */
    public static final short BOTTOM_SENSOR = 1 << 7;

    /**
     * Bottom sensor collision mask.
     */
    public static final short TOP_SENSOR = 1 << 8;


    /**
     * Creates an entity Body.
     *
     * @param world World the body is going to be inserted in.
     */
    public ScreenSensorBody(World world) {
        super(world, SCREEN_WIDTH / 2, SCREEN_HEIGHT / 2);

        PhysicalVariables phys = new PhysicalVariables(SCREEN_WIDTH, SCREEN_HEIGHT, 0f, 0f, 0f);


        this.add_fixture(body, new float[]{0, TOP_OFFSET, 0, TOP_OFFSET + SENSOR_HEIGHT, SCREEN_WIDTH, TOP_OFFSET, SCREEN_WIDTH, TOP_OFFSET + SENSOR_HEIGHT}
                , phys, TOP_SENSOR, PLATFORM_MASK, true);


        this.add_fixture(body, new float[]{0, SCREEN_HEIGHT - SENSOR_HEIGHT - BOTTOM_OFFSET, 0, SCREEN_HEIGHT - BOTTOM_OFFSET, SCREEN_WIDTH, SCREEN_HEIGHT - SENSOR_HEIGHT - BOTTOM_OFFSET, SCREEN_WIDTH, SCREEN_HEIGHT - BOTTOM_OFFSET}
                , phys, BOTTOM_SENSOR, PLATFORM_MASK, true);

        this.body.setGravityScale(0);
    }
}
