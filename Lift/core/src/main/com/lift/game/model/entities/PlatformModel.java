package main.com.lift.game.model.entities;

import main.com.lift.game.model.entities.person.Side;

public class PlatformModel extends EntityModel{

    private int number_of_people;

    public static final int MAX_NUMBER_OF_PEOPLE = 3;

    private int floor_number;

    /**
     * Default entity constructor.
     *
     * @param x position.
     * @param y position
     */
    public PlatformModel(float x, float y, Side side, int floor_number) {
        super(x, y, side);
        number_of_people = 0;
        this.floor_number = floor_number;
    }

    public int getNumber_of_people() {
        return number_of_people;
    }

    public int incrementNPeople() {
        number_of_people++;
        return number_of_people;
    }

    public void decrementNPeople() {
        number_of_people--;
    }

    public int getFloor_number() {
        return floor_number;
    }
}
