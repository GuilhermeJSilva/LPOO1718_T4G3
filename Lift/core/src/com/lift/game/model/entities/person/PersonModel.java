package com.lift.game.model.entities.person;

import com.lift.game.model.entities.EntityModel;

public class PersonModel extends EntityModel {

    /**
     * Starting satisfaction.
     */
	public static Float STARTING_SATISFACTION = 10f;
	
	/**
	 * Satisfaction decrease factor.
	 */
	private Float satisfaction_factor;
	
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
     * Side of the screen the person is on.
     */
    private Side side;

    /**
     * New person.
     */
    private boolean new_person = true;
    private boolean tryingToEnter;


    public PersonModel(float x, float y, int floor, Side side, Float satisfaction_factor, int destination) {
		super(x, y);
		this.satisfaction = STARTING_SATISFACTION;
		this.satisfaction_factor = satisfaction_factor;
		this.destination = destination;
		this.personType = RandomTypeGenerator.getRandomType();
		this.personState = PersonState.Waiting;
		this.floor =  floor;
		this.side = side;
		this.tryingToEnter = false;
	}


    /**
	 * Returns the person's satisfaction.
	 * @return Satisfaction.
	 */
	public Float getSatisfaction() {
		return satisfaction;
	}

	/**
	 * Updates the person's satisfaction based on the time that passed.
	 * @param delta Time that passed.
	 */
	public boolean update(float delta) {
		this.satisfaction -= delta / satisfaction_factor;
		return satisfaction < 0;
	}

	/**
	 * Returns the person's destination.
	 * @return Person's destination.
	 */
	public Integer getDestination() {
		return destination;
	}

    /**
     * Returns the person's type.
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

    public Side getSide() {
        return side;
    }

    public boolean isTryingToEnter() {
        return tryingToEnter;
    }

    public void setTryingToEnter(boolean tryingToEnter) {
        this.tryingToEnter = tryingToEnter;
    }
}
