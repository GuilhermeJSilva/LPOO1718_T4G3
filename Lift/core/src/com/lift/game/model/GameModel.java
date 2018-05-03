package com.lift.game.model;

import static com.lift.game.controller.GameController.METERS_PER_FLOOR;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.lift.game.controller.entities.PersonBody;
import com.lift.game.controller.entities.PlatformBody;
import com.lift.game.model.entities.ElevatorModel;
import com.lift.game.model.entities.PersonModel;
import com.lift.game.model.entities.PlatformModel;

/**
 * Represents the current state of the game.
 * Implements a singleton.
 */
public class GameModel {

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
     * Constructs the model.
     */
    private GameModel() {
        super();
        n_levels = DEFAULT_N_LEVEL;
        left_elevator = new ElevatorModel(15f, 0, this.n_levels);
        right_elevator = new ElevatorModel(30f, 0, this.n_levels);

        this.left_floors = new ArrayList<PlatformModel>();
        this.right_floors = new ArrayList<PlatformModel>();

        for (int i = 1; i <= n_levels; i++) {
            left_floors.add(new PlatformModel(PlatformBody.PLATFORM_LENGTH / 4f - 0.5f, i * METERS_PER_FLOOR - METERS_PER_FLOOR / 2f));
        }

        for (int i = 1; i <= n_levels; i++) {
            right_floors.add(new PlatformModel(39, i * METERS_PER_FLOOR - METERS_PER_FLOOR / 2f));
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

    /**
     * Number of left_floors in the game.
     */
    private Integer n_levels;

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
     * Updates the parameters of the people, base don the time that passed.
     *
     * @param delta Time that passed.
     */
    public void update(float delta) {
        ArrayList<List<PersonModel>> passengers = this.left_elevator.getPassengers();

        for (List<PersonModel> list : passengers) {
            for (PersonModel person : list) {
                person.update(delta);
            }
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
        if(left !=  0)
            floors= this.left_floors;
        else
            floors= this.right_floors;

        if (floors.get(floor).getWaiting_people().size() >= PlatformBody.PLATFORM_LENGTH / PersonBody.WIDTH - 1)
            return null;
        float x = PersonBody.WIDTH / 2f * floors.get(floor).getWaiting_people().size() + PersonBody.WIDTH / 4f;
        PersonModel new_p = new PersonModel(x, floors.get(floor).getY() + PersonBody.HEIGHT / 2f - PlatformBody.PLATFORM_HEIGHT / 4f, satisfaction_factor, dest);
        floors.get(floor).getWaiting_people().add(new_p);
        return new_p;
    }
}
