package com.lift.game.controller.entities;

import com.badlogic.gdx.physics.box2d.World;
import com.lift.game.model.entities.ElevatorModel;

public class ElevatorBody extends EntityBody{
	
	/**
	 * Margin to stop in a certain floor.
	 */
	private final float FLOOR_MARGIN = 0.1f;

	/**
	 * True if the elevator is not moving.
	 */
	private Boolean stopped;
	
	/**
	 * The elevator is heading towards this elevator.
	 */
	private Integer target_floor;
	
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
	public ElevatorBody(World world, ElevatorModel model) {
		super(world, model);
		
		float density = 1f, friction = 0.5f, restitution = 0f;
		int width = 20, height = 40;
		
		this.add_fixture(body, new float[] {0, 0, 0, 20, 40, 0, 40, 20}
		, width, height, density, friction, restitution, (short)0, (short)0);
	}

	/**
	 * Returns the target floor.
	 * @return Target floor.
	 */
	public Integer getTarget_floor() {
		return target_floor;
	}

	/**
	 * Changes the target floor.
	 * @param target_floor New target floor.
	 */
	public void setTarget_floor(Integer target_floor) {
		this.target_floor = target_floor;
	}
	
	/**
	 * Verifies if  it reached the target floor.
	 * @return Returns true if the elevator stopped.
	 */
	public boolean reached_floor() {
		float x = this.body.getPosition().x;
		
		if(x >= target_floor - FLOOR_MARGIN && x <= target_floor + FLOOR_MARGIN) {
			this.stopped = true;
			this.body.setLinearVelocity(0, 0);
			this.body.setTransform((float) target_floor, this.body.getPosition().y, 0);
			return true;
		}
		return false;
	}

	
}
