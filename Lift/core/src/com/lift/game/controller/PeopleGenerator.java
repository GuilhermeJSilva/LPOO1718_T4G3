package com.lift.game.controller;

import com.lift.game.controller.entities.PersonBody;
import com.lift.game.controller.entities.PlatformBody;
import com.lift.game.model.GameModel;
import com.lift.game.model.entities.PlatformModel;
import com.lift.game.model.entities.person.PersonModel;
import com.lift.game.model.entities.person.Side;

import java.util.ArrayList;
import java.util.Random;

/**
 * Responsible to generating new people in the controller.
 */
public class PeopleGenerator {
    /**
     * Minimum amount of seconds of people between people.
     */
    public static final float MIN_SBP = 1.5f;

    /**
     * Decrease of the seconds between person.
     */
    public static final float SBP_DELTA = 0.1f;

    /**
     * Instance of the game controller.
     */
    private final GameController gameController;

    /**
     * Time accumulator for people generation.
     */
    private Float t_accumulator = 0.0f;

    /**
     * People generated per second.
     */
    private Float seconds_b_person = 3f;

    /**
     * Constructs a new generator associated with a controller.
     * @param gameController Controller to generate the people in.
     */
    public PeopleGenerator(GameController gameController) {
        this.gameController = gameController;
    }

    /**
     * Returns the time accumulate since the last person was generated.
     * @return  Time accumulate since the last person was generated.
     */
    private Float getT_accumulator() {
        return t_accumulator;
    }

    /**
     * Sets the time accumulated since the last person was generated.
     * @param t_accumulator New value.
     */
    private void setT_accumulator(Float t_accumulator) {
        this.t_accumulator = t_accumulator;
    }

    /**
     * Returns the number of seconds between the generation of people.
     * @return Number of seconds between the generation of people.
     */
    public Float getSeconds_b_person() {
        return seconds_b_person;
    }

    /**
     * Changes the number of seconds between the generation of people.
     * @param seconds_b_person Number of seconds between the generation of people.
     */
    public void setSeconds_b_person(Float seconds_b_person) {
        this.seconds_b_person = seconds_b_person;
    }

    /**
     * Generates new people in the game.
     *
     * @param n_people Number of people to generate.
     */
    public void generatePeople(int n_people) {
        if (n_people < 0)
            return;
        Random generator = new Random();
        for (int i = 0; i < n_people; ++i) {
            int floor = generator.nextInt(gameController.getGameModel().getN_levels());
            generatePerson(floor);
        }
    }

    /**
     * Generates a person in a certain floor.
     *
     * @param floor Floor to generate in.
     */
    public void generatePerson(int floor) {
        Random generator = new Random();
        int dest;
        do {
            dest = generator.nextInt(gameController.getGameModel().getN_levels());
        } while (dest == floor);

        Side side = Side.values()[new Random().nextInt(2)];
        PersonModel p_model = this.add_waiting_person(floor, dest, side);

        if (p_model != null) {
            PersonBody personBody = new PersonBody(gameController.getWorld(), p_model);
            gameController.addPerson(personBody);
            gameController.getStrategySelector().getStrategy(p_model).initialMovement(personBody.getBody(), side);
        }

    }

    /**
     * Generates new people bases on the level of difficulty.
     *
     * @param delta Time passed since the last generating attempt.
     */
    public void generateNewPeople(float delta) {
        this.setT_accumulator(this.getT_accumulator() + delta);
        while (this.getT_accumulator() >= this.getSeconds_b_person()) {
            generatePeople(1);
            this.setT_accumulator(this.getT_accumulator() - this.getSeconds_b_person());
        }

    }


    /**
     * Adds a new person to waiting for the left_elevator.
     *
     * @param floor               Floor the person is currently in.
     * @param dest Floor number of destination.
     * @param side Side of the screen.
     * @return The person model that was added.
     */
    public PersonModel add_waiting_person(int floor, int dest, Side side) {
        ArrayList<PlatformModel> floors;
        float x;
        if (side == Side.Left) {
            floors = gameController.getGameModel().getLeft_floors();
            x = 0;
        } else {
            floors = gameController.getGameModel().getRight_floors();
            x = floors.get(floor).getX() + PlatformBody.PLATFORM_LENGTH / 2;
        }

        if (floors.get(floor).getNumber_of_people() >= PlatformModel.MAX_NUMBER_OF_PEOPLE)
            return null;
        int people_position = floors.get(floor).incrementNPeople();
        float y = floors.get(floor).getY() + PersonBody.HEIGHT / 2f + PlatformBody.PLATFORM_HEIGHT / 2;
        PersonModel new_p = new PersonModel(x, y, floor, side, dest, people_position);
        gameController.getGameModel().addPerson(new_p);

        return new_p;
    }

    /**
     * Increases the difficulty of the game by decreasing the number of seconds between generation attempts.
     */
    public void increaseDifficulty() {
        if(Math.round(seconds_b_person* 10) /10f > MIN_SBP ) {
            seconds_b_person -= SBP_DELTA;
        }
    }
}
