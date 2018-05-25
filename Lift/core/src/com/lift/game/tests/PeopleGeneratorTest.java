package com.lift.game.tests;

import static org.junit.Assert.*;

import com.lift.game.controller.GameController;
import com.lift.game.controller.PeopleGenerator;
import org.junit.Test;

public class PeopleGeneratorTest extends GameTester{

    @Test
    public void generatePeople() {
        PeopleGenerator peopleGenerator =  new PeopleGenerator(GameController.getInstance());
    }

    @Test
    public void generatePerson() {
    }

    @Test
    public void generateNewPeople() {
    }

    @Test
    public void add_waiting_person() {
    }

    @Test
    public void increaseDifficulty() {
    }
}
