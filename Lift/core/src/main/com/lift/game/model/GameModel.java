package main.com.lift.game.model;

import static main.com.lift.game.controller.GameController.METERS_PER_FLOOR;

import java.util.ArrayList;
import java.util.LinkedList;

import main.com.lift.game.controller.entities.PlatformBody;
import main.com.lift.game.model.entities.ElevatorModel;
import main.com.lift.game.model.entities.EntityModel;
import main.com.lift.game.model.entities.PlatformModel;
import main.com.lift.game.model.entities.PowerUpModel;
import main.com.lift.game.model.entities.person.PersonModel;
import main.com.lift.game.model.entities.person.PersonState;
import main.com.lift.game.model.entities.person.Side;

/**
 * Represents the current state of the game.
 */
public class GameModel {
    /**
     * Max number of lives a model can have.
     */
    public static final int MAX_LIVES = 3;
    /**
     * Lives left.
     */
    private Integer lives;

    /**
     * Time left for the game.
     */
    private Double time_left;

    /**
     * Game's score.
     */
    private Double score = 0.0;

    /**
     * Number of left_floors in the game.
     */
    private Integer n_levels;

    /**
     * Default number of left_floors.
     */
    private static final Integer DEFAULT_N_LEVEL = 6;

    /**
     * Game's left left_elevator.
     */
    private ElevatorModel left_elevator;

    /**
     * Coins won in this game.
     */
    private Integer coins;

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
     * Free flying people.
     */
    private ArrayList<PersonModel> people;

    /**
     * Power ups present.
     */
    private LinkedList<PowerUpModel> powerUpModels;

    /**
     * Number of active power ups.
     */
    private Integer nActivePowerUps = 0;

    /**
     * Max number of active power ups.
     */
    public final static Integer MAX_ACTIVE_PU = 3;

    /**
     * Constructs the model.
     */
    public GameModel() {
        super();
        this.lives = 3;
        this.time_left = 30.0;
        this.coins = 10;
        this.n_levels = DEFAULT_N_LEVEL;
        this.left_elevator = new ElevatorModel(15.2f, Side.Left);
        this.right_elevator = new ElevatorModel(30f, Side.Right);

        this.people = new ArrayList<PersonModel>();
        this.left_floors = new ArrayList<PlatformModel>();
        this.right_floors = new ArrayList<PlatformModel>();

        for (int i = 1; i <= n_levels; i++) {
            left_floors.add(new PlatformModel(PlatformBody.PLATFORM_LENGTH / 2f + 0.1f, i * METERS_PER_FLOOR - METERS_PER_FLOOR / 2f, Side.Left, i - 1));
        }

        for (int i = 1; i <= n_levels; i++) {
            right_floors.add(new PlatformModel(38.5f, i * METERS_PER_FLOOR - METERS_PER_FLOOR / 2f, Side.Right, i - 1));
        }

        this.powerUpModels = new LinkedList<PowerUpModel>();
    }

    /**
     * Returns the number of lives.
     * @return Number of lives.
     */
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
     * Return the elevator according to character.
     */
    public ElevatorModel getElevator(Side side) {
        if (side == Side.Left)
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
        this.score += delta;
        if (this.time_left < 0)
            this.time_left = 0.0;

    }

    /**
     * Returns the people in the model.
     *
     * @return People in the model.
     */
    public ArrayList<PersonModel> getPeople() {
        return people;
    }

    /**
     * Adds a person to the model.
     *
     * @param new_p Person to be added
     */
    public void addPerson(PersonModel new_p) {
        people.add(new_p);
    }

    /**
     * Increments the number of lives.
     */
    public void incrementLives() {
        if(lives < MAX_LIVES)
            lives++;
    }

    /**
     * Decrements the number of lives.
     */
    public void decrementLives() {
        if (lives > 0)
            lives--;
    }

    /**
     * Removes a person from the model.
     *
     * @param model Model to remove.
     */
    public void remove(EntityModel model) {
        if (model instanceof PersonModel) {
            people.remove(model);
        }
    }

    /**
     * Increments the amount of time left, up to a max of 99.9 seconds.
     *
     * @param timeIncrease Time to increase by.
     */
    public void incrementTime(float timeIncrease) {
        time_left += timeIncrease;
        if (time_left > 99.9)
            time_left = 99.9;
    }

    /**
     * Returns the game's current score.
     *
     * @return Current score.
     */
    public Double getScore() {
        return (Math.round(score * 10)) / 10.0;
    }

    /**
     * Checks if the game has ended.
     *
     * @return True if the game has ended.
     */
    public boolean endGame() {
        return time_left <= 0 || lives <= 0;
    }

    /**
     * Return the number of coins won in this game.
     *
     * @return Number of coins.
     */
    public Integer getCoins() {
        return coins;
    }

    /**
     * Increases the number of coins won in this game.
     *
     * @param coins Increment.
     */
    public void incCoins(Integer coins) {
        this.coins += coins;
    }

    /**
     * All the people in one side of the screen try to enter.
     * @param side Side of the screen.
     */
    public void tryToEnter(Side side) {
        for (PersonModel personModel : people) {
            if (personModel.getFloor() == getElevator(side).getTarget_floor() && getElevator(side).getStopped() && personModel.getSide() == side && (personModel.getPersonState() == PersonState.Waiting || personModel.getPersonState() == PersonState.StoppedWaiting)) {
                personModel.setTryingToEnter(true);
            }
        }
    }

    /**
     * Add new power up to the list.
     *
     * @param powerUpModel Power up to be added.
     */
    public void addPowerUp(PowerUpModel powerUpModel) {
        powerUpModels.add(powerUpModel);
    }

    /**
     * Returns the power up list.
     * @return Power up list.
     */
    public LinkedList<PowerUpModel> getPowerUpModels() {
        return powerUpModels;
    }

    /**
     * Increments the number of active power ups.
     * @return True if the increment was successful.
     */
    public boolean incrementActivePowerUps() {
        if(nActivePowerUps < MAX_ACTIVE_PU) {
            nActivePowerUps++;
            return true;
        }
        return false;
    }

    /**
     * Decrements the number of active power ups.
     */
    public void decrementActivePowerUps() {
        nActivePowerUps--;
        if(nActivePowerUps < 0)
            nActivePowerUps = 0;
    }
}


