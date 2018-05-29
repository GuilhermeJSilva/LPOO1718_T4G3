package com.lift.game.controller.utils;

/**
 * To make easier the creation of fixtures.
 */
public class PhysicalVariables {

    /**
     * Radius.
     */
    private float radius;

    /**
     * Width.
     */
    private float width;

    /**
     * Height.
     */
    private float height;

    /**
     * Density.
     */
    private float density;

    /**
     * Friction
     */
    private float friction;

    /**
     * Restitution.
     */
    private float restitution;

    /**
     * Creates the object.
     * @param width Width.
     * @param height Height.
     * @param density Density.
     * @param friction Friction.
     * @param restitution Restitution.
     */
    public PhysicalVariables(float width, float height, float density, float friction, float restitution) {
        this.width = width;
        this.height = height;
        this.density = density;
        this.friction = friction;
        this.restitution = restitution;
        this.radius = 0;
    }

    /**
     * Creates the object.
     * @param radius Radius.
     * @param density Density.
     * @param friction Friction.
     * @param restitution Restitution.
     */
    public PhysicalVariables(float radius, float density, float friction, float restitution) {
        this.radius = radius;
        this.density = density;
        this.friction = friction;
        this.restitution = restitution;
        this.width = radius;
        this.height = radius;
    }

    /**
     * Returns the radius.
     * @return Radius.
     */
    public float getRadius() {
        return radius;
    }

    /**
     * Returns the width.
     * @return Width.
     */
    public float getWidth() {
        return width;
    }

    /**
     * Returns the height.
     * @return Height.
     */
    public float getHeight() {
        return height;
    }


    /**
     * Returns the Density.
     * @return Density.
     */
    public float getDensity() {
        return density;
    }

    /**
     * Returns the Friction.
     * @return Friction.
     */
    public float getFriction() {
        return friction;
    }


    /**
     * Returns the Restitution.
     * @return Restitution.
     */
    public float getRestitution() {
        return restitution;
    }

}
