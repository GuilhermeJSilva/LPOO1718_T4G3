package com.lift.tests.controller;

import com.lift.game.controller.GameController;
import com.lift.game.controller.powerups.PowerUpState;
import com.lift.game.controller.powerups.types.LifePU;
import com.lift.game.model.GameModel;
import com.lift.game.model.Side;
import com.lift.game.model.entities.PowerUpModel;
import org.junit.Test;

import static org.junit.Assert.*;

public class BasicPowerUPTest {

    @Test
    public void update() {
        GameModel gameModel =  new GameModel();
        GameController gameController = new GameController(gameModel);
        LifePU lifePU = new LifePU(new PowerUpModel(0,0,Side.Left), gameController.getWorld());
        assertEquals(3l, (long)gameModel.getLives());
        assertEquals(PowerUpState.Waiting, lifePU.getPowerUpState());
        lifePU.update(gameController, LifePU.TIME_TO_DISAPPEAR);
        assertEquals(PowerUpState.Done, lifePU.getPowerUpState());

        LifePU lifePU2 = new LifePU(new PowerUpModel(0,0,Side.Left), gameController.getWorld());
        assertEquals(PowerUpState.Waiting, lifePU2.getPowerUpState());
        lifePU2.setPowerUpState(PowerUpState.PickedUp);
        lifePU.update(gameController, LifePU.TIME_TO_DISAPPEAR);
        assertEquals(PowerUpState.PickedUp, lifePU2.getPowerUpState());

        gameModel.decrementLives();
        lifePU2.setPowerUpState(PowerUpState.PickedUp);
        lifePU2.update(gameController, LifePU.TIME_TO_DISAPPEAR);
        assertEquals(PowerUpState.Done, lifePU2.getPowerUpState());

    }
}