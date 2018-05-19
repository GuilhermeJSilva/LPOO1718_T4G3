package com.lift.game.controller.entities.pstrategies;

import com.badlogic.gdx.physics.box2d.Body;
import com.lift.game.controller.entities.PersonBody;
import com.lift.game.model.GameModel;
import com.lift.game.model.entities.person.PersonModel;
import com.lift.game.model.entities.person.PersonState;
import com.lift.game.model.entities.person.Side;

import static com.lift.game.controller.entities.PlatformBody.PLATFORM_ELEVATOR_SENSOR;

public class DrunkenMovement extends NullStrategy implements MovementStrategy {

    private static final int INITIAL_V = 2;

    private final Integer priority = 1;

    public DrunkenMovement() {
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
        if(platformFixture == PLATFORM_ELEVATOR_SENSOR) {
            PersonModel personModel = (PersonModel) personBody.getUserData();
            if(personModel.getPersonState() == PersonState.Waiting) {
                personModel.setPersonState(PersonState.FreeFlying);
                if (personModel.getSide() == Side.Left) {
                    GameModel.getInstance().getLeft_floors().get(personModel.getFloor()).decrementNPeople();
                } else {
                    GameModel.getInstance().getRight_floors().get(personModel.getFloor()).decrementNPeople();
                }
            }

        }

    }

    @Override
    public void initialMovement(Body body, Side side) {
        if (side == Side.Left) {
            body.setLinearVelocity(INITIAL_V, 0);
        } else {
            body.setLinearVelocity(-INITIAL_V, 0);
        }
    }

}
