package com.lift.game.controller.entities.pstrategies;

import com.badlogic.gdx.physics.box2d.Body;

import static com.lift.game.controller.entities.PlatformBody.PLATFORM_END_SENSOR;

public class DrunkenMovement implements MovementStrategy {

    private static final int INITIAL_V_Y = 10;

    private final Integer priority = 1;

    private static DrunkenMovement ourInstance = new DrunkenMovement();

    public static DrunkenMovement getInstance() {
        return ourInstance;
    }

    private DrunkenMovement() {
    }

    @Override
    public int getPriority() {
        return priority;
    }

    @Override
    public void collisionPersonPersonInPlatform(Body bodyA, Body bodyB) {

    }

    @Override
    public void solvePersonPlatformCollision(Body personBody, Body platformBody, int platformFixture) {
        if (platformFixture != PLATFORM_END_SENSOR) {
            personBody.setGravityScale(15);
            //Comment to introduce jumping drunks
            personBody.setLinearVelocity(personBody.getLinearVelocity().x, 0);
        }
    }

    @Override
    public void initialMovement(Body body, boolean b) {
        if (b) {
            body.setLinearVelocity(INITIAL_V_Y, 0);
        } else {
            body.setLinearVelocity(-INITIAL_V_Y, 0);
        }
    }
}
