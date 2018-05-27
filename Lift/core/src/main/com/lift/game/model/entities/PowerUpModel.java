package main.com.lift.game.model.entities;

import main.com.lift.game.controller.powerups.PowerUpState;
import main.com.lift.game.model.entities.person.Side;

/**
 * Models a power up.
 */
public class PowerUpModel extends EntityModel {

    /**
     * State of the power up.
     */
    private PowerUpState powerUpState;

    /**
     * Type of power up.
     */
    private PowerUpType powerUpType;
    /**
     * Default entity constructor.
     *
     * @param x    position.
     * @param y    position.
     * @param side Side of the screen.
     */
    public PowerUpModel(float x, float y, Side side) {
        super(x, y, side);
        this.powerUpState = PowerUpState.Waiting;
        this.powerUpType = PowerUpType.NullPowerUp;
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

    /**
     * Returns the power up type.
     * @return Power up type.
     */
    public PowerUpType getPowerUpType() {
        return powerUpType;
    }

    /**
     * Changes the power up type.
     * @param powerUpType Power up type.
     */
    public void setPowerUpType(PowerUpType powerUpType) {
        this.powerUpType = powerUpType;
    }
}
