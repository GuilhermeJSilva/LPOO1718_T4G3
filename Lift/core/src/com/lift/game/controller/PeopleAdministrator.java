package com.lift.game.controller;

import com.lift.game.controller.entities.PlatformBody;

import java.util.ArrayList;

public class PeopleAdministrator {
    private final GameController gameController;

    public PeopleAdministrator(GameController gameController) {
        this.gameController = gameController;
    }

    void movePeople() {
        ArrayList<PlatformBody> floors = gameController.getLeft_floors();
        floors = gameController.getRight_floors();
    }
}