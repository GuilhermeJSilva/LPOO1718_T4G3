package com.lift.game.model.entities;

import com.lift.game.model.entities.person.PersonModel;

import java.util.LinkedList;
import java.util.List;

public class PlatformModel extends EntityModel{

    private int number_of_people;

    public static final int MAX_NUMBER_OF_PEOPLE = 3;

    /**
     * Default entity constructor.
     *
     * @param x position.
     * @param y position
     */
    public PlatformModel(float x, float y) {
        super(x, y);
        number_of_people = 0;
    }

    public int getNumber_of_people() {
        return number_of_people;
    }

    public void incrementNPeople() {
        number_of_people++;
    }

    public void decrementNPeople() {
        number_of_people--;
    }
}
