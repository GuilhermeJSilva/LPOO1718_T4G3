package com.lift.game.controller.powerups.types;

import com.badlogic.gdx.physics.box2d.World;
import com.lift.game.controller.GameController;
import com.lift.game.controller.powerups.PowerUp;
import com.lift.game.controller.powerups.PowerUpState;
import com.lift.game.model.entities.EntityModel;

/**
 * Implements the based class for a power up which is active.
 */
public abstract class TimedPowerUp extends StaticPowerUP implements PowerUp {

    /**
     * Active time for the power up.
     */
    private Float activeTime;

    /**
     * Constructs a timed power up with a given time to disappear and an active time.
     * @param timeToDisappear Time before the power up disappears.
     *
     * @param activeTime Time the power up will be active.
     */
    public TimedPowerUp(World world, EntityModel model, Float timeToDisappear, Float activeTime) {
        super(timeToDisappear, model, world);
        this.activeTime = activeTime;
    }

    /**
     * To run when the power up is in the ned of its life.
     * @param gameController Controller to be manipulated.
     */
    public abstract void end(GameController gameController);

    /**
     * Updates a power ups stats.
     *
     * @param gameController Controller to act upon.
     * @param delta          Time since the last update.
     * @return True when the power up is finished.
     */
    @Override
    public boolean update(GameController gameController, float delta) {
        switch (getPowerUpState()) {
            case Waiting:
                timeToDisappear -= delta;
                if (timeToDisappear <= 0f) {
                    setPowerUpState(PowerUpState.Done);
                    return true;
                }
                break;
            case PickedUp:
                this.pickup(gameController);
                setPowerUpState(PowerUpState.Active);
                break;
            case Active:
                activeTime -= delta;
                if (activeTime <= 0f) {
                    setPowerUpState(PowerUpState.Done);
                    return true;
                }
                break;
            case Done:
                break;
        }
        return getPowerUpState() == PowerUpState.Done;
    }
}
