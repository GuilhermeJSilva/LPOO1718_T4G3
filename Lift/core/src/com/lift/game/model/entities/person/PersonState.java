package com.lift.game.model.entities.person;

/**
 * Represents the state of a person.
 */
public enum PersonState {
    /**
     * The person is waiting in the platform and moving.
     */
    Waiting,
    /**
     * The person is waiting in the platform and not moving.
     */
    StoppedWaiting,
    /**
     * Fist person waiting,
     */
    FirstInLine,
    /**
     * The person is in the elevator.
     */
    InElevator,
    /**
     * The person has reached his/hers destination.
     */
    Reached,
    /**
     * The person has given up waiting.
     */
    GiveUP,
    /**
     * The person is free flying.
     */
    FreeFlying,
}
