package com.lift.game.controller.entities.pstrategies;

import com.badlogic.gdx.physics.box2d.Body;
import com.lift.game.controller.entities.PersonBody;
import com.lift.game.model.entities.person.Side;

public class NullStrategy implements MovementStrategy {

    public NullStrategy() {
    }

    @Override
    public int getPriority() {
        return 0;
    }

    @Override
    public void initialMovement(Body body, Side side) {

    }

    @Override
    public void collisionPersonPersonInPlatform(Body bodyA, Body bodyB) {

    }

    @Override
    public void solvePersonPlatformCollision(Body personBody, Body platformBody, int platformFixture) {

    }

    @Override
    public void giveUp(PersonBody personBody, Side side) {

    }

    @Override
    public void collisionEndPersonPersonInPlatform(Body person1, Body person2, Side side) {

    }
}
