package com.lift.game.controller.powerups.types;

import com.badlogic.gdx.physics.box2d.World;
import com.lift.game.controller.GameController;
import com.lift.game.model.entities.EntityModel;
import com.lift.game.model.entities.PowerUpModel;
import com.lift.game.model.entities.PowerUpType;
import com.lift.game.model.entities.person.Side;

/**
 * Changes the velocity of the elevator.
 */
public class VelocityPU extends TimedPowerUp {

    /**
     * Time before the power up disappears.
     */
    public static final float TIME_TO_DISAPPEAR = 10f;

    /**
     * Active time for this power up.
     */
    public static final float ACTIVE_TIME = 5f;

    /**
     * Constructs a timed power up with a given time to disappear and an active time.
     *
     * @param world           World the power is in.
     * @param model           Model the power up is based on.
     */
    public VelocityPU(EntityModel model, World world) {
        super(world, model, TIME_TO_DISAPPEAR, ACTIVE_TIME);
        ((PowerUpModel) model).setPowerUpType(PowerUpType.ElevatorVelocity);
    }

    /**
     * To run when the power up is in the end of its life.
     *
     * @param gameController Controller to be manipulated.
     */
    @Override
    public void end(GameController gameController) {
        gameController.getElevator(Side.Left).change_multiplier(-2);
        gameController.getElevator(Side.Right).change_multiplier(-2);

    }

    /**
     * Runs when a power up is picked.
     *
     * @param gameController Controller to be manipulated.
     * @return True if the action was successful.
     */
    @Override
    public boolean pickup(GameController gameController) {
        gameController.getElevator(Side.Left).change_multiplier(2);
        gameController.getElevator(Side.Right).change_multiplier(2);
        return true;
    }

    /**
     * To run when the power up disappears.
     *
     * @param gameController Controller to be manipulated.
     */
    @Override
    public void disappear(GameController gameController) {

    }
}