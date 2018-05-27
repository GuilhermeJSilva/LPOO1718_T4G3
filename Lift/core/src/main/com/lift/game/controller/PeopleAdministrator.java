package main.com.lift.game.controller;

import java.util.LinkedList;
import java.util.List;

import com.badlogic.gdx.physics.box2d.Body;

import main.com.lift.game.controller.entities.ElevatorBody;
import main.com.lift.game.controller.entities.PersonBody;
import main.com.lift.game.controller.entities.pstrategies.StrategySelector;
import main.com.lift.game.model.entities.ElevatorModel;
import main.com.lift.game.model.entities.person.PersonModel;
import main.com.lift.game.model.entities.person.PersonState;
import main.com.lift.game.model.entities.person.Side;

/**
 * Changes the people states and physical attributes.
 */
public class PeopleAdministrator {
    /**
     * Maximum difficulty factor.
     */
    public static final int MAX_DF = 2;

    /**
     * Difficulty factor increment.
     */
    public static final double DF_DELTA = 0.1;

    /**
     * Owner of the administrator.
     */
    private final GameController gameController;

    /**
     * People that have reached their destination.
     */
    private List<PersonBody> reachedPeople;

    /**
     * Difficulty factor.
     */
    private float difficultyFactor = 1;

    /**
     * Constructs the people administrator.
     * @param gameController Owner
     */
    PeopleAdministrator(GameController gameController) {
        this.gameController = gameController;
        reachedPeople = new LinkedList<PersonBody>();

    }

    /**
     * Moves people from and to the elevator and eliminates those who have left the screen.
     */
    public void movePeople() {
        movePeopleOut();

        for (PersonBody personBody : gameController.getPeople()) {
            Body body = personBody.getBody();
            PersonModel personModel = ((PersonModel) body.getUserData());

            if (personModel != null) {
                if (personModel.isTryingToEnter() && personModel.getPersonState() != PersonState.InElevator) {
                    enterTheElevator(body, personModel);
                }

                if (personModel.getPersonState() == PersonState.FreeFalling || personModel.getPersonState() == PersonState.Reached) {
                    if(body.getPosition().x < 0 || body.getPosition().x > 45 || body.getPosition().y < 0) {
                        if (personModel.getPersonState() == PersonState.FreeFalling && !personModel.isFlaggedForRemoval())
                            gameController.getGameModel().decrementLives();
                        personModel.setFlaggedForRemoval(true);
                    }
                }
            }
        }
    }

    /**
     * Moves people out of the elevator.
     */
    public void movePeopleOut() {
        for(PersonBody personBody : reachedPeople) {
            PersonModel personModel = ((PersonModel) personBody.getBody().getUserData());
            updatePositionWhenReached(personModel.getSide(), personBody);
            gameController.getGameModel().incrementTime(gameController.getStrategySelector().getStrategy(personModel).getTimeIncrease());
        }
        reachedPeople.clear();
    }

    /**
     * Updates a person's position when they reached their destination.
     * @param side Side the movement is going to occur.
     * @param personBody Body to be updated.
     */
    public void updatePositionWhenReached(Side side, PersonBody personBody) {
        ElevatorBody elevatorBody = gameController.getElevator(side);
        personBody.getBody().setTransform(elevatorBody.getX(),elevatorBody.getY(),0);
        personBody.getBody().setLinearVelocity(side == Side.Left ? -10 :10 , 2);
    }

    /**
     * Check if a person can enter the elevator, if it can moves the person into it.
     * @param personBody Body to be moved.
     * @param personModel Model of the body.
     */
    public void enterTheElevator(Body personBody, PersonModel personModel) {
        ElevatorModel elevator = gameController.getGameModel().getElevator(personModel.getSide());
        if (elevator.getTarget_floor() == personModel.getFloor() && elevator.getStopped() && elevator.isThereSpaceFree()) {
            personModel.setPersonState(PersonState.InElevator);
            freeSpaceInPlatform(personModel);
            elevator.incrementOccupancy();
            personBody.setTransform(20,90,0);
            personBody.setLinearVelocity(0,0);
            personBody.setGravityScale(0);

        }
        personModel.setTryingToEnter(false);
    }

    /**
     * Makes a person move into the free falling.
     * @param personModel Model to be updated.
     */
    public void moveToFreeFly(PersonModel personModel) {
        if (personModel.getPersonState() == PersonState.Waiting || personModel.getPersonState() == PersonState.GiveUP) {
            personModel.setPersonState(PersonState.FreeFalling);
            freeSpaceInPlatform(personModel);
        }
    }

    /**
     * Frees space in a platform-
     * @param personModel Free the space previously owned by this model-
     */
    public void freeSpaceInPlatform(PersonModel personModel) {
        if (personModel.getSide() == Side.Left) {
            gameController.getGameModel().getLeft_floors().get(personModel.getFloor()).decrementNPeople();
        } else {
            gameController.getGameModel().getRight_floors().get(personModel.getFloor()).decrementNPeople();
        }
    }

    /**
     * Updates all the people.
     * @param strategySelector Strategy selector for the people.
     * @param delta Time since the last update.
     */
    public void updatePeople(StrategySelector strategySelector, float delta) {
        for (PersonBody personBody : gameController.getPeople()) {
            PersonModel per = (PersonModel) personBody.getBody().getUserData();
            if (per != null) {
                float real_delta = strategySelector.getStrategy(per).getSatisfactionDelta(delta) * difficultyFactor;
                if (per.update(real_delta) && per.getPersonState() != PersonState.GiveUP) {
                    strategySelector.getStrategy(per).giveUp(personBody, per.getSide());
                }
            }
        }
    }

    /**
     * Delivers the people in the elevator to a given floor.
     * @param target_floor Floor the elevator arrived in.
     * @param side Side of the elevator.
     */
    public void deliverPeople(int target_floor, Side side) {
        for (PersonBody personBody : gameController.getPeople()) {
            Body body = personBody.getBody();
            PersonModel personModel = ((PersonModel) body.getUserData());

            if (personModel != null) {
                if (personModel.getPersonState() == PersonState.InElevator && personModel.getDestination() ==target_floor && personModel.getSide() == side) {
                    personBody.getBody().setGravityScale(5);
                    reachedPeople.add(personBody);
                    personModel.setPersonState(PersonState.Reached);
                    gameController.getGameModel().getElevator(side).decrementOccupancy();
                }
            }
        }
    }


    /**
     * Increases the difficulty of the game.
     */
    public void increaseDifficulty() {
        if(Math.round(difficultyFactor* 10) /10f < MAX_DF) {
            difficultyFactor += DF_DELTA;
        }
    }

    /**
     * Returns the people that have reached their destination.
     * @return People that have reached their destination.
     */
    public List<PersonBody> getReachedPeople() {
        return reachedPeople;
    }

    /**
     * Returns the difficulty factor.
     * @return Difficulty factor.
     */
    public float getDifficultyFactor() {
        return difficultyFactor;
    }
}