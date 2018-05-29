package com.lift.game.controller.entities;

import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.World;
import com.lift.game.controller.GameController;
import com.lift.game.controller.utils.PhysicalVariables;
import com.lift.game.model.entities.ElevatorModel;

import static com.lift.game.controller.entities.PlatformBody.PLATFORM_ELEVATOR_SENSOR;
import static com.lift.game.controller.powerups.types.BasicPowerUP.PU_MASK;

/**
 * Represents the body of an elevator.
 */
public class ElevatorBody extends EntityBody {
    /**
     * Elevator's vertical speed.
     */
    private static int vy = 50;

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
     * Creates an elevator Body.
     *
     * @param model Elevator model.
     */
    public ElevatorBody(World world, ElevatorModel model) {
        super(world, model, BodyDef.BodyType.DynamicBody);
        this.target_floor = model.getTarget_floor();

        PhysicalVariables phys = new PhysicalVariables(ELEVATOR_WIDTH, ELEVATOR_HEIGHT, 1, 0.5f, 0f);

        this.add_fixture(body, new float[]{0, 0, 0, ELEVATOR_HEIGHT, ELEVATOR_WIDTH, 0, ELEVATOR_WIDTH, ELEVATOR_HEIGHT}
                , phys, ELEVATOR_MASK, (short) (PLATFORM_ELEVATOR_SENSOR | PU_MASK), true);
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
     * @param gameController Controller that contains the floors.
     * @param floor          New target floor.
     *
     * @return  True if it changed target.
     */
    public boolean setTarget_floor(GameController gameController, Integer floor) {
        float y = (this.getY() - ELEVATOR_HEIGHT / 2);
        if (this.target_floor != floor) {
            this.target_floor = floor;
            if (gameController.getFloors(this.getSide()).get(floor).getY() + ELEVATOR_HEIGHT/2  > y) {
                this.setLinearVelocity(0, vy * velocity_multiplier);
                return true;
            } else if (gameController.getFloors(this.getSide()).get(floor).getY() + ELEVATOR_HEIGHT/2 < y) {
                this.setLinearVelocity(0, -vy * velocity_multiplier);
                return true;
            }
        }
        return false;
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
