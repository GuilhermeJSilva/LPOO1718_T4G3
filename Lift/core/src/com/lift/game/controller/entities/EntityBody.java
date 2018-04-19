package com.lift.game.controller.entities;

import com.lift.game.model.entities.EntityModel;

public class EntityBody {
	
	/**
	 * Position in the y axis.
	 */
	private Double y;

	/**
	 * Position in the x axis.
	 */
	private Double x;


	/**
	 * Creates an entity Body.
	 * @param model Entity model.
	 */
	public EntityBody(EntityModel model) {
		super();
		this.y = model.getY();
		this.x = model.getX();
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
