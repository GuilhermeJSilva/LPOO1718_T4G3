package com.lift.tests.controller;

import com.lift.game.controller.GameController;
import com.lift.game.controller.powerups.types.CoinPU;
import com.lift.game.model.GameModel;
import com.lift.game.model.Side;
import com.lift.game.model.entities.PowerUpModel;
import org.junit.Test;

import static com.lift.game.controller.powerups.types.CoinPU.COINS;
import static org.junit.Assert.*;

public class CoinPUTest {

    @Test
    public void pickup() {
        GameModel gameModel =  new GameModel();
        GameController gameController = new GameController(gameModel);
        CoinPU coinPU = new CoinPU(new PowerUpModel(0,0,Side.Left), gameController.getWorld());

        Integer coins = gameModel.getCoins();

        coinPU.pickup(gameController);

        assertEquals(coins + COINS, (long)gameModel.getCoins());
    }
}