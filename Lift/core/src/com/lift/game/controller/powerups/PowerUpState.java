package com.lift.game.controller.powerups;

/**
 * Describes the states of a power up.
 */
public enum PowerUpState {
    /**
     * The power up is waiting to be picked up.
     */
    Waiting,
    /**
     * The power has just been picked up.
     */
    PickedUp,
    /**
     * The power up is active.
     */
    Active,
    /**
     * The power up is done with its activity.
     */
    Done
}
