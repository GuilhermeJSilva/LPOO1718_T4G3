package com.lift.game.model;

import static com.lift.game.controller.GameController.BUILDING_HEIGHT;
import static com.lift.game.controller.GameController.METERS_PER_FLOOR;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import com.lift.game.model.entities.ElevatorModel;
import com.lift.game.model.entities.PersonModel;
import com.lift.game.model.entities.PlatformModel;

/**
 * Represents the current state of the game.
 * Implements a singleton.
 */
public class GameModel {
		
	/**
	 * Default number of floors.
	 */
	private static final Integer DEFAULT_N_LEVEL = 6;
	
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
     * TODO: REMOVE.
	 */
	private ArrayList<List<PersonModel>> waiting_people;



    /**
     * Floors of the game.
     */
    private ArrayList<PlatformModel> floors;
	
	/**
	 * Constructs the model.
	 */
	private GameModel() {
		super();
		n_levels = DEFAULT_N_LEVEL;
		elevator = new ElevatorModel(15f, 0, this.n_levels);
		this.floors = new ArrayList<PlatformModel>();

		for (int i = 1; i <= n_levels; i++) {
            floors.add(new PlatformModel(0,i * METERS_PER_FLOOR - METERS_PER_FLOOR/2f));
		}
		System.out.println(this.floors.size());
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
     * Returns the floors of the game.
     * @return Floors of the game.
     */
    public ArrayList<PlatformModel> getFloors() {
        return floors;
    }

	/**
	 * Returns the people waiting for the elevator.
	 * @return People waiting for the elevator.
	 */
	public ArrayList<List<PersonModel>> getWaiting_people() {
		return waiting_people;
	}
	
	/**
	 * Updates the parameters of the people, base don the time that passed.
	 * @param delta Time that passed.
	 */
	public void update(float delta) {
	    /*
		for (List<PersonModel> list : waiting_people) {
			for (PersonModel person : list) {
				person.update(delta);
			}
		}
		*/
		ArrayList<List<PersonModel>> passengers = this.elevator.getPassengers();
		
		for (List<PersonModel> list : passengers) {
			for (PersonModel person : list) {
				person.update(delta);
			}
		}
	}
	
	/**
	 * Adds a new person to waiting for the elevator.
	 * @param floor Floor the person is currently in.
	 * @param x X axis position of the person.
	 * @param satisfaction_factor Rate that the satisfaction decreases.
	 * @return The person model that was added.
	 */
	public PersonModel add_waiting_person(int floor, float x, float satisfaction_factor, int dest) {
		PersonModel new_p = new PersonModel(x,(float)floor*METERS_PER_FLOOR + 6.1f, satisfaction_factor, dest);
		waiting_people.get(floor).add(new_p);
		return new_p;
	}
}
