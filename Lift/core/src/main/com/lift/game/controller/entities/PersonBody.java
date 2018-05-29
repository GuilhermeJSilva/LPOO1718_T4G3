package com.lift.game.controller.entities;

import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.World;
import com.lift.game.controller.utils.PhysicalVariables;
import com.lift.game.model.entities.person.PersonModel;

import static com.lift.game.controller.entities.PlatformBody.*;

public class PersonBody extends EntityBody {

    public static final int WIDTH = 3;
    public static final int HEIGHT = 5;
    public static final short PERSON_MASK = 1 << 2;
    public static final short PERSON_SENSOR_MASK = 1 << 5;

    /**
     * Creates an Person body.
     *
     * @param model Person model.
     */
    public PersonBody(World world, PersonModel model) {
        super(world, model, BodyDef.BodyType.DynamicBody);
        PhysicalVariables phys = new PhysicalVariables(WIDTH, HEIGHT, 0.01f, 0f, 0f);

        this.add_fixture(body, new float[]{0, 0, 0, HEIGHT, WIDTH, 0, WIDTH, HEIGHT}
                , phys, PERSON_MASK, (short) (PLATFORM_MASK | PLATFORM_ELEVATOR_SENSOR), false);

        float sensor_width = 0.25f;

        this.add_fixture(body, new float[]{0, 0, 0, HEIGHT, -sensor_width, 0, -sensor_width, HEIGHT}
                , phys, PERSON_SENSOR_MASK, (short) (PLATFORM_END_SENSOR | PERSON_SENSOR_MASK), true);

        this.add_fixture(body, new float[]{WIDTH, 0, WIDTH, HEIGHT, WIDTH + sensor_width, 0, WIDTH + sensor_width, HEIGHT}
                , phys, PERSON_SENSOR_MASK, (short) (PLATFORM_END_SENSOR | PERSON_SENSOR_MASK), true);

        this.body.setGravityScale(5);
    }

}
