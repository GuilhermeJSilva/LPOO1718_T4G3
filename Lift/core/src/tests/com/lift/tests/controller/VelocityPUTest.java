package com.lift.tests.controller;

import com.lift.game.controller.GameController;
import com.lift.game.controller.powerups.types.CoinPU;
import com.lift.game.controller.powerups.types.VelocityPU;
import com.lift.game.model.GameModel;
import com.lift.game.model.Side;
import com.lift.game.model.entities.PowerUpModel;
import org.junit.Test;

import static com.lift.game.controller.entities.ElevatorBody.vy;
import static com.lift.game.controller.powerups.types.VelocityPU.MULTIPLIER_INCREMENT;
import static org.junit.Assert.*;

public class VelocityPUTest {


    @Test
    public void pickup() {
        GameModel gameModel =  new GameModel();
        GameController gameController = new GameController(gameModel);
        VelocityPU velocityPU = new VelocityPU(new PowerUpModel(0,0,Side.Left), gameController.getWorld());

        assertEquals(vy, gameController.getElevator(Side.Left).getVelocity(), 0.01);
        assertEquals(vy, gameController.getElevator(Side.Right).getVelocity(), 0.01);
        velocityPU.pickup(gameController);
        assertEquals(vy * (1 + MULTIPLIER_INCREMENT), gameController.getElevator(Side.Left).getVelocity(), 0.01);
        assertEquals(vy * (1 + MULTIPLIER_INCREMENT), gameController.getElevator(Side.Right).getVelocity(), 0.01);
        velocityPU.end(gameController);
        assertEquals(vy, gameController.getElevator(Side.Left).getVelocity(), 0.01);
        assertEquals(vy, gameController.getElevator(Side.Right).getVelocity(), 0.01);
    }
}