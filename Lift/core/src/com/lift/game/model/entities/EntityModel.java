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
     * Rotation of the model.
     */
    private float rotation = 0;

	/**
	 * Default entity constructor.
	 * 
	 * @param x
	 *            position.
	 * @param y
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

    public float getRotation() {
        return rotation;
    }

    public void setPosition(float x, float y, float rotation) {
		this.x = x;
		this.y = y;
		this.rotation = rotation;
	}
}
