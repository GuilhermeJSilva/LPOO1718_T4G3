package com.lift.game.tests;

import static org.junit.Assert.*;

import com.lift.game.controller.GameController;
import com.lift.game.controller.PeopleGenerator;
import com.lift.game.model.GameModel;
import com.lift.game.model.entities.person.PersonModel;
import com.lift.game.model.entities.person.Side;
import org.junit.Assert;
import org.junit.Test;

public class PeopleGeneratorTest extends GameTester{

    @Test
    public void generatePeople() {
        GameController gameController = new GameController();
        PeopleGenerator peopleGenerator =  new PeopleGenerator(gameController);
        peopleGenerator.generatePeople(2);
        assertEquals(2, gameController.getPeople().size());
        peopleGenerator.generatePeople(1);
        assertEquals(3, gameController.getPeople().size());
        
        GameModel.resetModel();
    }

    @Test
    public void generatePerson() {
        PeopleGenerator peopleGenerator =  new PeopleGenerator(new GameController());
        for (int i = 0; i < 6; i++) {
            peopleGenerator.generatePerson(i);
            boolean fail = true;
            for (PersonModel personModel : GameModel.getInstance().getPeople()) {
                if(personModel.getFloor() == i)
                    fail = false;
            }
            if(fail)
                fail("Not generated in th right floor");
        }

        
        GameModel.resetModel();
    }

    @Test
    public void generateNewPeople() {
        PeopleGenerator peopleGenerator =  new PeopleGenerator(new GameController());
        peopleGenerator.generateNewPeople(0);
        assertEquals(0,GameModel.getInstance().getPeople().size());
        peopleGenerator.generateNewPeople(peopleGenerator.getSeconds_b_person());
        assertEquals(1,GameModel.getInstance().getPeople().size());
        peopleGenerator.generateNewPeople(2 * peopleGenerator.getSeconds_b_person());
        assertEquals(3,GameModel.getInstance().getPeople().size());
        
        GameModel.resetModel();

    }

    @Test
    public void add_waiting_person() {
        PeopleGenerator peopleGenerator =  new PeopleGenerator(new GameController());
        peopleGenerator.add_waiting_person(0,1,Side.Left);
        PersonModel personModel = GameModel.getInstance().getPeople().get(0);
        assertEquals(0, personModel.getFloor());
        assertEquals(1L, personModel.getDestination().longValue());
        assertEquals(Side.Left, personModel.getSide());
        
        GameModel.resetModel();
    }

    @Test
    public void increaseDifficulty() {
        PeopleGenerator peopleGenerator =  new PeopleGenerator(new GameController());
        while(Math.round(peopleGenerator.getSeconds_b_person()* 10) /10f > PeopleGenerator.MIN_SBP) {
            Float sbp = Math.round(peopleGenerator.getSeconds_b_person() * 10) / 10f;
            peopleGenerator.increaseDifficulty();
            assertEquals(PeopleGenerator.SBP_DELTA, sbp - Math.round(peopleGenerator.getSeconds_b_person()* 10) /10f, 0.01f);
        }
        peopleGenerator.increaseDifficulty();
        assertEquals(PeopleGenerator.MIN_SBP, Math.round(peopleGenerator.getSeconds_b_person()* 10) /10f, 0.01f);
    }
}
