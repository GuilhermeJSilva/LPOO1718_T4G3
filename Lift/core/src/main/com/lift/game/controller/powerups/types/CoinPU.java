package com.lift.game.controller.powerups.types;

import com.badlogic.gdx.physics.box2d.World;
import com.lift.game.controller.GameController;
import com.lift.game.model.entities.EntityModel;

/**
 * Gives coins to the user.
 */
public class CoinPU extends BasicPowerUP {
    /**
     * Time to disappear.
     */
    public static final float TIME_TO_DISAPPEAR = 5f;

    /**
     * Number of coins it gives.
     */
    public static final int COINS = 10;

    /**
     * Constructs a static power up with a given time to disappear.
     *
     * @param model           Model the power up is based on.
     * @param world           World the power is in.
     */
    public CoinPU(EntityModel model, World world) {
        super(TIME_TO_DISAPPEAR, model, world);
    }

    /**
     * Runs when a power up is picked.
     *
     * @param gameController Controller to be manipulated.
     * @return True if the action was successful.
     */
    @Override
    public boolean pickup(GameController gameController) {
        gameController.getGameModel().incCoins(COINS);
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
