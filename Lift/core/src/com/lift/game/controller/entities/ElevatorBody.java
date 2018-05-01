package com.lift.game.controller.entities;

import static com.lift.game.controller.GameController.METERS_PER_FLOOR;
import static com.lift.game.controller.entities.PlatformBody.PLATFORM_MASK;

import com.badlogic.gdx.physics.box2d.World;
import com.lift.game.controller.GameController;
import com.lift.game.model.entities.ElevatorModel;

public class ElevatorBody extends EntityBody {
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
     *  Collision mask of the elevator.
     */
    public static  final short ELEVATOR_MASK = 1 << 1;

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
     *
     * @param model Elevator model.
     */
    public ElevatorBody(World world, ElevatorModel model) {
        super(world, model);
        this.stopped = true;
        this.target_floor = model.getTarget_floor();

        float density = 1f, friction = 0.5f, restitution = 0f;
        this.add_fixture(body, new float[]{0, 0, 0, height, width, 0, width, height}
                , width, height, density, friction, restitution, ELEVATOR_MASK,  PLATFORM_MASK, false);

    }

    /**
     * Returns the target floor.
     *
     * @return Target floor.
     */
    public Integer getTarget_floor() {
        return target_floor;
    }

    /**
     * Changes the target floor.
     *
     * @param floor New target floor.
     */
    public void setTarget_floor(Integer floor) {
        float y = (this.getY() - this.height / 2);
        if (this.stopped && this.target_floor == floor)
            return;
        this.target_floor = floor;
        if (floor * METERS_PER_FLOOR > y) {
            this.setLinearVelocity(0, vy);
            this.stopped = false;
        } else if (floor * METERS_PER_FLOOR < y) {
            this.setLinearVelocity(0, -vy);
            this.stopped = false;
        }

    }


}
