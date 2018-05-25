package com.lift.game.controller.powerups;

/**
 * Implements a power up with a single action.
 */
public abstract class StaticPowerUP implements  PowerUp{

    /**
     * Time left before the power up disappears.
     */
    protected Float timeToDisappear;

    /**
     * State of the power up.
     */
    protected PowerUpState powerUpState;

    /**
     * Constructs a static power up with a given time to disappear.
     *
     * @param timeToDisappear Time before the power up disappears.
     *
     */
    public StaticPowerUP(Float timeToDisappear) {
        this.timeToDisappear = timeToDisappear;
        this.powerUpState = PowerUpState.Waiting;
    }

    /**
     * Returns the time left before the power up disappears.
     * @return Time left before the power up disappears.
     */
    public Float getTimeToDisappear() {
        return timeToDisappear;
    }

    /**
     * Returns the power up's state.
     * @return Power up's state.
     */
    public PowerUpState getPowerUpState() {
        return powerUpState;
    }

    /**
     * Updates a power ups stats.
     *
     * @param delta Time since the last update.
     * @return True when the power up is finished.
     */
    @Override
    public boolean update(float delta) {
        if(powerUpState == PowerUpState.Waiting) {
            timeToDisappear -= delta;
        }
        if (timeToDisappear <= 0f) {
            powerUpState = PowerUpState.Done;
            return true;
        }
        return powerUpState == PowerUpState.Done;
    }
}
