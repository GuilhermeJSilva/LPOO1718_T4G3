package com.lift.game.controller.entities.pstrategies;

import com.badlogic.gdx.physics.box2d.Body;

public class NullStrategy implements MovementStrategy {
    private static NullStrategy ourInstance = new NullStrategy();

    public static NullStrategy getInstance() {
        return ourInstance;
    }

    private NullStrategy() {
    }

    @Override
    public int getPriority() {
        return 0;
    }

    @Override
    public void initialMovement(Body body, boolean b) {

    }

    @Override
    public void collisionPersonPersonInPlatform(Body bodyA, Body bodyB) {

    }

    @Override
    public void solvePersonPlatformCollision(Body personBody, Body platformBody, int platformFixture) {

    }

    @Override
    public float getGravityScale() {
        return 1;
    }
}
