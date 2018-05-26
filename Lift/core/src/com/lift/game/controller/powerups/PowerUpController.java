package com.lift.game.controller.powerups;

import com.lift.game.controller.GameController;
import com.lift.game.controller.entities.ElevatorBody;
import com.lift.game.controller.powerups.types.LifePU;
import com.lift.game.controller.powerups.types.StaticPowerUP;
import com.lift.game.model.entities.PowerUpModel;
import com.lift.game.model.entities.person.Side;

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
    public static final float MAX_INTERVAL = 20f;

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
    private LinkedList<StaticPowerUP> powerUps;

    /**
     * Owner of the controller.
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
     * Constructs the power up controller.
     *
     * @param gameController Owner of the controller.
     */
    public PowerUpController(GameController gameController) {
        this.powerUps = new LinkedList<StaticPowerUP>();
        this.gameController = gameController;
        this.time_accumulator = 0f;
        this.timeToNext = newTimeToNext();
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

        for(ListIterator<StaticPowerUP> iter = this.powerUps.listIterator(); iter.hasNext();) {
            StaticPowerUP powerUp =  iter.next();
            powerUp.update(gameController, delta);
            if(powerUp.getPowerUpState() == PowerUpState.Done) {
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
        powerUps.add(new LifePU(powerUpModel, gameController.getWorld()));
    }

    /**
     * Generates a random position for the power up and makes sure the position doesn't coincide with the elevator.
     * @param side Side of the screen.
     * @return Generated number.
     */
    private float randomY(Side side) {
        Random random = new Random();
        float v;
        do {
        v = MINIMUM_Y + random.nextFloat() * (MAXIMUM_Y - MINIMUM_Y);

        } while (v > gameController.getElevator(side).getY() - ElevatorBody.height && v  <  gameController.getElevator(side).getY() + ElevatorBody.height);
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
        return time + 5f;
    }
}
