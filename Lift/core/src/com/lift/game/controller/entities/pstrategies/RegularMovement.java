package com.lift.game.controller.entities.pstrategies;

import com.badlogic.gdx.physics.box2d.Body;

import static com.lift.game.controller.entities.PlatformBody.PLATFORM_END_SENSOR;

public class RegularMovement implements MovementStrategy {
    private static final int INITIAL_V_Y = 10;

    private final Integer priority = 0;

    private static RegularMovement ourInstance = new RegularMovement();

    public static RegularMovement getInstance() {
        return ourInstance;
    }

    private RegularMovement() {
    }

    @Override
    public  int getPriority() {
        return priority;
    }

    @Override
    public void collisionPersonPersonInPlatform(Body bodyA, Body bodyB) {
        float x_velocity;
        if (bodyA.getLinearVelocity().x < 0 || bodyB.getLinearVelocity().x < 0)
            x_velocity = Math.max(bodyA.getLinearVelocity().x, bodyB.getLinearVelocity().x);
        else
            x_velocity = Math.min(bodyA.getLinearVelocity().x, bodyB.getLinearVelocity().x);
        bodyB.setLinearVelocity(x_velocity, 0);
        bodyA.setLinearVelocity(x_velocity, 0);
    }

    @Override
    public void solvePersonPlatformCollision(Body personBody, Body platformBody, int platformFixture) {
        if (platformFixture == PLATFORM_END_SENSOR) {
            personBody.setLinearVelocity(0, 0f);
        } else {
            personBody.setGravityScale(0);
            personBody.setLinearVelocity(personBody.getLinearVelocity().x, 0f);
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
