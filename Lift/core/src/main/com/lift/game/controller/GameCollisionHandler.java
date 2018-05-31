package com.lift.game.controller;

import com.badlogic.gdx.physics.box2d.*;
import com.lift.game.controller.entities.pstrategies.MovementStrategy;
import com.lift.game.controller.powerups.PowerUpState;
import com.lift.game.model.entities.ElevatorModel;
import com.lift.game.model.entities.PlatformModel;
import com.lift.game.model.entities.PowerUpModel;
import com.lift.game.model.entities.person.PersonModel;

/**
 * Collision handler for Box2D
 */
class  GameCollisionHandler implements ContactListener {

    /**
     * Access to sub controllers.
     */
    private final GameController gameController;

    /**
     * Creates the collision handler.
     * @param gameController Master game controller.
     */
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

    /**
     * Checks if an elevator hit the sensor.
     * @param contact Contact to check.
     */
    private void checkIfElevatorSensorCollision(Contact contact) {
        Body bodyA = contact.getFixtureA().getBody();
        Body bodyB = contact.getFixtureB().getBody();
        if (bodyA.getUserData() instanceof ElevatorModel && bodyB.getUserData() == null) {
            gameController.getElevatorController().handleCollision(bodyA, contact.getFixtureB().getFilterData().categoryBits);
        } else if (bodyA.getUserData() == null && bodyB.getUserData() instanceof ElevatorModel) {
            gameController.getElevatorController().handleCollision(bodyB, contact.getFixtureA().getFilterData().categoryBits);
        }
    }

    /**
     * Verifies if a platform hit the sensor.
     * @param contact Contact to check.
     */
    private void checkIfPlatformSensorCollision(Contact contact) {
        Body bodyA = contact.getFixtureA().getBody();
        Body bodyB = contact.getFixtureB().getBody();
        if (bodyA.getUserData() instanceof PlatformModel && bodyB.getUserData() == null) {
            gameController.getPlatformController().handleCollision(bodyA, contact.getFixtureB().getFilterData().categoryBits);
        } else if (bodyA.getUserData() == null && bodyB.getUserData() instanceof PlatformModel) {
            gameController.getPlatformController().handleCollision(bodyB, contact.getFixtureA().getFilterData().categoryBits);
        }
    }

    /**
     * Checks if a power up collided with an elevator.
     * @param bodyA One of the bodies that collided.
     * @param bodyB One of the bodies that collided.
     */
    private void checkIfPowerUpCollision(Body bodyA, Body bodyB) {
        if (bodyA.getUserData() instanceof PowerUpModel && bodyB.getUserData() instanceof ElevatorModel) {
            pickUpPowerUp(bodyA);
        } else if (bodyA.getUserData() instanceof ElevatorModel && bodyB.getUserData() instanceof PowerUpModel) {
            pickUpPowerUp(bodyB);
        }
    }

    /**
     * If a power up is waiting for a collision, it changes its state.
     * @param powerUpBody Physical body of the power up.
     */
    private void pickUpPowerUp(Body powerUpBody) {
        PowerUpModel powerUpModel = (PowerUpModel) powerUpBody.getUserData();
        if (powerUpModel.getPowerUpState() == PowerUpState.Waiting) {
            powerUpModel.setPowerUpState(PowerUpState.PickedUp);
        }
    }

    /**
     * Checks if a person collided with another.
     * @param bodyA One of the bodies that collided.
     * @param bodyB One of the bodies that collided.
     */
    private void checkIfPersonPersonCollision(Body bodyA, Body bodyB) {
        if (bodyA.getUserData() instanceof PersonModel && bodyB.getUserData() instanceof PersonModel) {
            gameController.getStrategySelector().getStrategy((PersonModel) bodyA.getUserData(), (PersonModel) bodyB.getUserData()).collisionPersonPersonInPlatform(bodyA, bodyB);
        }
    }

    /**
     * Verifies if a person collided with a platform.
     * @param contact Physical contact.
     * @param bodyA One of the bodies that collided.
     * @param bodyB One of the bodies that collided.
     */
    private void checkIfPlatformPersonCollision(Contact contact, Body bodyA, Body bodyB) {
        if (bodyA.getUserData() instanceof PlatformModel && bodyB.getUserData() instanceof PersonModel) {
            solvePersonPlatformCollision(bodyB, bodyA, contact.getFixtureA().getFilterData().categoryBits);
        } else if (bodyA.getUserData() instanceof PersonModel && bodyB.getUserData() instanceof PlatformModel) {
            solvePersonPlatformCollision(bodyA, bodyB, contact.getFixtureB().getFilterData().categoryBits);
        }
    }

    /**
     * Solves a collision between a person and a platform.
     * @param personBody Body of the person.
     * @param platformBody Body of the platform.
     * @param platformFixture Category bits of the platform fixture.
     */
    private void solvePersonPlatformCollision(Body personBody, Body platformBody, int platformFixture) {
        MovementStrategy movementStrategy = gameController.getStrategySelector().getStrategy((PersonModel) personBody.getUserData());
        movementStrategy.solvePersonPlatformCollision(personBody, platformBody, platformFixture);
    }

    /**
     * Verifies if the collision is between a platform and an elevator.
     * @param bodyA One of the bodies that collided.
     * @param bodyB One of the bodies that collided.
     */
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

    /**
     * Checks if a collision between a power up and an elevator ended.
     * @param bodyA One of the bodies that collided.
     * @param bodyB One of the bodies that collided.
     */
    private void checkIfEndPowerUP(Body bodyA, Body bodyB) {
        if (bodyA.getUserData() instanceof PowerUpModel && bodyB.getUserData() instanceof ElevatorModel) {
            dropPowerUp(bodyA);
        } else if (bodyA.getUserData() instanceof ElevatorModel && bodyB.getUserData() instanceof PowerUpModel) {
            dropPowerUp(bodyB);
        }
    }

    /**
     * Solves a the end of a collision between a power up and an elevator.
     * @param powerUpBody Body of the power up.
     */
    private void dropPowerUp(Body powerUpBody) {
        PowerUpModel powerUpModel = (PowerUpModel) powerUpBody.getUserData();
        if (powerUpModel.getPowerUpState() == PowerUpState.PickedUp) {
            powerUpModel.setPowerUpState(PowerUpState.Waiting);
        }
    }

    /**
     * Verifies if a collision between two people ended.
     * @param person1 One of the bodies that collided.
     * @param person2 One of the bodies that collided.
     */
    private void checkIfEndPersonPersonCollision(Body person1, Body person2) {
        if (person1.getUserData() instanceof PersonModel && person2.getUserData() instanceof PersonModel) {
            gameController.getStrategySelector().getStrategy((PersonModel) person1.getUserData(), (PersonModel) person2.getUserData()).collisionEndPersonPersonInPlatform(person1, person2, ((PersonModel) person1.getUserData()).getSide());
        }
    }

    /**
     * Verifies if a platform end its collision with an elevator.
     * @param bodyA One of the bodies that collided.
     * @param bodyB One of the bodies that collided.
     */
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
