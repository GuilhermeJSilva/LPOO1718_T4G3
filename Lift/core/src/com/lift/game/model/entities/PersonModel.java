package com.lift.game.model.entities;

public class PersonModel extends EntityModel{
	
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
	 * Default person constructor.
	 * 
	 * @param X
	 *            position.
	 * @param Y
	 *            position.
	 */
	public PersonModel(Float x, Float y, Float satisfaction_factor) {
		super(x, y);
		this.satisfaction = STARTING_SATISFACTION;
		this.satisfaction_factor = satisfaction_factor;
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
}
