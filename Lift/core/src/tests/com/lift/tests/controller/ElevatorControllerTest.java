package com.lift.tests.controller;

import com.lift.game.controller.GameController;
import com.lift.game.model.GameModel;
import com.lift.game.model.Side;
import com.lift.tests.GameTester;

import org.junit.Test;

import static com.lift.game.controller.entities.ScreenSensorBody.BOTTOM_SENSOR;
import static org.junit.Assert.*;

public class ElevatorControllerTest extends GameTester{

    @Test
    public void handlePlatformElevatorCollision() {
        GameModel gameModel = new GameModel();
        GameController gameController = new GameController(gameModel);
        handlePlatformElevatorCollisionSide(gameModel, gameController, Side.Left);
        handlePlatformElevatorCollisionSide(gameModel, gameController, Side.Right);

    }

    public void handlePlatformElevatorCollisionSide(GameModel gameModel, GameController gameController, Side side) {
        gameController.getElevator(side).getBody().setLinearVelocity(1,1);
        gameController.getElevator(side).setTarget_floor(1);
        gameModel.getElevator(side).setTarget_floor(1);
        gameController.getElevatorController().handlePlatformElevatorCollision(gameController.getFloors(side).get(1).getBody(), gameController.getElevator(side).getBody(), true);
        assertEquals(true, gameModel.getElevator(side).getStopped());
        assertEquals(gameController.getFloors(side).get(1).getBody().linVelLoc.y, gameController.getElevator(side).getBody().linVelLoc.y, 0.01);
    }
    @Test
    public void handleCollision() {
        GameModel gameModel = new GameModel();
        GameController gameController = new GameController(gameModel);
        handleCollisionSide(gameModel, gameController, Side.Left);
        handleCollisionSide(gameModel, gameController, Side.Right);
    }

    private void handleCollisionSide(GameModel gameModel, GameController gameController, Side side) {
        gameController.getElevator(side).getBody().setLinearVelocity(1,1);
        gameController.getElevator(side).setTarget_floor(1);
        gameModel.getElevator(side).setTarget_floor(1);
        gameController.getElevatorController().handleCollision(gameController.getElevator(side).getBody(), BOTTOM_SENSOR);
        assertEquals(-1, gameModel.getElevator(side).getTarget_floor());
        assertEquals(0, gameController.getElevator(side).getBody().getLinearVelocity().y, 0.01);
    }
}