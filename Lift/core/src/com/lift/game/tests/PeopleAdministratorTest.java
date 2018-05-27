package com.lift.game.tests;

import com.lift.game.controller.GameController;
import com.lift.game.controller.PeopleAdministrator;
import com.lift.game.controller.PeopleGenerator;
import com.lift.game.controller.entities.PersonBody;
import com.lift.game.model.GameModel;
import com.lift.game.model.entities.EntityModel;
import com.lift.game.model.entities.person.PersonModel;
import com.lift.game.model.entities.person.PersonState;
import com.lift.game.model.entities.person.Side;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class PeopleAdministratorTest {

    @Test
    public void movePeopleOut() {
        GameController gameController = new GameController(new GameModel());
        PersonModel model = new PersonModel(0, 0, 1, Side.Left, 2, 1);
        gameController.addPerson(new PersonBody(gameController.getWorld(), model));
        model.setPersonState(PersonState.InElevator);
        gameController.getGameModel().getElevator(Side.Left).setTarget_floor(2);
        gameController.getPeopleAdministrator().deliverPeople(2, Side.Left);
        assertEquals(1, gameController.getPeopleAdministrator().getReachedPeople().size());
        gameController.getPeopleAdministrator().movePeopleOut();
        assertEquals(0, gameController.getPeopleAdministrator().getReachedPeople().size());

    }

    @Test
    public void updatePositionWhenReached() {
        GameController gameController = new GameController(new GameModel());
        gameController.addPerson(new PersonBody(gameController.getWorld(), new PersonModel(0,0,1,Side.Left,2,1)));
        PersonBody personBody = gameController.getPeople().get(0);
        gameController.getPeopleAdministrator().updatePositionWhenReached(Side.Left, personBody);
        assertEquals(gameController.getElevator(Side.Left).getX(),personBody.getX(), 0.01);
        assertEquals(gameController.getElevator(Side.Left).getY(),personBody.getY(), 0.01);
        assertEquals(-10,personBody.getBody().getLinearVelocity().x, 0.01);
        assertEquals(2,personBody.getBody().getLinearVelocity().y, 0.01);
    }

    @Test
    public void enterTheElevator() {
        GameController gameController = new GameController(new GameModel());
        gameController.addPerson(new PersonBody(gameController.getWorld(), new PersonModel(0,0,1,Side.Left,2,1)));
        gameController.addPerson(new PersonBody(gameController.getWorld(), new PersonModel(0,0,1,Side.Left,2,1)));
        gameController.addPerson(new PersonBody(gameController.getWorld(), new PersonModel(0,0,1,Side.Left,2,1)));
        gameController.addPerson(new PersonBody(gameController.getWorld(), new PersonModel(0,0,1,Side.Left,2,1)));
        gameController.addPerson(new PersonBody(gameController.getWorld(), new PersonModel(0,0,2,Side.Left,2,1)));

        int expected[] = {0, 1, 2, 3, 3, 3};
        ArrayList<Integer> res = new ArrayList<Integer>();
        res.add(nPeopleInElevator(gameController));
        for(PersonBody personBody : gameController.getPeople()) {
            gameController.getPeopleAdministrator().enterTheElevator(personBody.getBody(), ((PersonModel) personBody.getBody().getUserData()));
            res.add(nPeopleInElevator(gameController));
        }
    }

    public int nPeopleInElevator(GameController gameController) {
        int count = 0;
        for (PersonModel personModel: gameController.getGameModel().getPeople()) {
            if(personModel.getPersonState() == PersonState.InElevator)
                count++;
        }
        return count;
    }

    @Test
    public void moveToFreeFly() {
        GameController gameController = new GameController(new GameModel());
        PersonModel personModel = new PersonModel(0, 0, 1, Side.Left, 2, 1);
        personModel.setPersonState(PersonState.Waiting);
        gameController.getPeopleAdministrator().moveToFreeFly(personModel);
        assertEquals(PersonState.FreeFalling, personModel.getPersonState());
        personModel.setPersonState(PersonState.GiveUP);
        gameController.getPeopleAdministrator().moveToFreeFly(personModel);
        assertEquals(PersonState.FreeFalling, personModel.getPersonState());
    }

    @Test
    public void freeSpaceInPlatform() {
        GameController gameController = new GameController(new GameModel());
        PersonModel personModel = new PersonModel(0, 0, 1, Side.Left, 2, 1);
        gameController.getGameModel().addPerson(personModel);
        assertEquals(1, gameController.getGameModel().getLeft_floors().get(personModel.getFloor()).getNumber_of_people());
        gameController.getPeopleAdministrator().freeSpaceInPlatform(personModel);
        assertEquals(0, gameController.getGameModel().getLeft_floors().get(personModel.getFloor()).getNumber_of_people());

    }


    @Test
    public void deliverPeople() {
        GameController gameController = new GameController(new GameModel());
        PersonModel model = new PersonModel(0, 0, 1, Side.Left, 2, 1);
        gameController.addPerson(new PersonBody(gameController.getWorld(), model));
        model.setPersonState(PersonState.InElevator);
        gameController.getGameModel().getElevator(Side.Left).setTarget_floor(2);
        gameController.getPeopleAdministrator().deliverPeople(2, Side.Left);
        assertEquals(1, gameController.getPeopleAdministrator().getReachedPeople().size());
        model = new PersonModel(0, 0, 1, Side.Right, 2, 1);
        gameController.addPerson(new PersonBody(gameController.getWorld(), model));
        gameController.getPeopleAdministrator().deliverPeople(2, Side.Left);
        assertEquals(1, gameController.getPeopleAdministrator().getReachedPeople().size());
        model = new PersonModel(0, 0, 1, Side.Left, 1, 1);
        gameController.addPerson(new PersonBody(gameController.getWorld(), model));
        gameController.getPeopleAdministrator().deliverPeople(2, Side.Left);
        assertEquals(1, gameController.getPeopleAdministrator().getReachedPeople().size());
    }

    @Test
    public void increaseDifficulty() {
        GameController gameController = new GameController(new GameModel());
        while(Math.round(gameController.getPeopleAdministrator().getDifficultyFactor()* 10) /10f < PeopleAdministrator.MAX_DF) {
            Float sbp = Math.round(gameController.getPeopleAdministrator().getDifficultyFactor() * 10) / 10f;
            gameController.getPeopleAdministrator().increaseDifficulty();
            assertEquals(PeopleAdministrator.DF_DELTA, sbp - Math.round(gameController.getPeopleAdministrator().getDifficultyFactor()* 10) /10f, 0.01f);
        }
        gameController.getPeopleAdministrator().increaseDifficulty();
        assertEquals(PeopleGenerator.MIN_SBP, Math.round(gameController.getPeopleAdministrator().getDifficultyFactor()* 10) /10f, 0.01f);
    }
}