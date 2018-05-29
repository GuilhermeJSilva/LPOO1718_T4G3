package com.lift.game.controller;

import com.lift.game.GameTester;
import com.lift.game.controller.controllers.PeopleGenerator;
import com.lift.game.model.GameModel;
import com.lift.game.model.entities.person.PersonModel;
import com.lift.game.model.Side;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class PeopleGeneratorTest extends GameTester {

    @Test
    public void generatePeople() {
        GameModel gameModel =  new GameModel();
        GameController gameController = new GameController(gameModel);
        com.lift.game.controller.controllers.PeopleGenerator peopleGenerator =  new com.lift.game.controller.controllers.PeopleGenerator(gameController);
        peopleGenerator.generatePeople(2);
        assertEquals(2, gameController.getPeople().size());
        peopleGenerator.generatePeople(1);
        assertEquals(3, gameController.getPeople().size());
        
        
    }

    @Test
    public void generatePerson() {
        GameModel gameModel =  new GameModel();
        com.lift.game.controller.controllers.PeopleGenerator peopleGenerator =  new com.lift.game.controller.controllers.PeopleGenerator(new GameController(gameModel));
        for (int i = 0; i < 6; i++) {
            peopleGenerator.generatePerson(i);
            boolean fail = true;
            for (PersonModel personModel : gameModel.getPeople()) {
                if(personModel.getFloor() == i) {
                    fail = false;
                }
            }
            if(fail) {
                fail("Not generated in th right floor");
            }
        }

        
        
    }

    @Test
    public void generateNewPeople() {
        GameModel gameModel =  new GameModel();

        com.lift.game.controller.controllers.PeopleGenerator peopleGenerator =  new com.lift.game.controller.controllers.PeopleGenerator(new GameController(gameModel));
        peopleGenerator.generateNewPeople(0);
        assertEquals(0,gameModel.getPeople().size());
        peopleGenerator.generateNewPeople(peopleGenerator.getSeconds_b_person());
        assertEquals(1,gameModel.getPeople().size());
        peopleGenerator.generateNewPeople(2 * peopleGenerator.getSeconds_b_person());
        assertEquals(3,gameModel.getPeople().size());
        
        

    }

    @Test
    public void add_waiting_person() {
        GameModel gameModel =  new GameModel();

        com.lift.game.controller.controllers.PeopleGenerator peopleGenerator =  new com.lift.game.controller.controllers.PeopleGenerator(new GameController(gameModel));
        peopleGenerator.add_waiting_person(0,1,Side.Left);
        PersonModel personModel = gameModel.getPeople().get(0);
        assertEquals(0, personModel.getFloor());
        assertEquals(1L, personModel.getDestination().longValue());
        assertEquals(Side.Left, personModel.getSide());
        
        
    }

    @Test
    public void increaseDifficulty() {
        GameModel gameModel =  new GameModel();

        com.lift.game.controller.controllers.PeopleGenerator peopleGenerator =  new com.lift.game.controller.controllers.PeopleGenerator(new GameController(gameModel));
        while(Math.round(peopleGenerator.getSeconds_b_person()* 10) /10f > com.lift.game.controller.controllers.PeopleGenerator.MIN_SBP) {
            Float sbp = Math.round(peopleGenerator.getSeconds_b_person() * 10) / 10f;
            peopleGenerator.increaseDifficulty();
            assertEquals(com.lift.game.controller.controllers.PeopleGenerator.SBP_DELTA, sbp - Math.round(peopleGenerator.getSeconds_b_person()* 10) /10f, 0.01f);
        }
        peopleGenerator.increaseDifficulty();
        assertEquals(PeopleGenerator.MIN_SBP, Math.round(peopleGenerator.getSeconds_b_person()* 10) /10f, 0.01f);
    }
}
