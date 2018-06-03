package com.lift.game.controller.entities;

import com.badlogic.gdx.physics.box2d.*;
import com.lift.game.controller.utils.CollisionVariables;
import com.lift.game.controller.utils.PhysicalVariables;
import com.lift.game.model.entities.EntityModel;
import com.lift.game.model.Side;

/**
 * Basic entity body.
 */
public class EntityBody {

    /**
     * Physical body.
     */
    final Body body;

    /**
     * Side of the screen the object is on.
     */
    private Side side;

    /**
     * Creates an entity Body.
     *  @param world World the body is going to be inserted in.
     * @param model Entity model.
     */
    protected EntityBody(World world, EntityModel model) {
        super();
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(model.getX(), model.getY());
        bodyDef.angle = 0;

        body = world.createBody(bodyDef);
        body.setUserData(model);

        this.side = model.getSide();
    }


    /**
     * Creates an entity Body.
     *  @param world World the body is going to be inserted in.
     *
     *
     * @param x X position of the body.
     * @param y Y position of the body.
     */
    protected EntityBody(World world, float x, float y) {
        super();
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.StaticBody;
        bodyDef.position.set(x, y);
        bodyDef.angle = 0;

        body = world.createBody(bodyDef);

    }



    /**
     * Adds a fixture to a given body.
     *
     * @param body Body to add fixture to.
     * @param vertexes Vertexes of the fixture.
     * @param phys Physical attributes of the body.
     * @param collisionVariables Attributes that describe a collision.
     *
     */
    protected final void add_fixture(Body body, float[] vertexes, PhysicalVariables phys, CollisionVariables collisionVariables) {
        for (int i = 0; i < vertexes.length; i++) {
            if (i % 2 == 0) vertexes[i] -= phys.getWidth() / 2;
            if (i % 2 != 0) vertexes[i] -= phys.getHeight() / 2;

            if (i % 2 != 0) vertexes[i] *= -1;
        }

        PolygonShape polygon = new PolygonShape();
        polygon.set(vertexes);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = polygon;

        fixtureDef.density = phys.getDensity();
        fixtureDef.friction = phys.getFriction();
        fixtureDef.restitution = phys.getRestitution();
        fixtureDef.filter.categoryBits = collisionVariables.getCategory_bits();
        fixtureDef.filter.maskBits = collisionVariables.getMask();
        fixtureDef.isSensor = collisionVariables.isSensor();
        body.createFixture(fixtureDef);

        polygon.dispose();
    }

    /**
     * Adds a circular fixture a body.
     * @param body Body the fixture is going to be added to.
     * @param phys Physical attributes of the body.
     * @param collisionVariables Attributes that describe a collision.

     */
    protected final void addCircularFixture(Body body, PhysicalVariables phys, CollisionVariables collisionVariables) {
        CircleShape circleShape = new CircleShape();
        circleShape.setRadius(phys.getRadius());

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = circleShape;
        fixtureDef.density = phys.getDensity();
        fixtureDef.friction = phys.getFriction();
        fixtureDef.restitution = phys.getRestitution();
        fixtureDef.filter.categoryBits = collisionVariables.getCategory_bits();
        fixtureDef.filter.maskBits = collisionVariables.getMask();
        fixtureDef.isSensor = collisionVariables.isSensor();

        body.createFixture(fixtureDef);
        circleShape.dispose();

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

    /**
     * Returns the body.
     * @return Body.
     */
    public Body getBody() {
        return body;
    }

    /**
     * Returns the side of the screen.
     * @return Side of the screen.
     */
    public Side getSide() {
        return side;
    }
}
