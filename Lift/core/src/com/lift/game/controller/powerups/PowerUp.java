package com.lift.game.controller.powerups;

/**
 * Interface for power ups to use.
 */
public interface PowerUp {
    /**
     * Runs when a power up is picked.
     */
    void pickup();

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
     */
    void disappear();
}
