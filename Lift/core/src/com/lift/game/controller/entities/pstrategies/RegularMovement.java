package com.lift.game.controller.entities.pstrategies;

import com.badlogic.gdx.physics.box2d.Body;
import com.lift.game.controller.entities.PersonBody;
import com.lift.game.model.entities.person.PersonModel;
import com.lift.game.model.entities.person.PersonState;
import com.lift.game.model.entities.person.Side;

import static com.lift.game.controller.entities.PlatformBody.PLATFORM_END_SENSOR;

public class RegularMovement extends NullStrategy implements MovementStrategy {
    private static final int INITIAL_V = 10;
    public static final int GIVING_UP_V = 2;

    private final Integer priority = 0;


    public RegularMovement() {
    }

    @Override
    public int getPriority() {
        return priority;
    }

    @Override
    public void collisionPersonPersonInPlatform(Body bodyA, Body bodyB) {
        PersonModel personModel1 = (PersonModel) bodyA.getUserData();
        PersonModel personModel2 = (PersonModel) bodyB.getUserData();

        if (personModel1.getPersonState() != PersonState.InElevator && personModel2.getPersonState() != PersonState.InElevator) {
            if (personModel1.getPersonState() != PersonState.FreeFlying && personModel2.getPersonState() != PersonState.FreeFlying) {
                bodyA.setLinearVelocity(0, bodyA.getLinearVelocity().y);
                bodyB.setLinearVelocity(0, bodyB.getLinearVelocity().y);
                personModel1.setPersonState(PersonState.StoppedWaiting);
                personModel2.setPersonState(PersonState.StoppedWaiting);
            }
        }
    }

    @Override
    public void solvePersonPlatformCollision(Body personBody, Body platformBody, int platformFixture) {
        super.solvePersonPlatformCollision(personBody, platformBody, platformFixture);
        PersonModel personModel = (PersonModel) personBody.getUserData();

        if (platformFixture == PLATFORM_END_SENSOR && personModel.getPersonState() == PersonState.Waiting) {
            personBody.setLinearVelocity(0, 0f);
            personModel.setPersonState(PersonState.StoppedWaiting);
        }

        if (personModel.getPersonState() == PersonState.StoppedWaiting)
            personBody.setLinearVelocity(0, 0f);

    }

    @Override
    public void initialMovement(Body body, Side side) {
        if (side == Side.Left) {
            body.setLinearVelocity(INITIAL_V, 0);
        } else {
            body.setLinearVelocity(-INITIAL_V, 0);
        }
    }

    @Override
    public void giveUp(PersonBody personBody, Side side) {
        PersonModel personModel = (PersonModel) personBody.getBody().getUserData();
        if(personModel.getPersonState() !=  PersonState.GiveUP && personModel.getPersonState() !=  PersonState.FreeFlying) {
            if (side == Side.Left) {
                personBody.setLinearVelocity(GIVING_UP_V, 0);
            } else {
                personBody.setLinearVelocity(-GIVING_UP_V, 0);
            }

            personModel.setPersonState(PersonState.GiveUP);
        }
    }

    @Override
    public void collisionEndPersonPersonInPlatform(Body person1, Body person2, Side side) {
        float x_velocity = side == Side.Left ? GIVING_UP_V : -GIVING_UP_V;
        PersonModel personModel1 = (PersonModel) person1.getUserData();
        PersonModel personModel2 = (PersonModel) person2.getUserData();

        if (personModel1.getPersonState() == PersonState.StoppedWaiting) {
            personModel1.setPersonState(PersonState.Waiting);
            person1.setLinearVelocity(x_velocity, person1.getLinearVelocity().y);
        }

        if (personModel2.getPersonState() == PersonState.StoppedWaiting) {
            personModel2.setPersonState(PersonState.Waiting);
            person2.setLinearVelocity(x_velocity, person2.getLinearVelocity().y);
        }
    }
}
