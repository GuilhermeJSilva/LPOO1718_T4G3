package com.lift.game.model;

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
	public static GameModel model;
	
	
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
		if(model == null)
			model = new GameModel();
		return model;
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
}
