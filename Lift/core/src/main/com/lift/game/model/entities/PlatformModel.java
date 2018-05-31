package com.lift.game.model.entities;

import com.lift.game.model.Side;

/**
 * Models a platform.
 */
public class PlatformModel extends EntityModel{

    /**
     * Number of people in a platform.
     */
    private int number_of_people;

    /**
     * Maximum amount people in the platform.
     */
    public static final int MAX_NUMBER_OF_PEOPLE = 3;

    /**
     * Number of the floor.
     */
    private final int floor_number;

    /**
     * Default entity constructor.
     *
     * @param x position.
     * @param y position
     * @param side Side of the screen.
     * @param floor_number Number if the floor.
     */
    public PlatformModel(float x, float y, Side side, int floor_number) {
        super(x, y, side);
        number_of_people = 0;
        this.floor_number = floor_number;
    }

    /**
     * Returns the number of the people in the platform.
     * @return Number of the people in the platform.
     */
    public int getNumber_of_people() {
        return number_of_people;
    }

    /**
     * Increments the number of people by 1.
     * @return Number of people.
     */
    public int incrementNPeople() {
        number_of_people++;
        return number_of_people;
    }

    /**
     * Decrements the number of people.
     */
    public void decrementNPeople() {
        number_of_people--;
    }

    /**
     * @return Returns the floor number.
     */
    public int getFloor_number() {
        return floor_number;
    }
}
