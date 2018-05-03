package com.lift.game.model.entities;

public class PersonModel extends EntityModel{

    /**
     * Starting satisfaction.
     */
	public static Float STARTING_SATISFACTION = 25f;
	
	/**
	 * Satisfaction decrease factor.
	 */
	protected Float satisfaction_factor;
	
	/**
	 * Satisfaction.
	 */
	private Float satisfaction;
	
	/**
	 * Person's destination.
	 */
	private Integer destination;

	/**
	 * Default person constructor.
	 * 
	 * @param X
	 *            position.
	 * @param Y
	 *            position.
	 */
	public PersonModel(Float x, Float y, Float satisfaction_factor, int destination) {
		super(x, y);
		this.satisfaction = STARTING_SATISFACTION;
		this.satisfaction_factor = satisfaction_factor;
		this.destination = destination;
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
	public void update(float delta) {
		this.satisfaction -= delta / satisfaction_factor;
	}

	/**
	 * Returns the person's destination.
	 * @return Person's destination.
	 */
	public Integer getDestination() {
		return destination;
	}
}
