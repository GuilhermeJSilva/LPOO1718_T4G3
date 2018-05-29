package com.lift.game.controller.powerups;

import com.badlogic.gdx.physics.box2d.World;
import com.lift.game.controller.GameController;
import com.lift.game.controller.entities.ElevatorBody;
import com.lift.game.controller.powerups.types.*;
import com.lift.game.model.entities.EntityModel;
import com.lift.game.model.entities.PowerUpModel;
import com.lift.game.model.Side;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.ListIterator;
import java.util.Random;

/**
 * Controls all power ups present in the game.
 */
public class PowerUpController {

    /**
     * Minimum interval between power up generations.
     */
    public static final float MIN_INTERVAL = 5f;

    /**
     * Maximum interval between power up generations.
     */
    public static final float MAX_INTERVAL = 10f;

    /**
     * Maximum y coordinate for a power up in meters.
     */
    public static final int MAXIMUM_Y = 75;

    /**
     * Minimum y coordinate for a power up in meters.
     */
    public static final int MINIMUM_Y = 5;

    /**
     * List of waiting power ups.
     */
    private LinkedList<BasicPowerUP> powerUps;

    /**
     * Owner of the com.lift.game.controller.
     */
    private GameController gameController;

    /**
     * Time accumulator for the generation of power ups.
     */
    private Float time_accumulator;

    /**
     * Time to the next power up.
     */
    private Float timeToNext;

    /**
     * Possible power ups.
     */
    private ArrayList<Class<? extends BasicPowerUP>> typesOfPowerUps;

    /**
     * Array of incremental percentages of power ups.
     */
    private ArrayList<Float> incPercentages;

    /**
     * Used to get random numbers.
     */
    private Random randomGenerator;

    /**
     * Constructs the power up com.lift.game.controller.
     *
     * @param gameController Owner of the com.lift.game.controller.
     */
    public PowerUpController(GameController gameController) {
        this.randomGenerator = new Random();
        this.powerUps = new LinkedList<BasicPowerUP>();
        this.gameController = gameController;
        this.time_accumulator = 0f;
        this.timeToNext = newTimeToNext();
        this.initializePossibilities();
    }

    /**
     * Initializes the arrays to generate random power ups.
     */
    private void initializePossibilities() {
        this.incPercentages = new ArrayList<Float>();
        this.typesOfPowerUps = new ArrayList<Class<? extends BasicPowerUP>>();
        this.typesOfPowerUps.add(CoinPU.class);
        this.incPercentages.add(0.6f);
        this.typesOfPowerUps.add(LifePU.class);
        this.incPercentages.add(0.75f);
        this.typesOfPowerUps.add(VelocityPU.class);
        this.incPercentages.add(1f);
    }

    /**
     * Updates the time_accumulator and tries to spawn a power up.
     * @param delta Increment for the time accumulator.
     */
    public void update(float delta) {
        time_accumulator += delta;
        while (time_accumulator > timeToNext) {
            generateNewPowerUp();
            time_accumulator -= timeToNext;
            timeToNext = newTimeToNext();
        }

        for(ListIterator<BasicPowerUP> iter = this.powerUps.listIterator(); iter.hasNext();) {
            BasicPowerUP powerUp =  iter.next();
            powerUp.update(gameController, delta);
            if(powerUp.getPowerUpState() == PowerUpState.Done) {
                ((EntityModel)powerUp.getBody().getUserData()).setFlaggedForRemoval(true);
                gameController.getWorld().destroyBody(powerUp.getBody());
                iter.remove();
            }
        }
    }

    /**
     * Generates a new power up by adding a new power up to the list.
     */
    private void generateNewPowerUp() {
        Side side = pickSide();
        Float x = gameController.getElevator(side).getX();
        Float y = randomY(side);
        PowerUpModel powerUpModel = new PowerUpModel(x, y, side);
        gameController.getGameModel().addPowerUp(powerUpModel);
        powerUps.add(this.randomPowerUp(powerUpModel));
    }

    /**
     * Returns a random power up.
     *
     * @param powerUpModel The model that the body is going to represent.
     * @return Random power up.
     */
    private BasicPowerUP randomPowerUp(PowerUpModel powerUpModel) {
        Float rNumber = randomGenerator.nextFloat();
        for(Float p : this.incPercentages) {
            if(rNumber < p) {
                try {
                    return this.typesOfPowerUps.get(this.incPercentages.indexOf(p)).getConstructor(EntityModel.class, World.class).newInstance(powerUpModel, gameController.getWorld());
                } catch (NoSuchMethodException e) {
                    return new NullPU(powerUpModel, gameController.getWorld());
                } catch (IllegalAccessException e) {
                    return new NullPU(powerUpModel, gameController.getWorld());
                } catch (InstantiationException e) {
                    return new NullPU(powerUpModel, gameController.getWorld());
                } catch (InvocationTargetException e) {
                    return new NullPU(powerUpModel, gameController.getWorld());
                }
            }
        }
        return new NullPU(powerUpModel, gameController.getWorld());
    }

    /**
     * Generates a random position for the power up and makes sure the position doesn't coincide with the elevator.
     * @param side Side of the screen.
     * @return Generated number.
     */
    private float randomY(Side side) {
        float v;
        do {
        v = MINIMUM_Y + randomGenerator.nextFloat() * (MAXIMUM_Y - MINIMUM_Y);

        } while (v > gameController.getElevator(side).getY() - ElevatorBody.ELEVATOR_HEIGHT && v  <  gameController.getElevator(side).getY() + ElevatorBody.ELEVATOR_HEIGHT);
        return v;
    }

    /**
     * Gets a random side for the power up.
     * @return Random side.
     */
    private Side pickSide() {
        return Side.values()[new Random().nextInt(2)];
    }

    /**
     * Determines a random time for the next power up.
     * @return Next time for a power up.
     */
    private Float newTimeToNext() {
        Random random = new Random();
        Float time = random.nextFloat() * (MAX_INTERVAL - MIN_INTERVAL);
        return time + MIN_INTERVAL;
    }
}
