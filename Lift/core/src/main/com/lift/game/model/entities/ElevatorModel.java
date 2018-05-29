package com.lift.game.model.entities;

import com.lift.game.model.Side;

/**
 * Model representing an elevator.
 */
public class ElevatorModel extends EntityModel {

    /**
     * Elevator start position.
     */
    private static final float STARTING_Y = 15f;

    /**
     * Default capacity.
     */
    private static  final Integer DEFAULT_CAPACITY = 3;

    /**
     * Maximum capacity of the elevator.
     */
    private Integer capacity;

    /**
     * Number of people currently in the elevator.
     */
    private Integer occupancy;

    /**
     * Target model.
     */
    private int target_floor;

    /**
     * True if the elevator is not moving.
     */
    private Boolean stopped;

    /**
     * Changes the target floor.
     *
     * @param target_floor New target floor.
     */
    public void setTarget_floor(int target_floor) {
        this.target_floor = target_floor;
    }

    /**
     * Changes the stopped variable.
     *
     * @param stopped New value for the variable stopped.
     */
    public void setStopped(Boolean stopped) {
        this.stopped = stopped;
    }

    /**
     * Returns the target floor.
     *
     * @return Target floor.
     */
    public int getTarget_floor() {
        return target_floor;
    }

    /**
     * Default constructor.
     */
    public ElevatorModel(Float x, Side side) {
        super(x, STARTING_Y, side);
        this.capacity = DEFAULT_CAPACITY;
        this.occupancy = 0;
        this.stopped = true;
        this.target_floor = -1;

    }

    /**
     * Returns the capacity of the elevator.
     *
     * @return Capacity of the elevator.
     */
    public Integer getCapacity() {
        return capacity;
    }

    /**
     * Returns true if there is space in the platform.
     * @return True if there is space in the platform.
     */
    public boolean isThereSpaceFree() {
        return (capacity > occupancy);
    }

    /**
     * Increments the occupancy by 1.
     */
    public void incrementOccupancy() {
        this.occupancy++;
    }

    /**
     * Decrements the occupancy by 1.
     */
    public void decrementOccupancy() {
        this.occupancy--;
    }

    /**
     * True if the elevator can receive people.
     * @return True if the elevator can receive people.
     */
    public Boolean getStopped() {
        return stopped;
    }


}
