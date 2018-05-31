package com.lift.game.controller.entities.pstrategies;

import com.badlogic.gdx.physics.box2d.Body;
import com.lift.game.controller.GameController;
import com.lift.game.controller.entities.ElevatorBody;
import com.lift.game.controller.entities.PersonBody;
import com.lift.game.model.entities.person.PersonModel;
import com.lift.game.model.entities.person.PersonState;
import com.lift.game.model.Side;

import static com.lift.game.controller.entities.PlatformBody.PLATFORM_ELEVATOR_SENSOR;

/**
 * Null object for strategies.
 */
public class NullStrategy implements MovementStrategy {

    /**
     * Controller to act on.
     */
    private final GameController gameController;

    /**
     * Constructs the new strategy.
     * @param gameController Controller to act on.
     */
    public NullStrategy(GameController gameController) {
        this.gameController =  gameController;
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
        if (personModel.getPersonState() != PersonState.Reached) {
            if (personModel.getPersonState() != PersonState.InElevator) {
                if (platformFixture == PLATFORM_ELEVATOR_SENSOR && personModel.getPersonState() != PersonState.FreeFalling) {
                    gameController.getPeopleAdministrator().moveToFreeFly(personModel);

                }
            }
        }

    }

    @Override
    public void giveUp(PersonBody personBody, Side side) {
        PersonModel personModel = (PersonModel) personBody.getBody().getUserData();
        if (personModel.getPersonState() != PersonState.Reached) {
            if(personModel.getPersonState() == PersonState.InElevator) {
                personBody.getBody().setGravityScale(5);
                ElevatorBody elevatorBody = gameController.getElevator(side);
                personBody.getBody().setTransform(elevatorBody.getX(),elevatorBody.getY(),0);
                personModel.setPersonState(PersonState.FreeFalling);
                gameController.getGameModel().getElevator(side).decrementOccupancy();
            }
        }

    }

    @Override
    public void collisionEndPersonPersonInPlatform(Body person1, Body person2, Side side) {

    }

    @Override
    public float getSatisfactionDelta(float delta) {
        return delta;
    }

    @Override
    public float getTimeIncrease() {
        return 0;
    }
}
