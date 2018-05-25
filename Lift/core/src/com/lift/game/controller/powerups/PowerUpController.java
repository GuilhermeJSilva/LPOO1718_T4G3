package com.lift.game.controller.powerups;

import com.lift.game.controller.GameController;

import java.util.LinkedList;

/**
 * Controls all power ups present in the game.
 */
public class PowerUpController {
    /**
     * List of waiting power ups.
     */
    private LinkedList<PowerUp> powerUps;

    /**
     * Owner of the controller.
     */
    private GameController gameController;

    /**
     * Time accumulator for the generation of power ups.
     */
    private Float time_accumulator;

    /**
     * Constructs the power up controller.
     *
     * @param gameController Owner of the controller.
     */
    public PowerUpController(GameController gameController) {
        this.powerUps = new LinkedList<PowerUp>();
        this.gameController = gameController;
    }
}
