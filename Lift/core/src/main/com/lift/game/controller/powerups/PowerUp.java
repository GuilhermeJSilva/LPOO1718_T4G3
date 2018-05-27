package main.com.lift.game.controller.powerups;

import main.com.lift.game.controller.GameController;

/**
 * Interface for power ups to use.
 */
public interface PowerUp {
    /**
     * Runs when a power up is picked.
     * @param gameController Controller to be manipulated.
     * @return True if the action was successful.
     */
    boolean pickup(GameController gameController);

    /**
     * Updates a power ups stats.
     *
     * @param gameController Controller to act upon.
     * @param delta Time since the last update.
     *
     * @return True when the power up is finished.
     */
    boolean update(GameController gameController, float delta);

    /**
     * To run when the power up disappears.
     * @param gameController Controller to be manipulated.
     */
    void disappear(GameController gameController);

    /**
     * State of the power up.
     * @return Returns the state of the power up.
     */
    PowerUpState getPowerUpState();

    /**
     * Changes the state of the power up.
     *
     * @param powerUpState New value for the power up state.
     */
    void setPowerUpState(PowerUpState powerUpState);
}
