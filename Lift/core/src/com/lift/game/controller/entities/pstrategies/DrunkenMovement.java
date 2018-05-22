package com.lift.game.controller.entities.pstrategies;

import com.badlogic.gdx.physics.box2d.Body;
import com.lift.game.model.entities.person.Side;

/**
 * Represents the movement of a drunken person.
 */
public class DrunkenMovement extends NullStrategy implements MovementStrategy {
    /**
     * Initial velocity of the person.
     */
    private static final int INITIAL_V = 2;

    /**
     * Priority of the strategy.
     */
    private final Integer priority = 1;

    /**
     * Constructs the strategy of drunken person.
     */
    DrunkenMovement() {
    }

    @Override
    public int getPriority() {
        return priority;
    }


    @Override
    public void initialMovement(Body body, Side side) {
        if (side == Side.Left) {
            body.setLinearVelocity(INITIAL_V, 0);
        } else {
            body.setLinearVelocity(-INITIAL_V, 0);
        }
    }

    @Override
    public float getSatisfactionDelta(float delta) {
        return 0;
    }

    @Override
    public float getTimeIncrease() {
        return 2f;
    }
}
