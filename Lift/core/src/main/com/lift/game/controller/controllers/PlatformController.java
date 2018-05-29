package com.lift.game.controller.controllers;

import com.lift.game.controller.GameController;

/**
 * Controls the platform movement.
 */
public class PlatformController {

    /**
     * Controls the platforms in this controller.
     */
    private GameController gameController;

    /**
     * Creates the platform controller.
     * @param gameController Controller to be stored.
     */
    public PlatformController(GameController gameController) {
        this.gameController = gameController;
    }

}
