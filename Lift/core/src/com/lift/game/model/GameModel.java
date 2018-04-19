package com.lift.game.model;

import java.util.ArrayList;
import java.util.List;

import com.lift.game.model.entities.ElevatorModel;
import com.lift.game.model.entities.PersonModel;

/**
 * Represents the current state of the game.
 * Implements a singleton.
 */
public class GameModel {
		
	/**
	 * Default number of floors.
	 */
	private static final Integer DEFAULT_LEVEL = 5;
	
	
	/**
	 * Stores the singleton.
	 */
	public static GameModel instance;
	
	/**
	 * Game's elevator.
	 */
	private ElevatorModel elevator;
	
	/**
	 * People waiting for the elevator.
	 */
	private ArrayList<List<PersonModel>> waiting_people;
	
	/**
	 * Constructs the model.
	 */
	private GameModel() {
		super();
		n_levels = DEFAULT_LEVEL;
	}
	
	/**
	 * Returns the game model instance.
	 * @return Game model instance.
	 */
	public static GameModel getInstance() {
		if(instance == null)
			instance = new GameModel();
		return instance;
	}
	
	/**
	 * Number of floors in the game.
	 */
	private Integer n_levels;

	/**
	 * Returns the number of levels in the current model.
	 * @return Number of levels in the current model.
	 */
	public Integer getN_levels() {
		return n_levels;
	}

	/**
	 * Returns the elevator.
	 * @return Game's elevator.
	 */
	public ElevatorModel getElevator() {
		return elevator;
	}

	/**
	 * Returns the people waiting for the elevator.
	 * @return People waiting for the elevator.
	 */
	public ArrayList<List<PersonModel>> getWaiting_people() {
		return waiting_people;
	}
	
	public void update(float delta) {
		for (List<PersonModel> list : waiting_people) {
			for (PersonModel person : list) {
				person.update(delta);
			}
		}
		
		ArrayList<List<PersonModel>> passengers = this.elevator.getPassengers();
		
		for (List<PersonModel> list : passengers) {
			for (PersonModel person : list) {
				person.update(delta);
			}
		}
	}
}
