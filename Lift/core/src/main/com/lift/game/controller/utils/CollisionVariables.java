package com.lift.game.controller.utils;

/**
 * Holds the variables to identify collisions
 */
public class CollisionVariables {
    /**
     * Identifies the fixture.
     */
    private short category_bits;

    /**
     * Identifies the body that collide with it.
     */
    private short mask;

    /**
     * True if the fixture is a sensor.
     */
    private boolean sensor;

    /**
     *
     * @param category_bits Identifies the fixture.
     * @param mask Identifies the body that collide with it.
     * @param sensor True if the fixture is a sensor.
     */
    public CollisionVariables(short category_bits, short mask, boolean sensor) {
        this.category_bits = category_bits;
        this.mask = mask;
        this.sensor = sensor;
    }

    /**
     * @return The collision category bits bits.
     */
    public short getCategory_bits() {
        return category_bits;
    }

    /**
     * @return The collision mask.
     */
    public short getMask() {
        return mask;
    }

    /**
     * @return True if the fixture is a sensor.
     */
    public boolean isSensor() {
        return sensor;
    }
}
