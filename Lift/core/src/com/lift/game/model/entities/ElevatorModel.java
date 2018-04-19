package com.lift.game.model.entities;

import java.util.ArrayList;
import java.util.List;

import com.lift.game.model.GameModel;

/**
 * Model representing an elevator.
 */
public class ElevatorModel extends EntityModel {
	/**
	 * Maximum capacity of the elevator.
	 */
	private Integer capacity;

	/**
	 * People in the elevator.
	 */
	private ArrayList<List<PersonModel>> passengers;

	/**
	 * True if the elevator is not moving.
	 */
	private Boolean stopped;

	/**
	 * Default constructor.
	 */
	public ElevatorModel(Integer capacity) {
		//TODO: change X depending on the view.
		super(0.0, 0.0);
		this.capacity = capacity;
		this.passengers = new ArrayList<List<PersonModel>>(GameModel.getInstance().getN_levels());
	}

	/**
	 * Returns the capacity of the elevator.
	 * 
	 * @return Capacity of the elevator.
	 */
	public Integer getCapacity() {
		return capacity;
	}

	/**
	 * Returns the passengers in the elevator.
	 * 
	 * @return Passengers in the elevator.
	 */
	public ArrayList<List<PersonModel>> getPassengers() {
		return passengers;
	}

	/**
	 * Returns true if the elevator is stopped.
	 * 
	 * @return True if the elevator is stopped, false otherwise.
	 */
	public Boolean getStopped() {
		return stopped;
	}
}
