package com.lift.game.controller;

import com.lift.game.controller.entities.PersonBody;
import com.lift.game.controller.entities.PlatformBody;
import com.lift.game.model.GameModel;
import com.lift.game.model.entities.PlatformModel;
import com.lift.game.model.entities.person.PersonModel;
import com.lift.game.model.entities.person.Side;

import java.util.ArrayList;
import java.util.Random;

class PeopleGenerator {
    private final GameController gameController;

    /**
     * Time accumulator for people generation.
     */
    private Float t_accumulator = 0.0f;

    /**
     * People generated per second.
     */
    private Float seconds_b_person = 3f;

    public PeopleGenerator(GameController gameController) {
        this.gameController = gameController;
    }


    private Float getT_accumulator() {
        return t_accumulator;
    }

    private void setT_accumulator(Float t_accumulator) {
        this.t_accumulator = t_accumulator;
    }

    private Float getSeconds_b_person() {
        return seconds_b_person;
    }

    public void setSeconds_b_person(Float seconds_b_person) {
        this.seconds_b_person = seconds_b_person;
    }

    /**
     * Generates new people in the game.
     *
     * @param n_people Number of people to generate.
     */
    private void generatePeople(int n_people) {
        if (n_people < 0)
            return;
        Random generator = new Random();
        for (int i = 0; i < n_people; ++i) {
            int floor = generator.nextInt(GameModel.getInstance().getN_levels());
            generatePerson(floor);
        }
    }

    /**
     * Generates a person in a certain floor.
     *
     * @param floor Floor to generate in.
     */
    private void generatePerson(int floor) {
        Random generator = new Random();
        int dest;
        do {
            dest = generator.nextInt(GameModel.getInstance().getN_levels());
        } while (dest == floor);

        Side side = Side.values()[new Random().nextInt(2)];
        PersonModel p_model = this.add_waiting_person(floor, dest, side);

        if (p_model != null) {
            PersonBody personBody = new PersonBody(gameController.getWorld(), p_model);
            gameController.addPerson(personBody);
            GameController.getInstance().getStrategySelector().getStrategy(p_model).initialMovement(personBody.getBody(), side);
        }

    }

    /**
     * Generates new people bases on the level of difficulty.
     */
    public void generateNewPeople(float delta) {
        this.setT_accumulator(this.getT_accumulator() + delta);
        while (this.getT_accumulator() > this.getSeconds_b_person()) {
            generatePeople(1);
            this.setT_accumulator(this.getT_accumulator() - this.getSeconds_b_person());
        }

    }


    /**
     * Adds a new person to waiting for the left_elevator.
     *
     * @param floor               Floor the person is currently in.
     * @return The person model that was added.
     */
    private PersonModel add_waiting_person(int floor, int dest, Side side) {
        ArrayList<PlatformModel> floors;
        float x;
        if (side == Side.Left) {
            floors = GameModel.getInstance().getLeft_floors();
            x = 0;
        } else {
            floors = GameModel.getInstance().getRight_floors();
            x = floors.get(floor).getX() + PlatformBody.PLATFORM_LENGTH / 2;
        }

        if (floors.get(floor).getNumber_of_people() >= PlatformModel.MAX_NUMBER_OF_PEOPLE)
            return null;
        int people_position = floors.get(floor).incrementNPeople();
        float y = floors.get(floor).getY() + PersonBody.HEIGHT / 2f + PlatformBody.PLATFORM_HEIGHT / 2;
        PersonModel new_p = new PersonModel(x, y, floor, side, dest, people_position);
        GameModel.getInstance().addPerson(new_p);

        return new_p;
    }
}
