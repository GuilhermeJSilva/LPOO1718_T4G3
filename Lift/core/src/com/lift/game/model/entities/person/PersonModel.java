package com.lift.game.model.entities.person;

import com.lift.game.model.entities.EntityModel;

public class PersonModel extends EntityModel {

    /**
     * Starting satisfaction.
     */
    public static Float STARTING_SATISFACTION = 25f;

    /**
     * Satisfaction.
     */
    private Float satisfaction;

    /**
     * Person's destination.
     */
    private Integer destination;

    /**
     * Type of the person.
     */
    private PersonType personType;

    /**
     * State of the person.
     */
    private PersonState personState;

    /**
     * Floor of the person.
     */
    private int floor;


    /**
     * New person.
     */
    private boolean new_person = true;

    /**
     * True if a person is trying to enter the elevator.
     */
    private boolean tryingToEnter;

    /**
     * Position of the person in the platform.
     */
    private int plat_position;

    public int n_updates = 0;

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

    @Override
    protected void finalize() throws Throwable {
        super.finalize();
        System.out.println("Updates " + n_updates);
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
     */
    public boolean update(float delta) {
        n_updates++;
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

    public boolean isNew_person() {
        return new_person;
    }

    public void setNew_person(boolean new_person) {
        this.new_person = new_person;
    }

    public void setPersonState(PersonState personState) {
        this.personState = personState;
    }

    public PersonState getPersonState() {
        return personState;
    }

    public int getFloor() {
        return floor;
    }


    public boolean isTryingToEnter() {
        return tryingToEnter;
    }

    public void setTryingToEnter(boolean tryingToEnter) {
        this.tryingToEnter = tryingToEnter;
    }

    public int getPlat_position() {
        return plat_position;
    }
}
