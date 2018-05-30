package com.lift.game.controller.entities;

import com.badlogic.gdx.physics.box2d.World;
import com.lift.game.controller.utils.PhysicalVariables;
import com.lift.game.model.entities.ElevatorModel;

import java.util.ArrayList;

import static com.lift.game.controller.entities.PlatformBody.PLATFORM_ELEVATOR_SENSOR;
import static com.lift.game.controller.entities.ScreenSensorBody.BOTTOM_SENSOR;
import static com.lift.game.controller.entities.ScreenSensorBody.TOP_SENSOR;
import static com.lift.game.controller.powerups.types.BasicPowerUP.PU_MASK;

/**
 * Represents the body of an elevator.
 */
public class ElevatorBody extends EntityBody {
    /**
     * Elevator's vertical speed.
     */
    private static final int vy = 50;

    /**
     * Velocity multiplier.
     */
    private Float velocity_multiplier = 1f;

    /**
     * Height of the elevator.
     */
    public static final int ELEVATOR_HEIGHT = 8;

    /**
     * Collision mask of the elevator.
     */
    public static final short ELEVATOR_MASK = 1 << 1;

    /**
     * Width oh the elevator.
     */
    public static final int ELEVATOR_WIDTH = 4;

    /**
     * The elevator is heading towards this elevator.
     */
    private Integer target_floor;

    /**
     * The floors it can reach.
     */
    private ArrayList<PlatformBody> platformBodies;


    /**
     * Creates an elevator Body.
     *  @param world World the body is going to belong to.
     * @param model Elevator model.
     * @param platformBodies Floors it can reach.
     */
    public ElevatorBody(World world, ElevatorModel model, ArrayList<PlatformBody> platformBodies) {
        super(world, model);
        this.target_floor = model.getTarget_floor();
        this.platformBodies = platformBodies;

        PhysicalVariables phys = new PhysicalVariables(ELEVATOR_WIDTH, ELEVATOR_HEIGHT, 1, 0.5f, 0f);

        this.add_fixture(body, new float[]{0, 0, 0, ELEVATOR_HEIGHT, ELEVATOR_WIDTH, 0, ELEVATOR_WIDTH, ELEVATOR_HEIGHT}
                , phys, ELEVATOR_MASK, (short) (PLATFORM_ELEVATOR_SENSOR | PU_MASK | TOP_SENSOR | BOTTOM_SENSOR), true);
        this.body.setGravityScale(0);

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
     * @param floor          New target floor.
     *
     */
    public void setTarget_floor(Integer floor) {
        ElevatorModel elevatorModel = ((ElevatorModel) this.getBody().getUserData());
        float y = (this.getY() - ELEVATOR_HEIGHT / 2);
        if (!this.target_floor.equals(floor)) {
            this.target_floor = floor;
            if (platformBodies.get(floor).getY() + ELEVATOR_HEIGHT/2  > y) {
                this.setLinearVelocity(0, vy * velocity_multiplier);
                elevatorModel.setTarget_floor(floor);
                elevatorModel.setStopped(false);
            } else if (platformBodies.get(floor).getY() + ELEVATOR_HEIGHT/2 < y) {
                this.setLinearVelocity(0, -vy * velocity_multiplier);
                elevatorModel.setStopped(false);
                elevatorModel.setTarget_floor(floor);
            }
        }
    }

    /**
     * Changes the multiplier by the amount give.
     *
     * @param increment Delta of the multiplier.
     */
    public void change_multiplier(float increment) {
        velocity_multiplier += increment;
    }

    /**
     * Returns the absolute value of the velocity.
     *
     * @return Absolute value of the velocity.
     */
    public float getVelocity() {
        return vy * velocity_multiplier;
    }
}
