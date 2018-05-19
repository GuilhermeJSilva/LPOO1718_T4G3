package com.lift.game.controller.entities.pstrategies;

import com.badlogic.gdx.physics.box2d.Body;
import com.lift.game.controller.entities.PersonBody;

//Declared as class because interfaces do not allow static methods.
public interface MovementStrategy {

    int getPriority();

    void collisionPersonPersonInPlatform(Body bodyA, Body bodyB);

    void solvePersonPlatformCollision(Body personBody, Body platformBody, int platformFixture);

    void initialMovement(Body body, boolean b);

    void giveUp(PersonBody personBody, char side);

    void collisionEndPersonPersonInPlatform(Body person1, Body person2, char side);
}
