package com.lift.game.controller;

import com.badlogic.gdx.physics.box2d.*;
import com.lift.game.model.GameModel;
import com.lift.game.model.entities.ElevatorModel;
import com.lift.game.model.entities.PlatformModel;

public class GameCollisionHandler implements ContactListener {
    public GameCollisionHandler() {
        super();
    }

    public void beginContact(Contact contact) {
        Body bodyA = contact.getFixtureA().getBody();
        Body bodyB = contact.getFixtureB().getBody();

        if(bodyA.getUserData() instanceof PlatformModel && bodyB.getUserData() instanceof ElevatorModel) {
            handlePlatformCollision(bodyA, bodyB, bodyB.getLinearVelocity().y < 0);

        }
        else if(bodyA.getUserData() instanceof ElevatorModel && bodyB.getUserData() instanceof PlatformModel) {
            handlePlatformCollision(bodyB, bodyA, bodyA.getLinearVelocity().y < 0);
        }

    }

    public void endContact(Contact contact) {
        Body bodyA = contact.getFixtureA().getBody();
        Body bodyB = contact.getFixtureB().getBody();

        if(bodyA.getUserData() instanceof PlatformModel && bodyB.getUserData() instanceof ElevatorModel) {
            handlePlatformCollision(bodyA, bodyB, bodyB.getLinearVelocity().y > 0);

        }
        else if(bodyA.getUserData() instanceof ElevatorModel && bodyB.getUserData() instanceof PlatformModel) {
            handlePlatformCollision(bodyB, bodyA, bodyA.getLinearVelocity().y > 0);
        }
    }

    private void handlePlatformCollision(Body bodyA, Body bodyB, boolean b) {
        PlatformModel pm = (PlatformModel) bodyA.getUserData();
        ElevatorModel em = (ElevatorModel) bodyB.getUserData();

        if(em == GameModel.getInstance().getLeft_elevator()) {
            if (em.getTarget_floor() == GameModel.getInstance().getLeft_floors().indexOf(pm) && b) {
                bodyB.setLinearVelocity(0, 0);
            }
        }
    }

    public void preSolve(Contact contact, Manifold oldManifold) {
    }

    public void postSolve(Contact contact, ContactImpulse impulse) {

    }
}
