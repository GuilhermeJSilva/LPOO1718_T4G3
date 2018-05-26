package com.lift.game.model.entities;

import com.lift.game.model.entities.person.Side;

/**
 * Models a power up.
 */
public class PowerUpModel extends EntityModel {

    /**
     * Default entity constructor.
     *
     * @param x    position.
     * @param y    position.
     * @param side Side of the screen.
     */
    public PowerUpModel(float x, float y, Side side) {
        super(x, y, side);
    }
}
