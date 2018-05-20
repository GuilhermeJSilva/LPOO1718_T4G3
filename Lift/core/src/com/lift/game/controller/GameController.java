package com.lift.game.controller;

import java.util.ArrayList;

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
import com.lift.game.model.entities.person.PersonState;

/**
 * Controls the game.
 */
public class GameController {

    /**
     * Meters per floor
     */
    public static final Integer METERS_PER_FLOOR = 13;

    /**
     * Physic's world.
     */
    private final World world;

    private final PeopleGenerator peopleGenerator = new PeopleGenerator(this);
    private final PeopleAdministrator peopleAdministrator = new PeopleAdministrator(this);

    /**
     * Time accumulator.
     */
    private Float accumulator = 0.0f;


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
    public static GameController instance;

    /**
     * Responsible to select what strategy to use.
     */
    private StrategySelector strategySelector;

    /**
     * Constructs the model.
     */
    private GameController() {
        super();
        this.world = new World(new Vector2(0, -5), false);
        this.left_elevator = new ElevatorBody(this.world, GameModel.getInstance().getLeft_elevator());
        this.right_elevator = new ElevatorBody(this.world, GameModel.getInstance().getRight_elevator());
        this.strategySelector = new StrategySelector();

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

        for (PersonModel pm : GameModel.getInstance().getPeople()){
            this.people.add(new PersonBody(world, pm));
        }
        world.setContactListener(new GameCollisionHandler());
    }

    /**
     * Returns the game model instance.
     *
     * @return Game model instance.
     */
    public static GameController getInstance() {
        if (instance == null)
            instance = new GameController();
        return instance;
    }

    public static void resetController() {
        instance = null;
    }

    /**
     * Returns the controller's left_elevator.
     *
     * @return Controller's left_elevator.
     */
    public ElevatorBody getLeft_elevator() {
        return left_elevator;
    }

    /**
     * Returns the controller's right_elevator.
     *
     * @return Controller's right_elevator.
     */
    public ElevatorBody getRight_elevator() {
        return right_elevator;
    }


    /**
     * Updates the game.
     *
     * @param delta Time passed.
     */
    public void update(float delta) {
        GameModel.getInstance().update(delta);
        this.updatePeople(delta);

        peopleAdministrator.run();

        float frameTime = Math.min(delta, 0.25f);
        accumulator += frameTime;

        while (accumulator >= 1 / 60f) {

            world.step(1 / 60f, 6, 2);
            accumulator -= 1 / 60f;

            peopleGenerator.generateNewPeople(1 / 60f);
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
                if(em ==  GameModel.getInstance().getLeft_elevator()) {
                    ((ElevatorModel) body.getUserData()).setTarget_floor(left_elevator.getTarget_floor());
                }
                else {
                    ((ElevatorModel) body.getUserData()).setTarget_floor(right_elevator.getTarget_floor());
                }
                em.setStopped(body.getLinearVelocity().y == 0);
            }
        }
    }

    private void updatePeople(float delta) {
        for(PersonBody personBody : people) {
            PersonModel per = (PersonModel) personBody.getBody().getUserData();
            if(per.update(delta) && per.getPersonState() != PersonState.GiveUP && per.getPersonState() != PersonState.InElevator) {
                strategySelector.getStrategy(per).giveUp(personBody, per.getSide());
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
        if(personBody != null)
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
}
