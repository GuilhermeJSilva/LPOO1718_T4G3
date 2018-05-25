package com.lift.game.controller.entities;

import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.World;
import com.lift.game.model.entities.PlatformModel;

import static com.lift.game.controller.entities.ElevatorBody.ELEVATOR_MASK;
import static com.lift.game.controller.entities.PersonBody.PERSON_MASK;
import static com.lift.game.controller.entities.PersonBody.PERSON_SENSOR_MASK;

public class PlatformBody extends EntityBody {
    /**
     * Platform length.
     */
    public static int PLATFORM_LENGTH = 13;

    /**
     * Platform height.
     */
    public static float PLATFORM_HEIGHT = 0.725f;

    /**
     * Collision bit mask.
     */
    public static final short PLATFORM_MASK = 1;

    public static final short PLATFORM_END_SENSOR = 1 << 3;

    public static final short PLATFORM_ELEVATOR_SENSOR = 1 << 4;


    /**
     * Creates an entity Body.
     *
     * @param world World the body belongs to.
     * @param model Model for the body.
     * @param right True if the sensor is to the right of the platform.
     */
    public PlatformBody(World world, PlatformModel model, boolean right) {
        super(world, model, BodyDef.BodyType.DynamicBody);


        float density = 1000000f, friction = 0f, restitution = 0f;
        float width = PLATFORM_LENGTH, height = PLATFORM_HEIGHT;

        this.add_fixture(body, new float[]{0, 0, 0, height, width, 0, width, height}
                , width, height, density, friction, restitution, PLATFORM_MASK, PERSON_MASK, false
        );

        int elevator_sensor_width = ElevatorBody.width;
        int person_sensor_width = 1;
        if (right) {
            this.add_fixture(body, new float[]{width, 0, width, height, width + elevator_sensor_width, 0, width + elevator_sensor_width, height}
                    , width, height, density, friction, restitution, PLATFORM_ELEVATOR_SENSOR, (short)(ELEVATOR_MASK | PERSON_MASK), true
            );

            this.add_fixture(body, new float[]{width - person_sensor_width, 0, width - person_sensor_width, -PersonBody.HEIGHT, width, 0, width, -PersonBody.HEIGHT}
                    , width, height, density, friction, restitution, PLATFORM_END_SENSOR, PERSON_SENSOR_MASK, true
            );

        } else {
            this.add_fixture(body, new float[]{-elevator_sensor_width, 0, -elevator_sensor_width, height, 0, 0, 0, height}
                    , width, height, density, friction, restitution, PLATFORM_ELEVATOR_SENSOR, (short)(ELEVATOR_MASK | PERSON_MASK), true
            );

            this.add_fixture(body, new float[]{0, 0, 0, -PersonBody.HEIGHT, person_sensor_width, 0, person_sensor_width, -PersonBody.HEIGHT}
                    , width, height, density, friction, restitution, PLATFORM_END_SENSOR, PERSON_SENSOR_MASK, true
            );


        }
        this.body.setGravityScale(0);
    }

}
