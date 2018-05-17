package com.lift.game.controller;

import com.lift.game.controller.entities.PersonBody;
import com.lift.game.controller.entities.PlatformBody;
import com.lift.game.model.GameModel;
import com.lift.game.model.entities.PlatformModel;
import com.lift.game.model.entities.person.PersonModel;
import com.lift.game.model.entities.person.PersonState;

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
        ArrayList<PlatformBody> floors = gameController.getLeft_floors();
        changeBodies(floors);
        floors = gameController.getRight_floors();
        changeBodies(floors);

        ArrayList<PlatformModel> model_floors = GameModel.getInstance().getLeft_floors();
        changeModels(model_floors);

        model_floors = GameModel.getInstance().getRight_floors();
        changeModels(model_floors);
    }

    private void changeBodies(ArrayList<PlatformBody> floors) {
        for (PlatformBody platformBody : floors) {
            List<PersonBody> waiting_people = platformBody.getWaiting_people();
            for (ListIterator<PersonBody> iter = waiting_people.listIterator(); iter.hasNext(); ) {
                PersonBody element = iter.next();
                if(element.getBody().getUserData() instanceof PersonModel) {
                    PersonModel personModel = (PersonModel) element.getBody().getUserData();
                    if(personModel.getPersonState() == PersonState.FreeFlying){
                        GameController.getInstance().addFreeFlyer(element);
                        iter.remove();
                    }
                }
            }
        }
    }


    private void changeModels(ArrayList<PlatformModel> floors) {
        for (PlatformModel platformBody : floors) {
            List<PersonModel> waiting_people = platformBody.getWaiting_people();
            for (ListIterator<PersonModel> iter = waiting_people.listIterator(); iter.hasNext(); ) {
                PersonModel element = iter.next();

                    if(element.getPersonState() == PersonState.FreeFlying) {
                        GameModel.getInstance().addFreeFlyer(element);
                        iter.remove();
                    }
            }
        }
    }
}