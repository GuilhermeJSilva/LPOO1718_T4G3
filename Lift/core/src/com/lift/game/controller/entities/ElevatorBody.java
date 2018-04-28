package com.lift.game.controller.entities;
import static com.lift.game.controller.GameController.METERS_PER_FLOOR;

import com.badlogic.gdx.physics.box2d.World;
import com.lift.game.model.entities.ElevatorModel;

public class ElevatorBody extends EntityBody{
	/**
	 * Elevator's vertical speed.
	 */
	private static int vy = 20;

	/**
	 * Margin to stop in a certain floor.
	 */
	private final float FLOOR_MARGIN = 0.5f;

	/**
	 * True if the elevator is not moving.
	 */
	private Boolean stopped;
	
	/**
	 * Height of the elevator.
	 */
	public static final int height = 16;


	/**
	 * Width oh the elevator.
	 */
	public static final int width = 10;

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
		this.stopped = true;
		
		float density = 1f, friction = 0.5f, restitution = 0f;
		this.add_fixture(body, new float[] {0, 0, 0, height, width, 0, width, height}
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
	public void setTarget_floor(Integer floor) {
		float y = (this.getY() - this.height/2);
		if(this.stopped && this.target_floor == floor)
			return;
		this.target_floor = floor;
		if(floor * METERS_PER_FLOOR > y) {
			this.setLinearVelocity(0, vy);
			this.stopped = false;
		}
		else if(floor * METERS_PER_FLOOR < y) {
			this.setLinearVelocity(0, -vy);
			this.stopped = false;
		}
			
	}
	
	/**
	 * Verifies if  it reached the target floor.
	 * @return Returns true if the elevator stopped.
	 */
	public boolean reached_floor() {
		float y = (this.body.getPosition().y);
		//System.out.println(y + " WITH TARGET " + (target_floor*METERS_PER_FLOOR + ElevatorModel.STARTING_Y));
		if(y >= target_floor*METERS_PER_FLOOR + ElevatorModel.STARTING_Y - FLOOR_MARGIN && y <= target_floor*METERS_PER_FLOOR + ElevatorModel.STARTING_Y + FLOOR_MARGIN) {
			this.stopped = true;
			this.body.setLinearVelocity(0, 0);
			//System.out.println((target_floor * METERS_PER_FLOOR + ElevatorModel.STARTING_Y));
			this.body.setTransform((float) this.body.getPosition().x,(float) target_floor * METERS_PER_FLOOR + ElevatorModel.STARTING_Y, 0);
			return true;
		}
		return false;
	}

	
}
