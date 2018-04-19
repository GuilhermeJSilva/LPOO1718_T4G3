package com.lift.game.controller.entities;

import com.lift.game.model.entities.ElevatorModel;

public class ElevatorBody extends EntityBody{

	/**
	 * True if the elevator is not moving.
	 */
	private Boolean stopped;
	
	
	/**
	 * Returns true if the elevator is stopped.
	 * 
	 * @return True if the elevator is stopped, false otherwise.
	 */
	public Boolean isStopped() {
		return stopped;
	}
	
	/**
	 * Creates an elevator Body.
	 * @param model Elevator model.
	 */
	public ElevatorBody(ElevatorModel model) {
		super(model);
	}

}
