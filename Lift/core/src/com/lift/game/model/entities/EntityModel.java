package com.lift.game.model.entities;

public class EntityModel {
	/**
	 * Position in the y axis.
	 */
	private float y;

	/**
	 * Position in the x axis.
	 */
	private float x;

	/**
	 * Default entity constructor.
	 * 
	 * @param X
	 *            position.
	 * @param Y
	 *            position.
	 */
	public EntityModel(float x, float y) {
		super();
		this.y = y;
		this.x = x;
	}

	/**
	 * Returns the y position of the entity.
	 * 
	 * @return Y position of the entity.
	 */
	public float getY() {
		return y;
	}

	/**
	 * Returns the x position of the entity.
	 * 
	 * @return X position of the entity.
	 */
	public float getX() {
		return x;
	}

	public void setPosition(float x, float y) {
		this.x = x;
		this.y = y;
	}
}
