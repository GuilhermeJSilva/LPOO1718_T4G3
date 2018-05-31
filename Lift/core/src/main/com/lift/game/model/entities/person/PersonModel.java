package com.lift.game.model.entities.person;

import com.lift.game.model.Side;
import com.lift.game.model.entities.EntityModel;

/**
 * Models a person.
 */
public class PersonModel extends EntityModel {

    /**
     * Starting satisfaction.
     */
    public static final Float STARTING_SATISFACTION = 25f;

    /**
     * Satisfaction.
     */
    private Float satisfaction;

    /**
     * Person's destination.
     */
    private final Integer destination;

    /**
     * Type of the person.
     */
    private final PersonType personType;

    /**
     * State of the person.
     */
    private PersonState personState;

    /**
     * Floor of the person.
     */
    private final int floor;


    /**
     * True if a person is trying to enter the elevator.
     */
    private boolean tryingToEnter;

    /**
     * Position of the person in the platform.
     */
    private final int plat_position;

    /**
     * Construst the model for a person.
     * @param x X position.
     * @param y Y position.
     * @param floor Floor of the person.
     * @param side Side of the screen.
     * @param destination Destination floor.
     * @param people_position Position of the person on the platform.
     */
    public PersonModel(float x, float y, int floor, Side side, int destination, int people_position) {
        super(x, y, side);

        this.personType = RandomTypeGenerator.getRandomType();
        this.personState = PersonState.Waiting;
        this.tryingToEnter = false;

        this.destination = destination;
        this.floor = floor;

        this.plat_position = people_position;
        this.satisfaction = STARTING_SATISFACTION;
    }

    /**
     * Returns the person's satisfaction.
     *
     * @return Satisfaction.
     */
    public Float getSatisfaction() {
        return satisfaction;
    }

    /**
     * Updates the person's satisfaction based on the time that passed.
     *
     * @param delta Time that passed.
     * @return True if the satisfaction has reached zero.
     */
    public boolean update(float delta) {
        this.satisfaction -= delta;
        return satisfaction < 0;
    }

    /**
     * Returns the person's destination.
     *
     * @return Person's destination.
     */
    public Integer getDestination() {
        return destination;
    }

    /**
     * Returns the person's type.
     *
     * @return Person's type.
     */
    public PersonType getPersonType() {
        return personType;
    }


    /**
     * Changes the person's state.
     * @param personState New person state.
     */
    public void setPersonState(PersonState personState) {
        this.personState = personState;
    }

    /**
     * @return Person's state.
     */
    public PersonState getPersonState() {
        return personState;
    }

    /**
     * @return Person's floor.
     */
    public int getFloor() {
        return floor;
    }

    /**
     * @return True if the person is trying to enter.
     */
    public boolean isTryingToEnter() {
        return tryingToEnter;
    }

    /**
     * Changes the trying to enter flag.
     * @param tryingToEnter New value for the flag.
     */
    public void setTryingToEnter(boolean tryingToEnter) {
        this.tryingToEnter = tryingToEnter;
    }

    /**
     * Returns the position
     * @return Position of the person in plat.
     */
    public int getPlat_position() {
        return plat_position;
    }
}
