package com.lift.game.model;

import static com.lift.game.controller.GameController.METERS_PER_FLOOR;

import java.util.ArrayList;
import java.util.List;

import com.lift.game.controller.entities.PersonBody;
import com.lift.game.controller.entities.PlatformBody;
import com.lift.game.model.entities.ElevatorModel;
import com.lift.game.model.entities.person.PersonModel;
import com.lift.game.model.entities.PlatformModel;

/**
 * Represents the current state of the game.
 * Implements a singleton.
 */
public class GameModel {

    /**
     * Lives left.
     */
    private Integer lives;

    /**
     * Time left for the game.
     */
    private Double time_left;

    /**
     * Number of left_floors in the game.
     */
    private Integer n_levels;

    /**
     * Default number of left_floors.
     */
    private static final Integer DEFAULT_N_LEVEL = 6;

    /**
     * Stores the singleton.
     */
    public static GameModel instance;

    /**
     * Game's left left_elevator.
     */
    private ElevatorModel left_elevator;


    /**
     * Game's right left_elevator.
     */
    private ElevatorModel right_elevator;

    /**
     * Left floors of the game.
     */
    private ArrayList<PlatformModel> left_floors;


    /**
     * Right floors of the game.
     */
    private ArrayList<PlatformModel> right_floors;

    /**
     * Freeflying people.
     */
    private ArrayList<PersonModel> people;

    /**
     * Constructs the model.
     */
    private GameModel() {
        super();
        this.lives = 3;
        this.time_left = 30.0;
        n_levels = DEFAULT_N_LEVEL;
        left_elevator = new ElevatorModel(15.2f, 0, this.n_levels);
        right_elevator = new ElevatorModel(30f, 0, this.n_levels);

        this.people = new ArrayList<PersonModel>();
        this.left_floors = new ArrayList<PlatformModel>();
        this.right_floors = new ArrayList<PlatformModel>();

        for (int i = 1; i <= n_levels; i++) {
            left_floors.add(new PlatformModel(PlatformBody.PLATFORM_LENGTH / 2f + 0.1f, i * METERS_PER_FLOOR - METERS_PER_FLOOR / 2f));
        }

        for (int i = 1; i <= n_levels; i++) {
            right_floors.add(new PlatformModel(38.5f, i * METERS_PER_FLOOR - METERS_PER_FLOOR / 2f));
        }
    }

    /**
     * Returns the game model instance.
     *
     * @return Game model instance.
     */
    public static GameModel getInstance() {
        if (instance == null)
            instance = new GameModel();
        return instance;
    }


    public static void resetModel() {
        instance = null;
    }

    public Integer getLives() {
        return lives;
    }

    /**
     * Returns the number of levels in the current model.
     *
     * @return Number of levels in the current model.
     */
    public Integer getN_levels() {
        return n_levels;
    }

    /**
     * Returns the left_elevator.
     *
     * @return Game's left_elevator.
     */
    public ElevatorModel getLeft_elevator() {
        return left_elevator;
    }

    /**
     * Returns the right_elevator.
     *
     * @return Game's right_elevator.
     */
    public ElevatorModel getRight_elevator() {
        return right_elevator;
    }


    /**
     * Return the elevator according to character.
     */
    public ElevatorModel getElevator(char side) {
        if (side == 'L')
            return left_elevator;
        else
            return right_elevator;
    }

    /**
     * Returns the left_floors of the game.
     *
     * @return Floors of the game.
     */
    public ArrayList<PlatformModel> getLeft_floors() {
        return left_floors;
    }

    /**
     * Returns the right_floors of the game.
     *
     * @return Floors of the game.
     */
    public ArrayList<PlatformModel> getRight_floors() {
        return right_floors;
    }

    /**
     * Return's the time that is left.
     *
     * @return Time that is left.
     */
    public Double getTime_left() {
        return time_left;
    }


    /**
     * Updates the parameters of the people, base don the time that passed.
     *
     * @param delta Time that passed.
     */
    public void update(float delta) {
        this.time_left -= delta;
        if (this.time_left < 0)
            this.time_left = 0.0;

    }

    public ArrayList<PersonModel> getPeople() {
        return people;
    }

    public void addPerson(PersonModel new_p) {
        people.add(new_p);
    }
}


