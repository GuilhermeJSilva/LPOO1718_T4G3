package com.lift.game.controller.entities.pstrategies;

import com.badlogic.gdx.physics.box2d.Body;
import com.lift.game.controller.GameController;
import com.lift.game.controller.entities.PersonBody;
import com.lift.game.model.entities.person.PersonModel;
import com.lift.game.model.entities.person.Side;

import static com.lift.game.controller.entities.PlatformBody.PLATFORM_ELEVATOR_SENSOR;

public  class NullStrategy implements MovementStrategy {

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
        PersonModel personModel = (PersonModel) personBody.getUserData();
        if(platformFixture == PLATFORM_ELEVATOR_SENSOR) {
            GameController.getInstance().getPeopleAdministrator().moveToFreeFly(personModel);

        }

    }

    @Override
    public void giveUp(PersonBody personBody, Side side) {

    }

    @Override
    public void collisionEndPersonPersonInPlatform(Body person1, Body person2, Side side) {

    }
}
