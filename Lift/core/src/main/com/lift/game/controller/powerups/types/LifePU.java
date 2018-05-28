package com.lift.game.controller.powerups.types;

import com.badlogic.gdx.physics.box2d.World;
import com.lift.game.controller.GameController;
import com.lift.game.model.entities.EntityModel;
import com.lift.game.model.entities.PowerUpModel;
import com.lift.game.model.entities.PowerUpType;

/**
 * Gives an extra life to the player.
 */
public class LifePU extends BasicPowerUP {

    /**
     * Time it takes the life power up to disappear.
     */
    public static final float TIME_TO_DISAPPEAR = 15f;


    /**
     * Constructs a static power up with a given time to disappear.
     *
     * @param model Model the power up is based on.
     * @param world World the power is in.
     */
    public LifePU(EntityModel model, World world) {
        super(TIME_TO_DISAPPEAR, model, world);
        ((PowerUpModel) model).setPowerUpType(PowerUpType.LifePowerUp);
    }

    /**
     * Runs when a power up is picked.
     *
     * @param gameController Controller to be manipulated.
     */
    @Override
    public boolean pickup(GameController gameController) {
        Integer before = gameController.getGameModel().getLives();
        gameController.getGameModel().incrementLives();
        return  gameController.getGameModel().getLives() - before == 1;
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
