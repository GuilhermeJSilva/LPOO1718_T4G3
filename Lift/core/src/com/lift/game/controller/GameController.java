package com.lift.game.controller;

import com.lift.game.controller.entities.ElevatorBody;
import com.lift.game.model.GameModel;

/**
 * 
 * Controls the game.
 *
 */
public class GameController {
	
	/**
	 * The buildings height in meters.
	 */
	public static final Integer BUILDING_HEIGHT = 100;
	
	/**
	 * The buildings width in meters.
	 */
	public static final Integer BUILDING_WIDTH = 40;
	
	
	/**
	 * Game's elevator.
	 */
	private ElevatorBody elevator;
	
	/**
	 * Stores the singleton.
	 */
	public static GameController instance;
	
	
	/**
	 * Constructs the model.
	 */
	private GameController() {
		super();
		this.elevator = new ElevatorBody(GameModel.getInstance().getElevator());
		
	}
	
	/**
	 * Returns the game model instance.
	 * @return Game model instance.
	 */
	public static GameController getInstance() {
		if(instance == null)
			instance = new GameController();
		return instance;
	}

	/**
	 * Returns the controller's elevator.
	 * @return Controller's elevator.
	 */
	public ElevatorBody getElevator() {
		return elevator;
	}
	

}
