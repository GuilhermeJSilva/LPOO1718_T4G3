package com.lift.game.controller.entities;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.lift.game.model.entities.EntityModel;

import static com.lift.game.view.GameView.PIXEL_TO_METER;

public class EntityBody {

	final Body body;
	/**
	 * Creates an entity Body.
	 * 
	 * @param model
	 *            Entity model.
	 */
	public EntityBody(World world, EntityModel model) {
		super();
		BodyDef bodyDef = new BodyDef();
		bodyDef.type = BodyDef.BodyType.DynamicBody;
		bodyDef.position.set(model.getX(), model.getY());
		bodyDef.angle = 0;

		body = world.createBody(bodyDef);
		body.setUserData(model);
	}
	
	/**
	 * Adds a fixture to a given body.
     * @param body
     * @param sensor
     */
	protected final void add_fixture(Body body, float[] vertexes, float width, float height, float density, float friction, float restitution, short category, short mask, boolean sensor) {
        for (int i = 0; i < vertexes.length; i++) {
            if (i % 2 == 0) vertexes[i] -= width / 2;
            if (i % 2 != 0) vertexes[i] -= height / 2;

            if (i % 2 != 0) vertexes[i] *= -1;

            //vertexes[i] *= 1;
        }

        PolygonShape polygon = new PolygonShape();
        polygon.set(vertexes);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = polygon;

        fixtureDef.density = density;
        fixtureDef.friction = friction;
        fixtureDef.restitution = restitution;
        fixtureDef.filter.categoryBits = category;
        fixtureDef.filter.maskBits = mask;
        fixtureDef.isSensor = sensor;
        body.createFixture(fixtureDef);

        polygon.dispose();
    }

	
	/**
	 * Returns the y position of the body.
	 * 
	 * @return Y position of the body.
	 */
    public float getX() {
        return body.getPosition().x;
    }

    /**
	 * Returns the x position of the body.
	 * 
	 * @return X position of the body.
	 */
    public float getY() {
        return body.getPosition().y;
    }


    /**
     * Changes the linear velocity of a body.
     * @param vx Velocity in the x axis.
     * @param vy Velocity in the y axis.
     */
    public void setLinearVelocity(float vx, float vy) {
    	this.body.setLinearVelocity(vx, vy);
    }

    public Body getBody() {
        return body;
    }
}
