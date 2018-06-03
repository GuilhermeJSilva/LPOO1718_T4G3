package com.lift.tests.controller;

import com.badlogic.gdx.math.Vector2;
import com.lift.game.controller.GameController;
import com.lift.game.controller.powerups.types.LifePU;
import com.lift.game.model.GameModel;
import com.lift.game.model.Side;
import com.lift.game.model.entities.PowerUpModel;
import org.junit.Test;

import static org.junit.Assert.*;

public class LifePUTest {

    @Test
    public void pickup() {
        GameModel gameModel =  new GameModel();
        GameController gameController = new GameController(gameModel);
        LifePU lifePU = new LifePU(new PowerUpModel(new Vector2(0,0),Side.Left), gameController.getWorld());
        assertEquals(3l, (long)gameModel.getLives());
        assertFalse(lifePU.pickup(gameController));
        assertEquals(3l, (long)gameModel.getLives());
        gameModel.decrementLives();
        assertEquals(2l, (long)gameModel.getLives());
        assertTrue(lifePU.pickup(gameController));
        assertEquals(3l, (long)gameModel.getLives());
    }
}