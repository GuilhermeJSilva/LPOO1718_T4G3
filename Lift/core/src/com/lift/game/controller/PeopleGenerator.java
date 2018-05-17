package com.lift.game.controller;

import com.badlogic.gdx.Game;
import com.lift.game.controller.entities.PersonBody;
import com.lift.game.controller.entities.PlatformBody;
import com.lift.game.controller.entities.pstrategies.StrategySelector;
import com.lift.game.model.GameModel;
import com.lift.game.model.entities.PlatformModel;
import com.lift.game.model.entities.person.PersonModel;

import java.util.ArrayList;
import java.util.Random;

public class PeopleGenerator {
    private final GameController gameController;

    /**
     * Time accumulator for people generation.
     */
    private Float t_accumulator = 0.0f;

    /**
     * People generated per second.
     */
    private Integer seconds_b_person = 1;

    public PeopleGenerator(GameController gameController) {
        this.gameController = gameController;
    }


    public Float getT_accumulator() {
        return t_accumulator;
    }

    public void setT_accumulator(Float t_accumulator) {
        this.t_accumulator = t_accumulator;
    }

    public Integer getSeconds_b_person() {
        return seconds_b_person;
    }

    public void setSeconds_b_person(Integer seconds_b_person) {
        this.seconds_b_person = seconds_b_person;
    }

    /**
     * Generates new people in the game.
     *
     * @param n_people Number of people to generate.
     */
    void generatePeople(int n_people) {
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
    void generatePerson(int floor) {
        Random generator = new Random();
        int dest;
        do {
            dest = generator.nextInt(GameModel.getInstance().getN_levels());
        } while (dest == floor);

        int left = new Random().nextInt(2);
        PersonModel p_model = this.add_waiting_person(floor, 1, dest, left);
        if (p_model != null) {
            PersonBody personBody = new PersonBody(gameController.getWorld(), p_model);

            gameController.addPerson(personBody);
            StrategySelector.getStrategy(p_model).initialMovement(personBody.getBody(), left == 1);
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
     * @param satisfaction_factor Rate that the satisfaction decreases.
     * @return The person model that was added.
     */
    public PersonModel add_waiting_person(int floor, float satisfaction_factor, int dest, int left) {
        ArrayList<PlatformModel> floors;
        float x;
        if (left != 0) {
            floors = GameModel.getInstance().getLeft_floors();
            x = 0;
        } else {
            floors = GameModel.getInstance().getRight_floors();
            x = floors.get(floor).getX() + PlatformBody.PLATFORM_LENGTH / 2;
        }

        if (floors.get(floor).getNumber_of_people() >= PlatformModel.MAX_NUMBER_OF_PEOPLE)
            return null;

        PersonModel new_p = new PersonModel(x, floors.get(floor).getY() + PersonBody.HEIGHT / 2f + PlatformBody.PLATFORM_HEIGHT / 2, satisfaction_factor, dest);
        GameModel.getInstance().addPerson(new_p);
        floors.get(floor).incrementNPeople();
        return new_p;
    }
}