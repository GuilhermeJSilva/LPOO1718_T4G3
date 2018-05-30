package com.lift.game.controller;

import com.badlogic.gdx.physics.box2d.*;
import com.lift.game.controller.entities.pstrategies.MovementStrategy;
import com.lift.game.controller.powerups.PowerUpState;
import com.lift.game.model.entities.ElevatorModel;
import com.lift.game.model.entities.PlatformModel;
import com.lift.game.model.entities.PowerUpModel;
import com.lift.game.model.entities.person.PersonModel;

class  GameCollisionHandler implements ContactListener {
    private final GameController gameController;

    public GameCollisionHandler(GameController gameController) {
        super();
        this.gameController = gameController;
    }

    public void beginContact(Contact contact) {
        Body bodyA = contact.getFixtureA().getBody();
        Body bodyB = contact.getFixtureB().getBody();

        checkIfPlatformElevatorCollision(bodyA, bodyB);
        checkIfPlatformPersonCollision(contact, bodyA, bodyB);
        checkIfPersonPersonCollision(bodyA, bodyB);
        checkIfPowerUpCollision(bodyA, bodyB);
        checkIfPlatformSensorCollision(contact);
        checkIfElevatorSensorCollision(contact);

    }

    private void checkIfElevatorSensorCollision(Contact contact) {
        Body bodyA = contact.getFixtureA().getBody();
        Body bodyB = contact.getFixtureB().getBody();
        if (bodyA.getUserData() instanceof ElevatorModel && bodyB.getUserData() == null) {
            gameController.getElevatorController().handleCollision(bodyA, contact.getFixtureB().getFilterData().categoryBits);
        } else if (bodyA.getUserData() == null && bodyB.getUserData() instanceof ElevatorModel) {
            gameController.getElevatorController().handleCollision(bodyB, contact.getFixtureA().getFilterData().categoryBits);
        }
    }

    private void checkIfPlatformSensorCollision(Contact contact) {
        Body bodyA = contact.getFixtureA().getBody();
        Body bodyB = contact.getFixtureB().getBody();
        if (bodyA.getUserData() instanceof PlatformModel && bodyB.getUserData() == null) {
            gameController.getPlatformController().handleCollision(bodyA, contact.getFixtureB().getFilterData().categoryBits);
        } else if (bodyA.getUserData() == null && bodyB.getUserData() instanceof PlatformModel) {
            gameController.getPlatformController().handleCollision(bodyB, contact.getFixtureA().getFilterData().categoryBits);
        }
    }

    private void checkIfPowerUpCollision(Body bodyA, Body bodyB) {
        if (bodyA.getUserData() instanceof PowerUpModel && bodyB.getUserData() instanceof ElevatorModel) {
            pickUpPowerUp(bodyA);
        } else if (bodyA.getUserData() instanceof ElevatorModel && bodyB.getUserData() instanceof PowerUpModel) {
            pickUpPowerUp(bodyB);
        }
    }

    private void pickUpPowerUp(Body powerUpBody) {
        PowerUpModel powerUpModel = (PowerUpModel) powerUpBody.getUserData();
        if (powerUpModel.getPowerUpState() == PowerUpState.Waiting) {
            powerUpModel.setPowerUpState(PowerUpState.PickedUp);
        }
    }

    private void checkIfPersonPersonCollision(Body bodyA, Body bodyB) {
        if (bodyA.getUserData() instanceof PersonModel && bodyB.getUserData() instanceof PersonModel) {
            solvePersonPersonCollision(bodyA, bodyB);
        }
    }

    private void solvePersonPersonCollision(Body person1, Body person2) {
        MovementStrategy movementStrategy1 = gameController.getStrategySelector().getStrategy((PersonModel) person1.getUserData());
        MovementStrategy movementStrategy2 = gameController.getStrategySelector().getStrategy((PersonModel) person2.getUserData());
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
        MovementStrategy movementStrategy = gameController.getStrategySelector().getStrategy((PersonModel) personBody.getUserData());
        movementStrategy.solvePersonPlatformCollision(personBody, platformBody, platformFixture);
    }


    private void checkIfPlatformElevatorCollision(Body bodyA, Body bodyB) {
        if (bodyA.getUserData() instanceof PlatformModel && bodyB.getUserData() instanceof ElevatorModel) {
            gameController.getElevatorController().handlePlatformElevatorCollision(bodyA, bodyB, bodyB.getLinearVelocity().y < 0);
        } else if (bodyA.getUserData() instanceof ElevatorModel && bodyB.getUserData() instanceof PlatformModel) {
            gameController.getElevatorController().handlePlatformElevatorCollision(bodyB, bodyA, bodyA.getLinearVelocity().y < 0);
        }
    }


    public void endContact(Contact contact) {
        Body bodyA = contact.getFixtureA().getBody();
        Body bodyB = contact.getFixtureB().getBody();

        checkIfEndPlatformElevatorCollision(bodyA, bodyB);
        checkIfEndPersonPersonCollision(bodyA, bodyB);
        checkIfEndPowerUP(bodyA, bodyB);
    }

    private void checkIfEndPowerUP(Body bodyA, Body bodyB) {
        if (bodyA.getUserData() instanceof PowerUpModel && bodyB.getUserData() instanceof ElevatorModel) {
            dropPowerUp(bodyA);
        } else if (bodyA.getUserData() instanceof ElevatorModel && bodyB.getUserData() instanceof PowerUpModel) {
            dropPowerUp(bodyB);
        }
    }

    private void dropPowerUp(Body powerUpBody) {
        PowerUpModel powerUpModel = (PowerUpModel) powerUpBody.getUserData();
        if (powerUpModel.getPowerUpState() == PowerUpState.PickedUp) {
            powerUpModel.setPowerUpState(PowerUpState.Waiting);
        }
    }

    private void checkIfEndPersonPersonCollision(Body person1, Body person2) {
        if (person1.getUserData() instanceof PersonModel && person2.getUserData() instanceof PersonModel) {
            MovementStrategy movementStrategy1 = gameController.getStrategySelector().getStrategy((PersonModel) person1.getUserData());
            MovementStrategy movementStrategy2 = gameController.getStrategySelector().getStrategy((PersonModel) person2.getUserData());
            MovementStrategy movementStrategy;
            if (movementStrategy1.getPriority() > movementStrategy2.getPriority())
                movementStrategy = movementStrategy1;
            else
                movementStrategy = movementStrategy2;
            movementStrategy.collisionEndPersonPersonInPlatform(person1, person2, ((PersonModel) person1.getUserData()).getSide());
        }
    }


    private void checkIfEndPlatformElevatorCollision(Body bodyA, Body bodyB) {
        if (bodyA.getUserData() instanceof PlatformModel && bodyB.getUserData() instanceof ElevatorModel) {
            gameController.getElevatorController().handlePlatformElevatorCollision(bodyA, bodyB, bodyB.getLinearVelocity().y > 0);
        } else if (bodyA.getUserData() instanceof ElevatorModel && bodyB.getUserData() instanceof PlatformModel) {
            gameController.getElevatorController().handlePlatformElevatorCollision(bodyB, bodyA, bodyA.getLinearVelocity().y > 0);
        }
    }


    public void preSolve(Contact contact, Manifold oldManifold) {

    }


    public void postSolve(Contact contact, ContactImpulse impulse) {

    }
}
