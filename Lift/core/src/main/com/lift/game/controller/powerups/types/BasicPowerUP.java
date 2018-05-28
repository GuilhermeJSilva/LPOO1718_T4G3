package com.lift.game.controller.powerups.types;

import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.World;
import com.lift.game.controller.GameController;
import com.lift.game.controller.entities.EntityBody;
import com.lift.game.controller.powerups.PowerUp;
import com.lift.game.controller.powerups.PowerUpState;
import com.lift.game.model.entities.EntityModel;
import com.lift.game.model.entities.PowerUpModel;

import static com.lift.game.controller.entities.ElevatorBody.ELEVATOR_MASK;

/**
 * Implements a power up with a single action.
 */
public abstract class BasicPowerUP extends EntityBody implements PowerUp {

    /**
     * Radius of the power up's body expressed in meters.
     */
    public static final float RADIUS_OF_THE_BODY = 1f;

    /**
     * Collision mask for the power ups.
     */
    public static final short PU_MASK = (short) (1 << 6);

    /**
     * Time left before the power up disappears.
     */
    protected Float timeToDisappear;

    /**
     * State of the power up.
     */
    protected PowerUpState powerUpState;

    /**
     * Constructs a static power up with a given time to disappear.
     *
     * @param timeToDisappear Time before the power up disappears.
     * @param model           Model the power up is based on.
     * @param world           World the power is in.
     */
    public BasicPowerUP(Float timeToDisappear, EntityModel model, World world) {
        super(world, model, BodyDef.BodyType.DynamicBody);
        this.addCircularFixture(this.getBody(), RADIUS_OF_THE_BODY, 100, 0, 0, PU_MASK, ELEVATOR_MASK, true);
        this.timeToDisappear = timeToDisappear;
        this.powerUpState = PowerUpState.Waiting;
        this.getBody().setGravityScale(0);
    }

    /**
     * Returns the time left before the power up disappears.
     *
     * @return Time left before the power up disappears.
     */
    public Float getTimeToDisappear() {
        return timeToDisappear;
    }

    /**
     * Returns the power up's state.
     *
     * @return Power up's state.
     */
    public PowerUpState getPowerUpState() {
        powerUpState = ((PowerUpModel) this.getBody().getUserData()).getPowerUpState();
        return powerUpState;
    }

    /**
     * Changes the state of the power up.
     *
     * @param powerUpState New value for the power up state.
     */
    @Override
    public void setPowerUpState(PowerUpState powerUpState) {
        ((PowerUpModel) this.getBody().getUserData()).setPowerUpState(powerUpState);
        this.powerUpState = powerUpState;
    }

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
                    this.disappear(gameController);
                    flagForRemoval();
                    setPowerUpState(PowerUpState.Done);
                    return true;
                }
                break;
            case PickedUp:
                if (this.pickup(gameController)) {
                    flagForRemoval();
                    setPowerUpState(PowerUpState.Done);
                }
                break;
            case Active:
                break;
            case Done:
                break;
        }
        return getPowerUpState() == PowerUpState.Done;
    }

    private void flagForRemoval() {
        ((PowerUpModel) this.getBody().getUserData()).setFlaggedForRemoval(true);
    }
}
