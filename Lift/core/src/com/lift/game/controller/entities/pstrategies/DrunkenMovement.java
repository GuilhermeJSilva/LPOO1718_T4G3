package com.lift.game.controller.entities.pstrategies;

import com.badlogic.gdx.physics.box2d.Body;
import com.lift.game.controller.entities.PersonBody;
import com.lift.game.model.GameModel;
import com.lift.game.model.entities.person.PersonModel;
import com.lift.game.model.entities.person.PersonState;

public class DrunkenMovement implements MovementStrategy {

    private static final int INITIAL_V = 2;

    private final Integer priority = 1;

    private static DrunkenMovement ourInstance = new DrunkenMovement();

    public static DrunkenMovement getInstance() {
        return ourInstance;
    }

    private DrunkenMovement() {
    }

    @Override
    public int getPriority() {
        return priority;
    }

    @Override
    public void collisionPersonPersonInPlatform(Body bodyA, Body bodyB) {

    }

    @Override
    public void solvePersonPlatformCollision(Body personBody, Body platformBody, int platformFixture) {
        if(personBody.getAngle() != 0) {
            PersonModel personModel = (PersonModel) personBody.getUserData();
            personModel.setPersonState(PersonState.FreeFlying);
            if(personModel.getSide() == 'L'){
                GameModel.getInstance().getLeft_floors().get(personModel.getFloor()).decrementNPeople();
            } else {
                GameModel.getInstance().getRight_floors().get(personModel.getFloor()).decrementNPeople();
            }

        }

    }

    @Override
    public void initialMovement(Body body, boolean b) {
        if (b) {
            body.setLinearVelocity(INITIAL_V, 0);
        } else {
            body.setLinearVelocity(-INITIAL_V, 0);
        }
    }

    @Override
    public float getGravityScale() {
        return 10;
    }

    @Override
    public void giveUp(PersonBody personBody, char side) {

    }

    @Override
    public void collisionEndPersonPersonInPlatform(Body person1, Body person2, char side) {

    }
}
