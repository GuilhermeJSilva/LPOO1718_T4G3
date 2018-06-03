package com.lift.tests.controller;

import com.lift.game.controller.GameController;
import com.lift.game.model.GameModel;
import com.lift.game.model.Side;
import org.junit.Test;

import static com.lift.game.controller.controllers.PlatformController.JUMP_HEIGHT;
import static com.lift.game.controller.entities.ScreenSensorBody.BOTTOM_SENSOR;
import static org.junit.Assert.*;

public class PlatformControllerTest {

    @Test
    public void handleCollision() {
        GameModel gameModel = new GameModel();
        GameController gameController = new GameController(gameModel);
        assertEquals(0, (long)gameController.getPlatformController().nPlatformsToJump());
        gameController.getPlatformController().handleCollision(gameController.getFloors(Side.Right).get(0).getBody(), (short)0);
        assertEquals(0, (long)gameController.getPlatformController().nPlatformsToJump());
        gameController.getPlatformController().handleCollision(gameController.getFloors(Side.Right).get(0).getBody(), BOTTOM_SENSOR);
        assertEquals(1, (long)gameController.getPlatformController().nPlatformsToJump());

    }

    @Test
    public void update() {

        GameModel gameModel = new GameModel();
        GameController gameController = new GameController(gameModel);
        gameController.getPlatformController().handleCollision(gameController.getFloors(Side.Right).get(0).getBody(), BOTTOM_SENSOR);
        assertEquals(1, (long)gameController.getPlatformController().nPlatformsToJump());
        gameController.getPlatformController().update();
        assertEquals(JUMP_HEIGHT, gameController.getFloors(Side.Right).get(0).getBody().getPosition().y,0.01);
        assertEquals(0, (long)gameController.getPlatformController().nPlatformsToJump());

    }
}