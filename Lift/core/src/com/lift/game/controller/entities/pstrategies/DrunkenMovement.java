package com.lift.game.controller.entities.pstrategies;

import com.badlogic.gdx.physics.box2d.Body;

import static com.lift.game.controller.entities.PlatformBody.PLATFORM_END_SENSOR;

public class DrunkenMovement implements MovementStrategy {
    private static DrunkenMovement ourInstance = new DrunkenMovement();

    public static DrunkenMovement getInstance() {
        return ourInstance;
    }

    private DrunkenMovement() {
    }

    @Override
    public void collisionPersonPersonInPlatform(Body bodyA, Body bodyB) {

    }

    @Override
    public void solvePersonPlatformCollision(Body personBody, Body platformBody, int platformFixture) {
        if (platformFixture != PLATFORM_END_SENSOR) {
            personBody.setGravityScale(0);
            personBody.setLinearVelocity(personBody.getLinearVelocity().x, 0f);
        }
    }
}
