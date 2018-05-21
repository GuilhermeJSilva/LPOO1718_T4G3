package com.lift.game.controller.entities;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.lift.game.model.entities.EntityModel;

public class EntityBody {

    final Body body;

    /**
     * Creates an entity Body.
     *
     * @param model Entity model.
     */
    EntityBody(World world, EntityModel model) {
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
     *
     * @param body Body to add fixture to.
     * @param vertexes Vertexes of the fixture.
     * @param width Width of the fixture.
     * @param height Height of the fixture.
     * @param density Density of the fixture.
     * @param friction Friction of the fixture.
     * @param restitution Restitution of the fixture.
     * @param category Category bits of the fixture.
     * @param mask Collision mask of the fixture.
     * @param sensor True if the fixture is a sensor.
     */
    final void add_fixture(Body body, float[] vertexes, float width, float height, float density, float friction, float restitution, short category, short mask, boolean sensor) {
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
     *
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
