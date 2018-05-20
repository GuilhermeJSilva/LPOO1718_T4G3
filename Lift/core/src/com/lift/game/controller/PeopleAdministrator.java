package com.lift.game.controller;

import com.badlogic.gdx.physics.box2d.Body;
import com.lift.game.controller.entities.PersonBody;
import com.lift.game.controller.entities.pstrategies.StrategySelector;
import com.lift.game.model.GameModel;
import com.lift.game.model.entities.ElevatorModel;
import com.lift.game.model.entities.person.PersonModel;
import com.lift.game.model.entities.person.PersonState;
import com.lift.game.model.entities.person.Side;

public class PeopleAdministrator {
    private final GameController gameController;

    PeopleAdministrator(GameController gameController) {
        this.gameController = gameController;
    }

    public void run() {
        this.movePeople();
        this.erasePeople();
    }

    //TODO Implement
    protected void erasePeople() {

    }

    private void movePeople() {
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

    public  void updatePeople(StrategySelector strategySelector, float delta) {
        for (PersonBody personBody : gameController.getPeople()) {
            PersonModel per = (PersonModel) personBody.getBody().getUserData();
            float real_delta = strategySelector.getStrategy(per).getSatisfactionDelta(delta);
            if (per.update(real_delta) && per.getPersonState() != PersonState.GiveUP) {
                strategySelector.getStrategy(per).giveUp(personBody, per.getSide());
            }
        }
    }

}