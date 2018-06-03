package com.lift.game.controller.entities;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.lift.game.controller.utils.CollisionVariables;
import com.lift.game.controller.utils.PhysicalVariables;

import static com.lift.game.controller.entities.ElevatorBody.ELEVATOR_MASK;
import static com.lift.game.controller.entities.PlatformBody.PLATFORM_MASK;

/**
 * Creates a screen boundary sensor.
 */
public class ScreenSensorBody extends EntityBody {

    /**
     * Screen width in meters.
     */
    public static final float SCREEN_WIDTH = 45;

    /**
     * Screen height in meters.
     */
    public static final float SCREEN_HEIGHT = 80;

    /**
     * Sensor height.
     */
    public static final float SENSOR_HEIGHT = 1;

    /**
     * Bottom sensor offset.
     */
    public static final float BOTTOM_OFFSET = 3;

    /**
     * Bottom sensor collision mask.
     */
    public static final short BOTTOM_SENSOR = 1 << 7;



    /**
     * Creates an entity Body.
     *
     * @param world World the body is going to be inserted in.
     */
    public ScreenSensorBody(World world) {
        super(world, new Vector2(SCREEN_WIDTH / 2, SCREEN_HEIGHT / 2));

        PhysicalVariables phys = new PhysicalVariables(SCREEN_WIDTH, SCREEN_HEIGHT, 0f, 0f, 0f);
        CollisionVariables cv = new CollisionVariables(BOTTOM_SENSOR, (short)(PLATFORM_MASK | ELEVATOR_MASK), true);

        this.add_fixture(body, new float[]{0, SCREEN_HEIGHT - SENSOR_HEIGHT - BOTTOM_OFFSET, 0, SCREEN_HEIGHT - BOTTOM_OFFSET, SCREEN_WIDTH, SCREEN_HEIGHT - SENSOR_HEIGHT - BOTTOM_OFFSET, SCREEN_WIDTH, SCREEN_HEIGHT - BOTTOM_OFFSET}
                , phys, cv);

        this.body.setGravityScale(0);
    }
}
