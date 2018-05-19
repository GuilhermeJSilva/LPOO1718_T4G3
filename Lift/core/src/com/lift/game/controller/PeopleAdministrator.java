package com.lift.game.controller;

import com.lift.game.controller.entities.PersonBody;
import com.lift.game.controller.entities.PlatformBody;
import com.lift.game.model.GameModel;
import com.lift.game.model.entities.ElevatorModel;
import com.lift.game.model.entities.PlatformModel;
import com.lift.game.model.entities.person.PersonModel;
import com.lift.game.model.entities.person.PersonState;
import com.lift.game.model.entities.person.Side;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

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

    //TODO Implement
    protected void movePeople() {
        for (PersonModel personModel:
             GameModel.getInstance().getPeople()) {
            if(personModel.getPersonState() == PersonState.TryToEnter) {
                ElevatorModel elevator = GameModel.getInstance().getElevator(personModel.getSide());
                if(elevator.getTarget_floor() == personModel.getFloor() && elevator.getStopped()) {
                    personModel.setPersonState(PersonState.InElevator);
                }
            }
        }
    }

    public void moveToFreeFly(PersonModel personModel) {
        if(personModel.getPersonState() == PersonState.Waiting || personModel.getPersonState() == PersonState.GiveUP) {
            personModel.setPersonState(PersonState.FreeFlying);
            if (personModel.getSide() == Side.Left) {
                GameModel.getInstance().getLeft_floors().get(personModel.getFloor()).decrementNPeople();
            } else {
                GameModel.getInstance().getRight_floors().get(personModel.getFloor()).decrementNPeople();
            }
        }
    }

}