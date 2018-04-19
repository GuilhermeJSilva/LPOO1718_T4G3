package com.lift.game.model.entities;

public class EntityModel {
	/**
	 * Position in the y axis.
	 */
	private Double y;

	/**
	 * Position in the x axis.
	 */
	private Double x;

	/**
	 * Default person constructor.
	 * 
	 * @param X
	 *            position.
	 * @param Y
	 *            position.
	 */
	public EntityModel(Double x, Double y) {
		super();
		this.y = y;
		this.x = x;
	}

	/**
	 * Returns the y position of the entity.
	 * 
	 * @return Y position of the entity.
	 */
	public Double getY() {
		return y;
	}

	/**
	 * Returns the x position of the entity.
	 * 
	 * @return X position of the entity.
	 */
	public Double getX() {
		return x;
	}
}
