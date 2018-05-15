package com.lift.game.controller.entities.pstrategies;

import com.badlogic.gdx.physics.box2d.Body;

//Declared as class because interfaces do not allow static methods.
public interface MovementStrategy {
    int priority = 0;

    int getPriority();

    void collisionPersonPersonInPlatform(Body bodyA, Body bodyB);

    void solvePersonPlatformCollision(Body personBody, Body platformBody, int platformFixture);


}
