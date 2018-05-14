package com.lift.game.controller;

import com.badlogic.gdx.physics.box2d.*;
import com.lift.game.model.GameModel;
import com.lift.game.model.entities.ElevatorModel;
import com.lift.game.model.entities.PlatformModel;
import com.lift.game.model.entities.person.PersonModel;

import static com.lift.game.controller.entities.PlatformBody.PLATFORM_END_SENSOR;

public class GameCollisionHandler implements ContactListener {
    public GameCollisionHandler() {
        super();
    }

    public void beginContact(Contact contact) {
        Body bodyA = contact.getFixtureA().getBody();
        Body bodyB = contact.getFixtureB().getBody();

        if (bodyA.getUserData() instanceof PlatformModel && bodyB.getUserData() instanceof ElevatorModel) {
            handlePlatformCollision(bodyA, bodyB, bodyB.getLinearVelocity().y < 0);

        } else if (bodyA.getUserData() instanceof ElevatorModel && bodyB.getUserData() instanceof PlatformModel) {
            handlePlatformCollision(bodyB, bodyA, bodyA.getLinearVelocity().y < 0);
        } else if (bodyA.getUserData() instanceof PlatformModel && bodyB.getUserData() instanceof PersonModel) {
            solvePersonPlatformCollision(bodyB, bodyA, contact.getFixtureA().getFilterData().categoryBits);
        } else if (bodyA.getUserData() instanceof PersonModel && bodyB.getUserData() instanceof PlatformModel) {
            solvePersonPlatformCollision(bodyA, bodyB, contact.getFixtureB().getFilterData().categoryBits);
        } else if (bodyA.getUserData() instanceof PersonModel && bodyB.getUserData() instanceof PersonModel) {
            float x_velocity;
            if (bodyA.getLinearVelocity().x < 0 || bodyB.getLinearVelocity().x < 0)
                x_velocity = Math.max(bodyA.getLinearVelocity().x, bodyB.getLinearVelocity().x);
            else
                x_velocity = Math.min(bodyA.getLinearVelocity().x, bodyB.getLinearVelocity().x);
            bodyB.setLinearVelocity(x_velocity, 0);
            bodyA.setLinearVelocity(x_velocity, 0);
        }


    }

    public void endContact(Contact contact) {
        Body bodyA = contact.getFixtureA().getBody();
        Body bodyB = contact.getFixtureB().getBody();

        if (bodyA.getUserData() instanceof PlatformModel && bodyB.getUserData() instanceof ElevatorModel) {
            handlePlatformCollision(bodyA, bodyB, bodyB.getLinearVelocity().y > 0);

        } else if (bodyA.getUserData() instanceof ElevatorModel && bodyB.getUserData() instanceof PlatformModel) {
            handlePlatformCollision(bodyB, bodyA, bodyA.getLinearVelocity().y > 0);
        } else if (bodyA.getUserData() instanceof PlatformModel && bodyB.getUserData() instanceof PersonModel) {
            bodyB.setGravityScale(1);
        } else if (bodyA.getUserData() instanceof PersonModel && bodyB.getUserData() instanceof PlatformModel) {
            bodyA.setGravityScale(1);
        } else if (bodyA.getUserData() instanceof PersonModel && bodyB.getUserData() instanceof PersonModel) {

        }
    }

    private void handlePlatformCollision(Body bodyA, Body bodyB, boolean b) {
        PlatformModel pm = (PlatformModel) bodyA.getUserData();
        ElevatorModel em = (ElevatorModel) bodyB.getUserData();


        if (em == GameModel.getInstance().getLeft_elevator()) {
            if (em.getTarget_floor() == GameModel.getInstance().getLeft_floors().indexOf(pm) && b) {
                bodyB.setLinearVelocity(0, 0);
            }
        } else {
            if (em.getTarget_floor() == GameModel.getInstance().getRight_floors().indexOf(pm) && b) {
                bodyB.setLinearVelocity(0, 0);
            }
        }
    }

    public void preSolve(Contact contact, Manifold oldManifold) {
        contact.setEnabled(false);
    }

    private void solvePersonPlatformCollision(Body personBody, Body platformBody, int platformFixture) {
        if (platformFixture == PLATFORM_END_SENSOR) {
            personBody.setLinearVelocity(0, 0f);
        } else {
            personBody.setGravityScale(0);
            personBody.setLinearVelocity(personBody.getLinearVelocity().x, 0f);
        }
    }

    public void postSolve(Contact contact, ContactImpulse impulse) {

    }
}
