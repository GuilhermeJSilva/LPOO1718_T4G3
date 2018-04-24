package com.lift.game.controller.entities;
import static com.lift.game.controller.GameController.METERS_PER_FLOOR;
import com.badlogic.gdx.physics.box2d.World;
import com.lift.game.model.entities.ElevatorModel;

public class ElevatorBody extends EntityBody{
	
	/**
	 * Margin to stop in a certain floor.
	 */
	private final float FLOOR_MARGIN = 0.1f;

	/**
	 * Height of the elevator.
	 */
	private final int height = 16;
	
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
		this.stopped = true;
		
		float density = 1f, friction = 0.5f, restitution = 0f;
		int width = 10;
		
		this.add_fixture(body, new float[] {0, 0, 0, 16, 10, 0, 10, 16}
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
		this.target_floor = floor;
		if(floor * METERS_PER_FLOOR >= this.getY()) {
			System.out.println("GOING DOWN");
			this.setLinearVelocity(0, 10);
		}
		else
			this.setLinearVelocity(0, -10);
		this.stopped = false;
	}
	
	/**
	 * Verifies if  it reached the target floor.
	 * @return Returns true if the elevator stopped.
	 */
	public boolean reached_floor() {
		float y = (this.body.getPosition().y + this.height/2)/10;
		System.out.println(y + " WITH TARGET " + target_floor );
		if(y >= target_floor - FLOOR_MARGIN && y <= target_floor + FLOOR_MARGIN) {
			this.stopped = true;
			this.body.setLinearVelocity(0, 0);
			this.body.setTransform((float) this.body.getPosition().x,(float) target_floor * METERS_PER_FLOOR + this.height/2, 0);
			return true;
		}
		return false;
	}

	
}
