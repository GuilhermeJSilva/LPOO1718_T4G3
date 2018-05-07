package com.lift.game.model.entities;

import com.lift.game.model.entities.person.PersonModel;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Model representing an elevator.
 */
public class ElevatorModel extends EntityModel {
	
	/**
	 * Elevator start position.
	 */
	public static final float STARTING_Y = 15f;
	
	/**
	 * Maximum capacity of the elevator.
	 */
	private Integer capacity;

	/**
	 * People in the elevator.
	 */
	private ArrayList<List<PersonModel>> passengers;

    /**
     * Target model.
     */
    private int target_floor;

    /**
     * Changes the target floor;
     * @param target_floor
     */
    public void setTarget_floor(int target_floor) {
        this.target_floor = target_floor;
    }

    /**
     * Returns the target floor.
     * @return Target floor.
     */
    public int getTarget_floor() {
        return target_floor;
    }

    /**
	 * Default constructor.
	 */
	public ElevatorModel(Float x, Integer capacity, Integer levels) {
		super(x, STARTING_Y);
		this.capacity = capacity;
		this.passengers = new ArrayList<List<PersonModel>>(levels);
		for (int i = 0; i < passengers.size(); i++) {
			passengers.set(i, new LinkedList<PersonModel>());
		}
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
}
