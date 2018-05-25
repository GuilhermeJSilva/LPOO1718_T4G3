package com.lift.game.controller.powerups;

import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.World;
import com.lift.game.controller.entities.EntityBody;
import com.lift.game.model.entities.EntityModel;

/**
 * Implements a power up with a single action.
 */
public abstract class StaticPowerUP extends EntityBody implements PowerUp {

    /**
     * Radius of the power up's body expressed in meters.
     */
    public static final float RADIUS_OF_THE_BODY = 2f;

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
    public StaticPowerUP(Float timeToDisappear, EntityModel model, World world) {
        super(world, model, BodyDef.BodyType.DynamicBody);
        this.addCircularFixture(this.getBody(), RADIUS_OF_THE_BODY, 100,0,0, (short) 0, (short) 0 , true);
        this.timeToDisappear = timeToDisappear;
        this.powerUpState = PowerUpState.Waiting;
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
        return powerUpState;
    }

    /**
     * Updates a power ups stats.
     *
     * @param delta Time since the last update.
     * @return True when the power up is finished.
     */
    @Override
    public boolean update(float delta) {
        if (powerUpState == PowerUpState.Waiting) {
            timeToDisappear -= delta;
        }
        if (timeToDisappear <= 0f) {
            powerUpState = PowerUpState.Done;
            return true;
        }
        return powerUpState == PowerUpState.Done;
    }
}
