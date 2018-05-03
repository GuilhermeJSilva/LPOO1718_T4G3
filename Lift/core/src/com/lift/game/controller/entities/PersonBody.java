package com.lift.game.controller.entities;

import com.badlogic.gdx.physics.box2d.World;
import com.lift.game.model.entities.PersonModel;

public class PersonBody extends EntityBody {

	/**
	 * Creates an Person body.
	 * @param model Person model.
	 */
	public PersonBody(World world, PersonModel model) {
		super(world, model);
		
		float density = 1f, friction = 0.5f, restitution = 0f;
		int width = 5, height = 5;
		
		this.add_fixture(body, new float[] {0, 0, 0, height, width, 0, width, height}
		, width, height, density, friction, restitution, (short)0, (short)0, true);
	}

}
