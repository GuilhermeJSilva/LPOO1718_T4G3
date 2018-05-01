package com.lift.game.model.entities;

import java.util.LinkedList;
import java.util.List;

public class PlatformModel extends EntityModel{


    /**
     * People waiting for the elevator in this platform.
     */
    private List<PersonModel> waiting_people;

    /**
     * Default entity constructor.
     *
     * @param x position.
     * @param y position
     */
    public PlatformModel(float x, float y) {
        super(x, y);
        waiting_people = new LinkedList<PersonModel>();
    }

    /**
     * Returns the people in this platform.
     * @return People in the platform.
     */
    public List<PersonModel> getWaiting_people() {
        return waiting_people;
    }
}
