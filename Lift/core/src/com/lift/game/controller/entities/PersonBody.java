package com.lift.game.controller.entities;

import com.badlogic.gdx.physics.box2d.World;
import com.lift.game.model.entities.person.PersonModel;

public class PersonBody extends EntityBody {

    public static final int WIDTH = 5;
    public static final int HEIGHT = 5;
    public static final short PERSON_MASK = 1 << 2;

    /**
	 * Creates an Person body.
	 * @param model Person model.
	 */
	public PersonBody(World world, PersonModel model) {
		super(world, model);
		
		float density = 1f, friction = 0.5f, restitution = 0f;
		int width = WIDTH;

        this.add_fixture(body, new float[] {0, 0, 0, HEIGHT, width, 0, width, HEIGHT}
		, width, HEIGHT, density, friction, restitution, PERSON_MASK , (short)0, false);
	}

}
