package com.lift.game.controller.entities;

import com.badlogic.gdx.physics.box2d.World;
import com.lift.game.controller.GameController;
import com.lift.game.controller.entities.pstrategies.StrategySelector;
import com.lift.game.model.entities.person.PersonModel;

import static com.lift.game.controller.entities.PlatformBody.PLATFORM_ELEVATOR_SENSOR;
import static com.lift.game.controller.entities.PlatformBody.PLATFORM_END_SENSOR;
import static com.lift.game.controller.entities.PlatformBody.PLATFORM_MASK;

public class PersonBody extends EntityBody {

    public static final int WIDTH = 3;
    public static final int HEIGHT = 4;
    public static final short PERSON_MASK = 1 << 2;
    public static final short PERSON_SENSOR_MASK = 1 << 5;

    /**
	 * Creates an Person body.
	 * @param model Person model.
	 */
	public PersonBody(World world, PersonModel model) {
		super(world, model);
		
		float density = 0.01f, friction = 0f, restitution = 0f;
		int width = WIDTH;

        this.add_fixture(body, new float[] {0, 0, 0, HEIGHT, width, 0, width, HEIGHT}
		, width, HEIGHT, density, friction, restitution, PERSON_MASK , (short)(PLATFORM_MASK | PLATFORM_ELEVATOR_SENSOR), false);

        float sensor_width = 0.25f;

        this.add_fixture(body, new float[] {0, 0, 0, HEIGHT, -sensor_width, 0, -sensor_width, HEIGHT}
                , width, HEIGHT, density, friction, restitution, PERSON_SENSOR_MASK , (short)(PLATFORM_END_SENSOR | PERSON_SENSOR_MASK ), true);

        this.add_fixture(body, new float[] {width, 0, width, HEIGHT, width + sensor_width, 0, width + sensor_width, HEIGHT}
                , width, HEIGHT, density, friction, restitution, PERSON_SENSOR_MASK , (short)(PLATFORM_END_SENSOR | PERSON_SENSOR_MASK), true);

        this.body.setGravityScale(5);
	}

}
