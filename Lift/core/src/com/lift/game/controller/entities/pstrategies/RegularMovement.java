package com.lift.game.controller.entities.pstrategies;

import com.badlogic.gdx.physics.box2d.Body;
import com.lift.game.controller.GameController;
import com.lift.game.controller.entities.PersonBody;
import com.lift.game.model.entities.person.PersonModel;
import com.lift.game.model.entities.person.PersonState;
import com.lift.game.model.entities.person.Side;

import static com.lift.game.controller.entities.PlatformBody.PLATFORM_END_SENSOR;

public class RegularMovement extends NullStrategy implements MovementStrategy {
    private static final int INITIAL_V = 10;
    private static final int GIVING_UP_V = 2;

    private final Integer priority = 0;


    public RegularMovement(GameController gameController) {
        super(gameController);
    }

    @Override
    public int getPriority() {
        return priority;
    }

    @Override
    public void collisionPersonPersonInPlatform(Body personBody1, Body personBody2) {
        PersonModel personModel1 = (PersonModel) personBody1.getUserData();
        PersonModel personModel2 = (PersonModel) personBody2.getUserData();


        if (personModel1.getPersonState() == PersonState.Waiting  && personModel2.getPersonState() != PersonState.Reached) {
            personBody1.setLinearVelocity(0, personBody1.getLinearVelocity().y);
            personModel1.setPersonState(PersonState.StoppedWaiting);
        }
        if (personModel2.getPersonState() == PersonState.Waiting && personModel1.getPersonState() != PersonState.Reached) {
            personBody2.setLinearVelocity(0, personBody2.getLinearVelocity().y);
            personModel2.setPersonState(PersonState.StoppedWaiting);
        }

    }

    @Override
    public void solvePersonPlatformCollision(Body personBody, Body platformBody, int platformFixture) {
        super.solvePersonPlatformCollision(personBody, platformBody, platformFixture);
        PersonModel personModel = (PersonModel) personBody.getUserData();

        if (personModel.getPersonState() != PersonState.Reached) {
            if (platformFixture == PLATFORM_END_SENSOR && personModel.getPersonState() == PersonState.Waiting) {
                personBody.setLinearVelocity(0, 0f);
                personModel.setPersonState(PersonState.StoppedWaiting);
            }

            if (personModel.getPersonState() == PersonState.StoppedWaiting)
                personBody.setLinearVelocity(0, 0f);
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

    @Override
    public void giveUp(PersonBody personBody, Side side) {
        super.giveUp(personBody,side);
        PersonModel personModel = (PersonModel) personBody.getBody().getUserData();
        if (personModel.getPersonState() != PersonState.Reached) {
            if(personModel.getPersonState() !=  PersonState.GiveUP && personModel.getPersonState() !=  PersonState.FreeFlying && personModel.getPersonState() !=  PersonState.InElevator) {
                if (side == Side.Left) {
                    personBody.setLinearVelocity(GIVING_UP_V, personBody.getBody().linVelLoc.y);
                } else {
                    personBody.setLinearVelocity(-GIVING_UP_V, personBody.getBody().linVelLoc.y);
                }

                personModel.setPersonState(PersonState.GiveUP);
            }
        }
    }

    @Override
    public void collisionEndPersonPersonInPlatform(Body person1, Body person2, Side side) {
        float x_velocity = side == Side.Left ? GIVING_UP_V : -GIVING_UP_V;
        PersonModel personModel1 = (PersonModel) person1.getUserData();
        PersonModel personModel2 = (PersonModel) person2.getUserData();

        if (personModel1.getPersonState() != PersonState.Reached && personModel2.getPersonState() != PersonState.Reached) {
            if (personModel1.getPersonState() == PersonState.StoppedWaiting && personModel2.getPlat_position() < personModel1.getPlat_position()) {
                personModel1.setPersonState(PersonState.Waiting);
                person1.setLinearVelocity(x_velocity, person1.getLinearVelocity().y);
            }

            if (personModel2.getPersonState() == PersonState.StoppedWaiting && personModel1.getPlat_position() < personModel2.getPlat_position()) {
                personModel2.setPersonState(PersonState.Waiting);
                person2.setLinearVelocity(x_velocity, person2.getLinearVelocity().y);
            }
        }
    }

    @Override
    public float getTimeIncrease() {
        return 3f;
    }
}
