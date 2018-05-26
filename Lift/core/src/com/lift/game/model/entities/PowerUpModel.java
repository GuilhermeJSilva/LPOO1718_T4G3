package com.lift.game.model.entities;

import com.lift.game.controller.powerups.PowerUpState;
import com.lift.game.model.entities.person.Side;

/**
 * Models a power up.
 */
public class PowerUpModel extends EntityModel {

    /**
     * State of the power up.
     */
    private PowerUpState powerUpState;

    /**
     * Default entity constructor.
     *
     * @param x    position.
     * @param y    position.
     * @param side Side of the screen.
     */
    public PowerUpModel(float x, float y, Side side) {
        super(x, y, side);
        powerUpState = PowerUpState.Waiting;
    }

    /**
     * Returns the current state of the power up.
     * @return State of the power up.
     */
    public PowerUpState getPowerUpState() {
        return powerUpState;
    }

    /**
     * Changes the state of the power up.
     * @param powerUpState New state for the power up.
     */
    public void setPowerUpState(PowerUpState powerUpState) {
        this.powerUpState = powerUpState;
    }
}
