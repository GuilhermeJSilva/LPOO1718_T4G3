package com.lift.game.controller.powerups.types;

import com.badlogic.gdx.physics.box2d.World;
import com.lift.game.LiftGame;
import com.lift.game.controller.GameController;
import com.lift.game.model.entities.EntityModel;

/**
 * Gives an extra life to the player.
 */
public class LifePU extends StaticPowerUP {

    /**
     * Time it takes the life power up to disappear.
     */
    public static final float TIME_TO_DISAPPEAR = 10f;


    /**
     * Constructs a static power up with a given time to disappear.
     *
     * @param model Model the power up is based on.
     * @param world World the power is in.
     */
    public LifePU(EntityModel model, World world) {
        super(TIME_TO_DISAPPEAR, model, world);
    }

    /**
     * Runs when a power up is picked.
     *
     * @param gameController Controller to be manipulated.
     */
    @Override
    public void pickup(GameController gameController) {
        gameController.getGameModel().incrementLives();
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
