package com.lift.game.controller.entities;

import static com.lift.game.controller.entities.PlatformBody.PLATFORM_ELEVATOR_SENSOR;

import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.World;
import com.lift.game.controller.GameController;
import com.lift.game.model.entities.ElevatorModel;

/**
 * Represents the body of an elevator.
 */
public class ElevatorBody extends EntityBody {
    /**
     * Elevator's vertical speed.
     */
    private static int vy = 50;


    /**
     * Height of the elevator.
     */
    public static final int height = 8;

    /**
     *  Collision mask of the elevator.
     */
    public static  final short ELEVATOR_MASK = 1 << 1;

    /**
     * Width oh the elevator.
     */
    public static final int width = 4;

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

        float density = 1f, friction = 0.5f, restitution = 0f;
        this.add_fixture(body, new float[]{0, 0, 0, height, width, 0, width, height}
                , width, height, density, friction, restitution, ELEVATOR_MASK,  PLATFORM_ELEVATOR_SENSOR, true);
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
     * @param floor New target floor.
     */
    public void setTarget_floor(GameController gameController, Integer floor) {
        float y = (this.getY() - height / 2);
        if (this.target_floor != floor) {
            this.target_floor = floor;
            if (gameController.getFloors(this.getSide()).get(floor).getY() > y) {
                this.setLinearVelocity(0, vy);
                System.out.println("Upwards");
            } else if (gameController.getFloors(this.getSide()).get(floor).getY() < y) {
                this.setLinearVelocity(0, -vy);
                System.out.println("Downwards");
            }
        }
    }


}
