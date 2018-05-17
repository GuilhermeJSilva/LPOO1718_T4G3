package com.lift.game.controller;

import com.badlogic.gdx.physics.box2d.*;
import com.lift.game.controller.entities.PersonBody;
import com.lift.game.controller.entities.pstrategies.DrunkenMovement;
import com.lift.game.controller.entities.pstrategies.MovementStrategy;
import com.lift.game.controller.entities.pstrategies.RegularMovement;
import com.lift.game.controller.entities.pstrategies.StrategySelector;
import com.lift.game.model.GameModel;
import com.lift.game.model.entities.ElevatorModel;
import com.lift.game.model.entities.PlatformModel;
import com.lift.game.model.entities.person.PersonModel;
import com.lift.game.model.entities.person.PersonState;

import static com.lift.game.controller.entities.PlatformBody.PLATFORM_END_SENSOR;

public class GameCollisionHandler implements ContactListener {
    public GameCollisionHandler() {
        super();
    }

    public void beginContact(Contact contact) {
        Body bodyA = contact.getFixtureA().getBody();
        Body bodyB = contact.getFixtureB().getBody();

        checkIfPlatformElevatorCollision(bodyA, bodyB);
        checkIfPlatformPersonCollision(contact, bodyA, bodyB);
        checkIfPersonPersonCollision(bodyA, bodyB);


    }

    private void checkIfPersonPersonCollision(Body bodyA, Body bodyB) {
        if (bodyA.getUserData() instanceof PersonModel && bodyB.getUserData() instanceof PersonModel) {
            solvePersonPersonCollision(bodyA, bodyB);
        }
    }

    private void solvePersonPersonCollision(Body person1, Body person2) {
        MovementStrategy movementStrategy1 = StrategySelector.getStrategy((PersonModel) person1.getUserData());
        MovementStrategy movementStrategy2 = StrategySelector.getStrategy((PersonModel) person2.getUserData());
        MovementStrategy movementStrategy;
        if (movementStrategy1.getPriority() > movementStrategy2.getPriority())
            movementStrategy = movementStrategy1;
        else
            movementStrategy = movementStrategy2;
        movementStrategy.collisionPersonPersonInPlatform(person1, person2);


    }

    private void checkIfPlatformPersonCollision(Contact contact, Body bodyA, Body bodyB) {
        if (bodyA.getUserData() instanceof PlatformModel && bodyB.getUserData() instanceof PersonModel) {
            solvePersonPlatformCollision(bodyB, bodyA, contact.getFixtureA().getFilterData().categoryBits);
        } else if (bodyA.getUserData() instanceof PersonModel && bodyB.getUserData() instanceof PlatformModel) {
            solvePersonPlatformCollision(bodyA, bodyB, contact.getFixtureB().getFilterData().categoryBits);
        }
    }

    private void solvePersonPlatformCollision(Body personBody, Body platformBody, int platformFixture) {
        MovementStrategy movementStrategy = StrategySelector.getStrategy((PersonModel) personBody.getUserData());
        movementStrategy.solvePersonPlatformCollision(personBody, platformBody, platformFixture);
    }


    private void checkIfPlatformElevatorCollision(Body bodyA, Body bodyB) {
        if (bodyA.getUserData() instanceof PlatformModel && bodyB.getUserData() instanceof ElevatorModel) {
            handlePlatformElevatorCollision(bodyA, bodyB, bodyB.getLinearVelocity().y < 0);
        } else if (bodyA.getUserData() instanceof ElevatorModel && bodyB.getUserData() instanceof PlatformModel) {
            handlePlatformElevatorCollision(bodyB, bodyA, bodyA.getLinearVelocity().y < 0);
        }
    }

    private void handlePlatformElevatorCollision(Body bodyA, Body bodyB, boolean b) {
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


    public void endContact(Contact contact) {
        Body bodyA = contact.getFixtureA().getBody();
        Body bodyB = contact.getFixtureB().getBody();

        checkIfEndPlatformElevatorCollision(bodyA, bodyB);
        checkIfEndPlatformPersonCollision(bodyA, bodyB);
        checkIfEndPersonPersonCollision(bodyA, bodyB);
    }

    private void checkIfEndPersonPersonCollision(Body bodyA, Body bodyB) {
        if (bodyA.getUserData() instanceof PersonModel && bodyB.getUserData() instanceof PersonModel) {
            //TODO Implement
        }
    }

    private void checkIfEndPlatformPersonCollision(Body bodyA, Body bodyB) {
        if (bodyA.getUserData() instanceof PlatformModel && bodyB.getUserData() instanceof PersonModel) {
            //((PersonModel) bodyB.getUserData()).setPersonState(PersonState.FreeFlying);
        } else if (bodyA.getUserData() instanceof PersonModel && bodyB.getUserData() instanceof PlatformModel) {
            //((PersonModel) bodyA.getUserData()).setPersonState(PersonState.FreeFlying);
        }
    }

    private void checkIfEndPlatformElevatorCollision(Body bodyA, Body bodyB) {
        if (bodyA.getUserData() instanceof PlatformModel && bodyB.getUserData() instanceof ElevatorModel) {
            handlePlatformElevatorCollision(bodyA, bodyB, bodyB.getLinearVelocity().y > 0);
        } else if (bodyA.getUserData() instanceof ElevatorModel && bodyB.getUserData() instanceof PlatformModel) {
            handlePlatformElevatorCollision(bodyB, bodyA, bodyA.getLinearVelocity().y > 0);
        }
    }


    public void preSolve(Contact contact, Manifold oldManifold) {
    }


    public void postSolve(Contact contact, ContactImpulse impulse) {

    }
}
