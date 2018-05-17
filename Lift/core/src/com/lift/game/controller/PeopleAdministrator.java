package com.lift.game.controller;

import com.lift.game.controller.entities.PlatformBody;

import java.util.ArrayList;

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
        floors = gameController.getRight_floors();
    }
}