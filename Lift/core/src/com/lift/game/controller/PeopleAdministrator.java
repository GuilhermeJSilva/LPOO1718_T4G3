package com.lift.game.controller;

import com.badlogic.gdx.physics.box2d.Body;
import com.lift.game.controller.entities.ElevatorBody;
import com.lift.game.controller.entities.PersonBody;
import com.lift.game.controller.entities.pstrategies.StrategySelector;
import com.lift.game.model.GameModel;
import com.lift.game.model.entities.ElevatorModel;
import com.lift.game.model.entities.person.PersonModel;
import com.lift.game.model.entities.person.PersonState;
import com.lift.game.model.entities.person.Side;

import java.util.LinkedList;
import java.util.List;

public class PeopleAdministrator {
    private final GameController gameController;

    private List<PersonBody> reachedPeople;

    PeopleAdministrator(GameController gameController) {
        this.gameController = gameController;
        reachedPeople = new LinkedList<PersonBody>();

    }

    void movePeople() {
        for(PersonBody personBody : reachedPeople) {
            PersonModel personModel = ((PersonModel) personBody.getBody().getUserData());
            updatePositionWhenReached(personModel.getSide(), personBody);
        }
        reachedPeople.clear();

        for (PersonBody personBody : gameController.getPeople()) {
            Body body = personBody.getBody();
            PersonModel personModel = ((PersonModel) body.getUserData());

            if (personModel.isTryingToEnter() && personModel.getPersonState() != PersonState.InElevator) {
                enterTheElevator(body, personModel);
            }
        }
    }

    private void enterTheElevator(Body personBody, PersonModel personModel) {
        ElevatorModel elevator = GameModel.getInstance().getElevator(personModel.getSide());
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

    public void moveToFreeFly(PersonModel personModel) {
        if (personModel.getPersonState() == PersonState.Waiting || personModel.getPersonState() == PersonState.GiveUP) {
            personModel.setPersonState(PersonState.FreeFlying);
            freeSpaceInPlatform(personModel);
        }
    }

    private void freeSpaceInPlatform(PersonModel personModel) {
        if (personModel.getSide() == Side.Left) {
            GameModel.getInstance().getLeft_floors().get(personModel.getFloor()).decrementNPeople();
        } else {
            GameModel.getInstance().getRight_floors().get(personModel.getFloor()).decrementNPeople();
        }
    }

    void updatePeople(StrategySelector strategySelector, float delta) {
        for (PersonBody personBody : gameController.getPeople()) {
            PersonModel per = (PersonModel) personBody.getBody().getUserData();
            float real_delta = strategySelector.getStrategy(per).getSatisfactionDelta(delta);
            if (per.update(real_delta) && per.getPersonState() != PersonState.GiveUP) {
                strategySelector.getStrategy(per).giveUp(personBody, per.getSide());
            }
        }
    }

    void deliverPeople(int target_floor, Side side) {
        for (PersonBody personBody : gameController.getPeople()) {
            Body body = personBody.getBody();
            PersonModel personModel = ((PersonModel) body.getUserData());

            if (personModel.getPersonState() == PersonState.InElevator && personModel.getDestination() ==target_floor && personModel.getSide() == side) {
                personBody.getBody().setGravityScale(5);
                reachedPeople.add(personBody);
                personModel.setPersonState(PersonState.Reached);
                GameModel.getInstance().getElevator(side).decrementOccupancy();
            }
        }
    }

    private void updatePositionWhenReached(Side side, PersonBody personBody) {
        ElevatorBody elevatorBody = GameController.getInstance().getElevator(side);
        personBody.getBody().setTransform(elevatorBody.getX(),elevatorBody.getY(),0);
        personBody.getBody().setLinearVelocity(side == Side.Left ? -10 :10 , 10);
        personBody.getBody().applyAngularImpulse(side == Side.Left ? -2 :2, true);
    }
}