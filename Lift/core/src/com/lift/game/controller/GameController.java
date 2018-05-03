package com.lift.game.controller;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
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
import com.lift.game.model.entities.PersonModel;
import com.lift.game.model.entities.PlatformModel;

/**
 * 
 * Controls the game.
 *
 */
public class GameController implements ContactListener {

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
	 * Game's elevator.
	 */
	private ElevatorBody elevator;

	/**
	 * People waiting for the elevator;
     * TODO: REMOVE
	 */
	private ArrayList<List<PersonBody>> waiting_people;



    /**
     * Floors of the game.
     */
    private ArrayList<PlatformBody> floors;

    /**
	 * Stores the singleton.
	 */
	public static GameController instance;

	/**
	 * Constructs the model.
	 */
	private GameController() {
		super();
		this.world = new World(new Vector2(0, 0), false);
		this.elevator = new ElevatorBody(this.world, GameModel.getInstance().getElevator());
		this.floors = new ArrayList<PlatformBody>();

		ArrayList<PlatformModel> fm = GameModel.getInstance().getFloors();

		for (PlatformModel pm: fm) {
		    floors.add(new PlatformBody(this.world, pm));
        }

        world.setContactListener(this);
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

	/**
	 * Returns the controller's elevator.
	 * 
	 * @return Controller's elevator.
	 */
	public ElevatorBody getElevator() {
		return elevator;
	}

	/**
	 * Updates the game.
	 * @param delta Time passed.
	 */
	public void update(float delta) {
		GameModel.getInstance().update(delta);

		float frameTime = Math.min(delta, 0.25f);
		accumulator += frameTime;
		
		while (accumulator >= 1 / 60f) {
			
			world.step(1 / 60f, 6, 2);
			accumulator -= 1 / 60f;
			
	        this.generateNewPeople();
		}

		Array<Body> bodies = new Array<Body>();
		world.getBodies(bodies);

		for (Body body : bodies) {
			((EntityModel) body.getUserData()).setPosition(body.getPosition().x, body.getPosition().y);
			if(body.getUserData() instanceof ElevatorModel) {
                ((ElevatorModel) body.getUserData()).setTarget_floor(elevator.getTarget_floor());
            }
		}
	}
	
	
	/**
	 * Generates new people in the game.
	 * @param n_people Number of people to generate.
	 */
	private void generatePeople(int n_people) {
		if(n_people < 0)
			return;
		Random generator = new Random();
		for(int i = 0; i < n_people; ++i) {
			int floor = generator.nextInt(GameModel.getInstance().getN_levels());
			generatePerson(floor);
		}
	}

	/**
	 * Generates a person in a certain floor.
	 * @param floor Floor to generate in.
	 */
	private void generatePerson(int floor) {
		Random generator = new Random();
		int dest;
		do {
			dest = generator.nextInt(GameModel.getInstance().getN_levels());
		} while (dest == floor);
		PersonModel p_model = GameModel.getInstance().add_waiting_person(floor, 0, 1, dest);
		waiting_people.get(floor).add(new PersonBody(world, p_model));
		
	}

	/**
	 * Generates new people bases on the level of difficulty.
	 * TODO : Add difficulty level
	 */
	public void generateNewPeople() {
		//generatePeople(1);
	}
	
	/**
	 * Returns the world.
	 * @return World;
	 */
	public World getWorld() {
		return this.world;
	}

    /**
     * Returns the floors of the game.
     * @return Floors of the game.
     */
    public ArrayList<PlatformBody> getFloors() {
        return floors;
    }

    @Override
    public void beginContact(Contact contact) {
        Body bodyA = contact.getFixtureA().getBody();
        Body bodyB = contact.getFixtureB().getBody();

        if(bodyA.getUserData() instanceof PlatformModel && bodyB.getUserData() instanceof ElevatorModel) {
            handlePlatformCollision(bodyA, bodyB, bodyB.getLinearVelocity().y < 0);

        }
        else if(bodyA.getUserData() instanceof ElevatorModel && bodyB.getUserData() instanceof PlatformModel) {
            handlePlatformCollision(bodyB, bodyA, bodyA.getLinearVelocity().y < 0);
        }
    }

    @Override
    public void endContact(Contact contact) {
        Body bodyA = contact.getFixtureA().getBody();
        Body bodyB = contact.getFixtureB().getBody();

        if(bodyA.getUserData() instanceof PlatformModel && bodyB.getUserData() instanceof ElevatorModel) {
            handlePlatformCollision(bodyA, bodyB, bodyB.getLinearVelocity().y > 0);

        }
        else if(bodyA.getUserData() instanceof ElevatorModel && bodyB.getUserData() instanceof PlatformModel) {
            handlePlatformCollision(bodyB, bodyA, bodyA.getLinearVelocity().y > 0);
        }
    }

    private void handlePlatformCollision(Body bodyA, Body bodyB, boolean b) {
        PlatformModel pm = (PlatformModel) bodyA.getUserData();
        ElevatorModel em = (ElevatorModel) bodyB.getUserData();


        if (em.getTarget_floor() == GameModel.getInstance().getFloors().indexOf(pm) && b) {
            bodyB.setLinearVelocity(0, 0);
        }
    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {

    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {

    }
}
