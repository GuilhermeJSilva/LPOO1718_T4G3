package com.lift.tests.controller;

import com.badlogic.gdx.math.Vector2;
import com.lift.game.controller.GameController;
import com.lift.game.controller.entities.PersonBody;
import com.lift.game.controller.entities.pstrategies.NullStrategy;
import com.lift.game.model.GameModel;
import com.lift.game.model.Side;
import com.lift.game.model.entities.person.PersonModel;
import com.lift.game.model.entities.person.PersonState;
import org.junit.Test;

import static com.lift.game.controller.entities.PlatformBody.PLATFORM_ELEVATOR_SENSOR;
import static org.junit.Assert.*;

public class NullStrategyTest {

    @Test
    public void solvePersonPlatformCollision() {
        GameModel gameModel = new GameModel();
        GameController gameController = new GameController(gameModel);
        NullStrategy nullStrategy = new NullStrategy(gameController);
        PersonModel personModel = new PersonModel(new Vector2(0,0), 0, Side.Left, 1, 0);
        gameModel.addPerson(personModel);
        gameController.addPerson(new PersonBody(gameController.getWorld(), personModel));
        gameModel.getLeft_floors().get(0).incrementNPeople();
        assertEquals(1, gameModel.getLeft_floors().get(0).getNumber_of_people());
        personModel.setPersonState(PersonState.Reached);
        nullStrategy.solvePersonPlatformCollision(gameController.getPeople().get(0).getBody(), gameController.getFloors(Side.Left).get(0).getBody(), PLATFORM_ELEVATOR_SENSOR);
        assertEquals(1, gameModel.getLeft_floors().get(0).getNumber_of_people());

        personModel.setPersonState(PersonState.InElevator);
        nullStrategy.solvePersonPlatformCollision(gameController.getPeople().get(0).getBody(), gameController.getFloors(Side.Left).get(0).getBody(), PLATFORM_ELEVATOR_SENSOR);
        assertEquals(1, gameModel.getLeft_floors().get(0).getNumber_of_people());

        personModel.setPersonState(PersonState.FreeFalling);
        nullStrategy.solvePersonPlatformCollision(gameController.getPeople().get(0).getBody(), gameController.getFloors(Side.Left).get(0).getBody(), PLATFORM_ELEVATOR_SENSOR);
        assertEquals(1, gameModel.getLeft_floors().get(0).getNumber_of_people());

        personModel.setPersonState(PersonState.Waiting);
        nullStrategy.solvePersonPlatformCollision(gameController.getPeople().get(0).getBody(), gameController.getFloors(Side.Left).get(0).getBody(), (short) 0);
        assertEquals(1, gameModel.getLeft_floors().get(0).getNumber_of_people());

        personModel.setPersonState(PersonState.Waiting);
        nullStrategy.solvePersonPlatformCollision(gameController.getPeople().get(0).getBody(), gameController.getFloors(Side.Left).get(0).getBody(), PLATFORM_ELEVATOR_SENSOR);
        assertEquals(0, gameModel.getLeft_floors().get(0).getNumber_of_people());
    }

    @Test
    public void giveUp() {
        GameModel gameModel = new GameModel();
        GameController gameController = new GameController(gameModel);
        NullStrategy nullStrategy = new NullStrategy(gameController);
        PersonModel personModel = new PersonModel(new Vector2(0,0), 0, Side.Left, 1, 0);
        gameModel.addPerson(personModel);
        gameController.addPerson(new PersonBody(gameController.getWorld(), personModel));
        gameModel.getElevator(Side.Left).incrementOccupancy();
        gameModel.addPerson(personModel);
        gameController.addPerson(new PersonBody(gameController.getWorld(), personModel));
        gameModel.getElevator(Side.Left).incrementOccupancy();
        gameModel.addPerson(personModel);
        gameController.addPerson(new PersonBody(gameController.getWorld(), personModel));
        gameModel.getElevator(Side.Left).incrementOccupancy();

        assertFalse(gameModel.getElevator(Side.Left).isThereSpaceFree());

        personModel.setPersonState(PersonState.Reached);
        nullStrategy.giveUp(gameController.getPeople().get(0), Side.Left);
        assertNotEquals(gameController.getElevator(Side.Left).getBody().getPosition().x, gameController.getPeople().get(0).getBody().getPosition().x, 0.01);
        assertNotEquals(gameController.getElevator(Side.Left).getBody().getPosition().y, gameController.getPeople().get(0).getBody().getPosition().y, 0.01);
        assertFalse(gameModel.getElevator(Side.Left).isThereSpaceFree());

        personModel.setPersonState(PersonState.Waiting);
        nullStrategy.giveUp(gameController.getPeople().get(0), Side.Left);
        assertNotEquals(gameController.getElevator(Side.Left).getBody().getPosition().x, gameController.getPeople().get(0).getBody().getPosition().x, 0.01);
        assertNotEquals(gameController.getElevator(Side.Left).getBody().getPosition().y, gameController.getPeople().get(0).getBody().getPosition().y, 0.01);
        assertFalse(gameModel.getElevator(Side.Left).isThereSpaceFree());

        personModel.setPersonState(PersonState.InElevator);
        nullStrategy.giveUp(gameController.getPeople().get(0), Side.Left);
        assertEquals(gameController.getElevator(Side.Left).getBody().getPosition().x, gameController.getPeople().get(0).getBody().getPosition().x, 0.01);
        assertEquals(gameController.getElevator(Side.Left).getBody().getPosition().y, gameController.getPeople().get(0).getBody().getPosition().y, 0.01);
        assertTrue(gameModel.getElevator(Side.Left).isThereSpaceFree());
    }

}