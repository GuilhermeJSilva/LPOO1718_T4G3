package com.lift.game.controller.entities.pstrategies;

import com.badlogic.gdx.physics.box2d.Body;
import com.lift.game.model.entities.person.PersonModel;
import com.lift.game.model.entities.person.PersonState;

import static com.lift.game.controller.entities.PlatformBody.PLATFORM_END_SENSOR;

public class DrunkenMovement implements MovementStrategy {

    private static final int INITIAL_V_Y = 2;

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
        if(personBody.getAngle() != 0)
            ((PersonModel) personBody.getUserData()).setPersonState(PersonState.FreeFlying);

    }

    @Override
    public void initialMovement(Body body, boolean b) {
        if (b) {
            body.setLinearVelocity(INITIAL_V_Y, 0);
        } else {
            body.setLinearVelocity(-INITIAL_V_Y, 0);
        }
    }

    @Override
    public float getGravityScale() {
        return 5;
    }
}
