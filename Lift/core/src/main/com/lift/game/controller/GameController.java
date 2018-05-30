package com.lift.game.controller;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.lift.game.GameState;
import com.lift.game.controller.controllers.ElevatorController;
import com.lift.game.controller.controllers.PeopleAdministrator;
import com.lift.game.controller.controllers.PeopleGenerator;
import com.lift.game.controller.controllers.PlatformController;
import com.lift.game.controller.entities.ElevatorBody;
import com.lift.game.controller.entities.PersonBody;
import com.lift.game.controller.entities.PlatformBody;
import com.lift.game.controller.entities.ScreenSensorBody;
import com.lift.game.controller.entities.pstrategies.StrategySelector;
import com.lift.game.controller.powerups.PowerUpController;
import com.lift.game.model.GameModel;
import com.lift.game.model.Side;
import com.lift.game.model.entities.EntityModel;
import com.lift.game.model.entities.PlatformModel;
import com.lift.game.model.entities.person.PersonModel;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Controls the game.
 */
public class GameController {

    /**
     * Meters per floor.
     */
    public static final Float METERS_PER_FLOOR = 12.5f;

    /**
     * Physic's world.
     */
    private final World world;

    /**
     * Responsible for generating new people.
     */
    private final PeopleGenerator peopleGenerator;

    /**
     * Responsible for administrating people.
     */
    private final PeopleAdministrator peopleAdministrator;

    /**
     * Handles the elevators collisions.
     */
    private final ElevatorController elevatorController;

    /**
     * Handles the platform movement.
     */
    private final PlatformController platformController;

    /**
     * Time accumulator.
     */
    private Float accumulator = 0.0f;

    /**
     * Accumulates time to determine when to increase the difficulty.
     */
    private Float difficulty_accumulator = 0.0f;


    /**
     * Game's left_elevator.
     */
    private final ElevatorBody left_elevator;


    /**
     * Game's right_elevator.
     */
    private final ElevatorBody right_elevator;


    /**
     * End of screen sensors.
     */
    private final ScreenSensorBody screenSensorBody;

    /**
     * Left floors of the game.
     */
    private final ArrayList<PlatformBody> left_floors;


    /**
     * Right floors of the game.
     */
    private final ArrayList<PlatformBody> right_floors;

    /**
     * Free flying people.
     */
    private final ArrayList<PersonBody> people;

    /**
     * Responsible to select what strategy to use.
     */
    private final StrategySelector strategySelector;

    /**
     * Power up com.lift.game.controller.
     */
    private final PowerUpController powerUpController;

    /**
     * Game model to be controlled.
     */
    private final GameModel  gameModel;

    /**
     * Constructs the model.
     * @param gameModel Game model controlled.
     */
    public GameController(GameModel gameModel) {
        super();
        this.gameModel =  gameModel;
        this.world = new World(new Vector2(0, -5), false);
        this.strategySelector = new StrategySelector(this);
        this.powerUpController = new PowerUpController(this);

        this.screenSensorBody = new ScreenSensorBody(world);

        this.people = new ArrayList<PersonBody>();
        this.left_floors = new ArrayList<PlatformBody>();
        this.right_floors = new ArrayList<PlatformBody>();

        ArrayList<PlatformModel> fm = gameModel.getLeft_floors();

        for (PlatformModel pm : fm) {
            left_floors.add(new PlatformBody(this.world, pm, true));
        }

        fm = gameModel.getRight_floors();

        for (PlatformModel pm : fm) {
            right_floors.add(new PlatformBody(this.world, pm, false));
        }

        for (PersonModel pm : gameModel.getPeople()) {
            this.people.add(new PersonBody(world, pm));
        }

        this.left_elevator = new ElevatorBody(this.world, gameModel.getElevator(Side.Left), left_floors);
        this.right_elevator = new ElevatorBody(this.world, gameModel.getElevator(Side.Right), right_floors);

        world.setContactListener(new GameCollisionHandler(this));
        peopleAdministrator = new PeopleAdministrator(this);
        peopleGenerator = new PeopleGenerator(this);
        elevatorController = new ElevatorController(this);
        platformController = new PlatformController(this);
    }


    /**
     * Returns the com.lift.game.controller's left_elevator.
     *
     * @param side Side of the elevator.
     * @return Controller's left_elevator.
     */
    public ElevatorBody getElevator(Side side) {
        if (Side.Left == side)
            return left_elevator;
        else
            return right_elevator;
    }

    /**
     * Updates the game.
     *
     * @param gameState State of the game.
     * @param delta Time passed.
     */
    public void update(GameState gameState, float delta) {

        if (gameState == GameState.Playing) {
            gameModel.update(delta);
            peopleAdministrator.movePeople();
        }

        float frameTime = Math.min(delta, 0.25f);
        accumulator += frameTime;

        while (accumulator >= 1 / 60f) {

            world.step(1 / 60f, 10, 4);
            accumulator -= 1 / 60f;

            gameModel.tryToEnter(Side.Left);
            gameModel.tryToEnter(Side.Right);
            peopleAdministrator.updatePeople(strategySelector,1 / 60f);
            peopleGenerator.generateNewPeople(1 / 60f);
            powerUpController.update(1/60f);
            platformController.update();
            increaseDifficulty(1/60f);
        }

        updateModel();
    }

    /**
     * Updates the game's model.
     */
    private void updateModel() {
        Array<Body> bodies = new Array<Body>();
        world.getBodies(bodies);

        for (Body body : bodies) {
            Object userData = body.getUserData();
            if(userData != null) {
                ((EntityModel) userData).setPosition(body.getPosition().x, body.getPosition().y, body.getAngle());
            }
        }

    }




    /**
     * Returns the world.
     *
     * @return World;
     */
    public World getWorld() {
        return this.world;
    }


    /**
     * Adds a person to the array of people.
     * @param personBody Person to be added.
     */
    public void addPerson(PersonBody personBody) {
        if (personBody != null)
            people.add(personBody);
    }

    /**
     * Returns the strategy selector.
     * @return Strategy selector.
     */
    public StrategySelector getStrategySelector() {
        return strategySelector;
    }

    /**
     * Returns the people administrator.
     * @return People administrator.
     */
    public PeopleAdministrator getPeopleAdministrator() {
        return peopleAdministrator;
    }

    /**
     * Returns the array of people in the world.
     * @return People in the world.
     */
    public ArrayList<PersonBody> getPeople() {
        return people;
    }

    /**
     * Removed the people flagged for removal.
     */
    public void removeFlagged() {
        for (Iterator<PersonBody> iter = people.iterator(); iter.hasNext(); ) {
            PersonBody personBody = iter.next();
            EntityModel entityModel = ((EntityModel) personBody.getBody().getUserData());
            if (entityModel.isFlaggedForRemoval()) {
                world.destroyBody(personBody.getBody());
                gameModel.remove(entityModel);
                iter.remove();
            }
        }
    }

    /**
     * Returns the floors according to side.
     * @param side Side of the floors to be returned.
     * @return Floors of the respective side.
     */
    public ArrayList<PlatformBody> getFloors(Side side) {
        if(side == Side.Left) {
            return left_floors;
        }
        return  right_floors;
    }

    /**
     * Increasing the difficulty of the game.
     * @param delta Time passed.
     */
    public void increaseDifficulty(float delta) {
        difficulty_accumulator += delta;
        while(difficulty_accumulator > 5f) {
            peopleAdministrator.increaseDifficulty();
            peopleGenerator.increaseDifficulty();
            difficulty_accumulator -= 5f;
        }
    }

    /**
     * Returns the game model.
     * @return Game model.
     */
    public GameModel getGameModel() {
        return gameModel;
    }

    /**
     * Returns the elevator controller.
     * @return Elevator controller.
     */
    public ElevatorController getElevatorController() {
        return elevatorController;
    }

    /**
     * Returns the platform controller.
     * @return Platform controller.
     */
    public PlatformController getPlatformController() {
        return platformController;
    }
}
