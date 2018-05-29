package com.lift.game.controller.controllers;

import com.badlogic.gdx.physics.box2d.Body;
import com.lift.game.controller.GameController;
import com.lift.game.model.Side;
import com.lift.game.model.entities.ElevatorModel;
import com.lift.game.model.entities.PlatformModel;

/**
 * Controls the elevator movement.
 */
public class ElevatorController {
    /**
     * Controls the elevators in this game.
     */
    private GameController gameController;

    /**
     * Constructs the controller.
     * @param gameController Main controller.
     */
    public ElevatorController(GameController gameController) {
        this.gameController = gameController;
    }

    /**
     * Handles a collision between a platform and an elevator sensor.
     * @param platformBody Collision body number 1.
     * @param elevatorBody Collision body number 2.
     * @param b Boolean condition to stop.
     */
    public void handlePlatformElevatorCollision(Body platformBody, Body elevatorBody, boolean b) {
        PlatformModel pm = (PlatformModel) platformBody.getUserData();
        ElevatorModel em = (ElevatorModel) elevatorBody.getUserData();


        if (em == gameController.getGameModel().getElevator(Side.Left)) {
            if (em.getTarget_floor() == gameController.getGameModel().getLeft_floors().indexOf(pm) && b) {
                elevatorBody.setLinearVelocity(0, platformBody.getLinearVelocity().y);
                gameController.getPeopleAdministrator().deliverPeople(em.getTarget_floor(), Side.Left);

            }
        } else {
            if (em.getTarget_floor() == gameController.getGameModel().getRight_floors().indexOf(pm) && b) {
                elevatorBody.setLinearVelocity(0, platformBody.getLinearVelocity().y);
                gameController.getPeopleAdministrator().deliverPeople(em.getTarget_floor(), Side.Right);

            }
        }
    }
}