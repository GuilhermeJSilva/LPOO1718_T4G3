package com.lift.game.controller.entities.pstrategies;

import com.badlogic.gdx.physics.box2d.Body;
import com.lift.game.controller.entities.PersonBody;
import com.lift.game.model.GameModel;
import com.lift.game.model.entities.person.PersonModel;
import com.lift.game.model.entities.person.PersonState;

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
        if(personBody.getAngle() != 0) {
            PersonModel personModel = (PersonModel) personBody.getUserData();
            personModel.setPersonState(PersonState.FreeFlying);
            if(personModel.getSide() == 'L'){
                GameModel.getInstance().getLeft_floors().get(personModel.getFloor()).decrementNPeople();
            } else {
                GameModel.getInstance().getRight_floors().get(personModel.getFloor()).decrementNPeople();
            }

        }

    }

    @Override
    public void initialMovement(Body body, boolean b) {
        if (b) {
            body.setLinearVelocity(INITIAL_V, 0);
        } else {
            body.setLinearVelocity(-INITIAL_V, 0);
        }
    }
    
}
