package com.lift.game.controller.powerups;

import com.lift.game.controller.GameController;

/**
 * Interface for power ups to use.
 */
public interface PowerUp {
    /**
     * Runs when a power up is picked.
     * @param gameController Controller to be manipulated.
     */
    void pickup(GameController gameController);

    /**
     * Updates a power ups stats.
     *
     * @param delta Time since the last update.
     *
     * @return True when the power up is finished.
     */
    boolean update(float delta);

    /**
     * To run when the power up disappears.
     * @param gameController Controller to be manipulated.
     */
    void disappear(GameController gameController);
}
