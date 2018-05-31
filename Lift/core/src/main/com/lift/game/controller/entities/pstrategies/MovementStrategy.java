package com.lift.game.controller.entities.pstrategies;

import com.badlogic.gdx.physics.box2d.Body;
import com.lift.game.controller.entities.PersonBody;
import com.lift.game.model.Side;

/**
 * Defines the interface for a movement strategy.
 */
public interface MovementStrategy {
    /**
     * Returns the priority of the strategy.
     * @return Priority of the strategy.
     */
    int getPriority();

    /**
     * Solves the collision between people in platforms.
     * @param bodyA One of the bodies that collided.
     * @param bodyB One of the bodies that collided.
     */
    void collisionPersonPersonInPlatform(Body bodyA, Body bodyB);

    /**
     * Solves a collision between a person and a platform.
     * @param personBody Body of the person.
     * @param platformBody Body of the platform.
     * @param platformFixture Category bits of the platform fixture.
     */
    void solvePersonPlatformCollision(Body personBody, Body platformBody, int platformFixture);

    /**
     * Initial movement of the body.
     * @param body Body of the person.
     * @param side Side of the screen its on.
     */
    void initialMovement(Body body, Side side);

    /**
     * To be call when a person gives up.
     * @param personBody Body of the person.
     * @param side Side of the screen its on.
     */
    void giveUp(PersonBody personBody, Side side);

    /**
     * Solves the collision between people in platforms.
     * @param person1 One of the bodies that collided.
     * @param person2 One of the bodies that collided.
     * @param side Side of the screen.
     */
    void collisionEndPersonPersonInPlatform(Body person1, Body person2, Side side);

    /**
     * Changes the basic delta.
     * @param delta Basic delta.
     * @return Delta updated for the person type.
     */
    float getSatisfactionDelta(float delta);

    /**
     * Returns the time it gives when a person is delivered.
     * @return Time it gives when a person is delivered.
     */
    float getTimeIncrease();
}
