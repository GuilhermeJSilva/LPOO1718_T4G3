package com.lift.game.controller.entities;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.lift.game.model.entities.PersonModel;
import com.lift.game.model.entities.PlatformModel;

import java.util.LinkedList;
import java.util.List;

import static com.lift.game.controller.entities.ElevatorBody.ELEVATOR_MASK;
import static com.lift.game.controller.entities.PersonBody.PERSON_MASK;

public class PlatformBody extends EntityBody {
    /**
     * Platform length.
     */
    public static int PLATFORM_LENGTH = 26;

    /**
     * Platform height.
     */
    public static int PLATFORM_HEIGHT = 2;

    /**
     * Collision bit mask.
     */
    public static final short PLATFORM_MASK = 1;

    /**
     * People waiting in the platform.
     */
    private List<PersonBody> waiting_people;

    /**
     * Creates an entity Body.
     *
     * @param world
     * @param model
     */
    public PlatformBody(World world, PlatformModel model) {
        super(world, model);

        waiting_people =  new LinkedList<PersonBody>();
        List<PersonModel> wp = model.getWaiting_people();
        for (PersonModel pm : wp) {
            waiting_people.add(new PersonBody(world, pm));
        }
        float density = 1f, friction = 0.5f, restitution = 0f;
        int width = PLATFORM_LENGTH, height = PLATFORM_HEIGHT;

        this.add_fixture(body, new float[]{0, 0, 0, height, width, 0, width, height}
                , width, height, density, friction, restitution, PLATFORM_MASK, ELEVATOR_MASK, false
        );
        this.add_fixture(body, new float[]{width, 0, width, height, width + 10, 0, width + 10, height}
                , width, height, density, friction, restitution, PLATFORM_MASK, ELEVATOR_MASK, true
        );
        this.add_fixture(body, new float[]{-10, 0, -10, height, 0, 0, 0, height}
                , width, height, density, friction, restitution, PLATFORM_MASK, ELEVATOR_MASK, true
        );
        this.body.setGravityScale(0);
    }

    /**
     * Returns people waiting in the platform.
     *
     * @return People waiting in the platform.
     */
    public List<PersonBody> getWaiting_people() {
        return this.waiting_people;
    }
}
