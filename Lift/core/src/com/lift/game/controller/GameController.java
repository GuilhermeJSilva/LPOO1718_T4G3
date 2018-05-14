package com.lift.game.controller;

import java.util.ArrayList;
import java.util.Random;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.Array;
import com.lift.game.controller.entities.ElevatorBody;
import com.lift.game.controller.entities.PersonBody;
import com.lift.game.controller.entities.PlatformBody;
import com.lift.game.model.GameModel;
import com.lift.game.model.entities.ElevatorModel;
import com.lift.game.model.entities.EntityModel;
import com.lift.game.model.entities.person.PersonModel;
import com.lift.game.model.entities.PlatformModel;

/**
 * Controls the game.
 */
public class GameController {

    /**
     * The buildings height in meters.
     */
    public static final Integer BUILDING_HEIGHT = 80;

    /**
     * The buildings width in meters.
     */
    public static final Integer BUILDING_WIDTH = 45;

    /**
     * Meters per floor
     */
    public static final Integer METERS_PER_FLOOR = 13;

    /**
     * Physic's world.
     */
    private final World world;

    /**
     * Time accumulator.
     */
    private Float accumulator = 0.0f;

    /**
     * Time accumulator for people generation.
     */
    private Float t_accumulator = 0.0f;

    /**
     * People generated per second.
     */
    private Integer p_per_second = 1;

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
     * Stores the singleton.
     */
    public static GameController instance;

    /**
     * Constructs the model.
     */
    private GameController() {
        super();
        this.world = new World(new Vector2(0, -5), false);
        this.left_elevator = new ElevatorBody(this.world, GameModel.getInstance().getLeft_elevator());
        this.right_elevator = new ElevatorBody(this.world, GameModel.getInstance().getRight_elevator());

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
        this.generatePeople(2);
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

        float frameTime = Math.min(delta, 0.25f);
        accumulator += frameTime;

        while (accumulator >= 1 / 60f) {

            world.step(1 / 60f, 6, 2);
            accumulator -= 1 / 60f;

            this.generateNewPeople(1 / 60f);
        }

        Array<Body> bodies = new Array<Body>();
        world.getBodies(bodies);

        for (Body body : bodies) {
            ((EntityModel) body.getUserData()).setPosition(body.getPosition().x, body.getPosition().y);
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

        int test = generator.nextInt(2);
        PersonModel p_model = GameModel.getInstance().add_waiting_person(floor, 1, dest, test);
        if (p_model != null) {
            PersonBody personBody = new PersonBody(this.world, p_model);
            if (test != 0) {
                left_floors.get(floor).getWaiting_people().add(personBody);
                personBody.getBody().setLinearVelocity(10, 0 );
            }
            else {
                right_floors.get(floor).getWaiting_people().add(personBody);
                personBody.getBody().setLinearVelocity(-10, 0 );

            }
        }

    }

    /**
     * Generates new people bases on the level of difficulty.
     */
    public void generateNewPeople(float delta) {
        t_accumulator += delta;
        while (t_accumulator > 1) {
            generatePeople(p_per_second);
            t_accumulator--;
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
     * Returns the left_floors of the game.
     *
     * @return Floors of the game.
     */
    public ArrayList<PlatformBody> getLeft_floors() {
        return left_floors;
    }

    /**
     * Returns the right_floors of the game.
     *
     * @return Floors of the game.
     */
    public ArrayList<PlatformBody> getRight_floors() {
        return right_floors;
    }


}
