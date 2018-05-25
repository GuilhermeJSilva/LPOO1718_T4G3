package com.lift.game.controller;

import java.util.ArrayList;
import java.util.Iterator;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.Array;
import com.lift.game.controller.entities.ElevatorBody;
import com.lift.game.controller.entities.PersonBody;
import com.lift.game.controller.entities.PlatformBody;
import com.lift.game.controller.entities.pstrategies.StrategySelector;
import com.lift.game.model.GameModel;
import com.lift.game.model.entities.ElevatorModel;
import com.lift.game.model.entities.EntityModel;
import com.lift.game.model.entities.PlatformModel;
import com.lift.game.model.entities.person.PersonModel;
import com.lift.game.model.entities.person.Side;

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
    private final PeopleGenerator peopleGenerator = new PeopleGenerator(this);

    /**
     * Responsible for administrating people.
     */
    private final PeopleAdministrator peopleAdministrator = new PeopleAdministrator(this);

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
    private ElevatorBody left_elevator;


    /**
     * Game's right_elevator.
     */
    private ElevatorBody right_elevator;

    /**
     * Left floors of the game.
     */
    private ArrayList<PlatformBody> left_floors;


    /**
     * Right floors of the game.
     */
    private ArrayList<PlatformBody> right_floors;

    /**
     * Free flying people.
     */
    private ArrayList<PersonBody> people;

    /**
     * Stores the singleton.
     */
    private static GameController instance;

    /**
     * Responsible to select what strategy to use.
     */
    private StrategySelector strategySelector;

    /**
     * Constructs the model.
     */
    public GameController() {
        super();
        this.world = new World(new Vector2(0, -5), false);
        this.left_elevator = new ElevatorBody(this.world, GameModel.getInstance().getElevator(Side.Left));
        this.right_elevator = new ElevatorBody(this.world, GameModel.getInstance().getElevator(Side.Right));
        this.strategySelector = new StrategySelector(this);

        this.people = new ArrayList<PersonBody>();
        this.left_floors = new ArrayList<PlatformBody>();
        this.right_floors = new ArrayList<PlatformBody>();

        ArrayList<PlatformModel> fm = GameModel.getInstance().getLeft_floors();

        for (PlatformModel pm : fm) {
            left_floors.add(new PlatformBody(this.world, pm, true));
        }

        fm = GameModel.getInstance().getRight_floors();

        for (PlatformModel pm : fm) {
            right_floors.add(new PlatformBody(this.world, pm, false));
        }

        for (PersonModel pm : GameModel.getInstance().getPeople()) {
            this.people.add(new PersonBody(world, pm));
        }
        world.setContactListener(new GameCollisionHandler(this));
    }


    /**
     * Returns the controller's left_elevator.
     *
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
     * @param delta Time passed.
     */
    public void update(float delta) {
        GameModel.getInstance().update(delta);

        peopleAdministrator.movePeople();

        float frameTime = Math.min(delta, 0.25f);
        accumulator += frameTime;

        while (accumulator >= 1 / 60f) {

            world.step(1 / 60f, 6, 2);
            accumulator -= 1 / 60f;

            GameModel.getInstance().tryToEnter(Side.Left);
            GameModel.getInstance().tryToEnter(Side.Right);
            peopleAdministrator.updatePeople(strategySelector,1 / 60f);
            peopleGenerator.generateNewPeople(1 / 60f);
            increaseDifficulty(1/60f);
        }

        updateModel();
    }


    private void updateModel() {
        Array<Body> bodies = new Array<Body>();
        world.getBodies(bodies);

        for (Body body : bodies) {
            ((EntityModel) body.getUserData()).setPosition(body.getPosition().x, body.getPosition().y, body.getAngle());
            if (body.getUserData() instanceof ElevatorModel) {
                ElevatorModel em = ((ElevatorModel) body.getUserData());
                em.setStopped(body.getLinearVelocity().y == 0);
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


    public void addPerson(PersonBody personBody) {
        if (personBody != null)
            people.add(personBody);
    }

    public StrategySelector getStrategySelector() {
        return strategySelector;
    }

    public PeopleAdministrator getPeopleAdministrator() {
        return peopleAdministrator;
    }

    public ArrayList<PersonBody> getPeople() {
        return people;
    }

    public void removeFlagged() {
        for (Iterator<PersonBody> iter = people.iterator(); iter.hasNext(); ) {
            PersonBody personBody = iter.next();
            EntityModel entityModel = ((EntityModel) personBody.getBody().getUserData());
            if (entityModel.isFlaggedForRemoval()) {
                world.destroyBody(personBody.getBody());
                GameModel.getInstance().remove(entityModel);
                iter.remove();
            }
        }
    }

    public ArrayList<PlatformBody> getFloors(Side side) {
        if(side == Side.Left) {
            return left_floors;
        }
        return  right_floors;
    }

    public void increaseDifficulty(float delta) {
        difficulty_accumulator += delta;
        while(difficulty_accumulator > 5f) {
            peopleAdministrator.increaseDifficulty();
            peopleGenerator.increaseDifficulty();
            difficulty_accumulator -= 5f;
        }
    }

}
