package com.lift.tests.controller;

import com.lift.game.controller.GameController;
import com.lift.game.controller.entities.pstrategies.DrunkenMovement;
import com.lift.game.controller.entities.pstrategies.NullStrategy;
import com.lift.game.controller.entities.pstrategies.RegularMovement;
import com.lift.game.model.GameModel;
import com.lift.game.model.Side;
import com.lift.game.model.entities.person.PersonModel;
import com.lift.game.model.entities.person.PersonType;
import org.junit.Test;

import static org.junit.Assert.*;

public class StrategySelectorTest {

    @Test(timeout = 100)
    public void getStrategy() {
        GameModel gameModel = new GameModel();
        GameController gameController = new GameController(gameModel);
        boolean reg = false;
        boolean drunk = false;

        while (!reg && !drunk) {
            PersonModel personModel = new PersonModel(0, 0, 0, Side.Left, 1, 0);
            if (personModel.getPersonType() == PersonType.Regular) {
                reg = true;
                assertTrue(gameController.getStrategySelector().getStrategy(personModel) instanceof RegularMovement);
            }

            if (personModel.getPersonType() == PersonType.Drunken) {
                drunk = true;
                assertTrue(gameController.getStrategySelector().getStrategy(personModel) instanceof DrunkenMovement);
            }
        }

        assertTrue(gameController.getStrategySelector().getStrategy(null) instanceof NullStrategy);
    }

    @Test(timeout = 100)
    public void getStrategy1() {
        GameModel gameModel = new GameModel();
        GameController gameController = new GameController(gameModel);

        boolean reg = false;
        boolean drunk = false;
        boolean reg_drunk = false;
        boolean reg_drunk2 = false;

        while (!reg && !drunk &&  !reg_drunk && !reg_drunk2) {
            PersonModel personModel = new PersonModel(0, 0, 0, Side.Left, 1, 0);
            PersonModel personModel1 = new PersonModel(0, 0, 0, Side.Left, 1, 0);

            if (personModel.getPersonType() == PersonType.Regular && personModel1.getPersonType() == PersonType.Drunken) {
                reg_drunk = true;
                assertTrue(gameController.getStrategySelector().getStrategy(personModel, personModel1) instanceof DrunkenMovement);
            }

            if (personModel.getPersonType() == PersonType.Drunken && personModel1.getPersonType() == PersonType.Regular) {
                reg_drunk2 = true;
                assertTrue(gameController.getStrategySelector().getStrategy(personModel, personModel1) instanceof DrunkenMovement);
            }

            if (personModel.getPersonType() == PersonType.Regular && personModel1.getPersonType() == PersonType.Regular) {
                reg = true;
                assertTrue(gameController.getStrategySelector().getStrategy(personModel, personModel1) instanceof RegularMovement);
            }

            if (personModel.getPersonType() == PersonType.Drunken && personModel1.getPersonType() == PersonType.Drunken) {
                drunk = true;
                assertTrue(gameController.getStrategySelector().getStrategy(personModel, personModel1) instanceof DrunkenMovement);
            }
        }
    }
}