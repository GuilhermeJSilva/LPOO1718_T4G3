package com.lift.game.controller.entities.pstrategies;

import com.badlogic.gdx.physics.box2d.Body;
import com.lift.game.controller.entities.PersonBody;

public class NullStrategy implements MovementStrategy {

    public NullStrategy() {
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

    @Override
    public void giveUp(PersonBody personBody, char side) {

    }

    @Override
    public void collisionEndPersonPersonInPlatform(Body person1, Body person2, char side) {

    }
}
