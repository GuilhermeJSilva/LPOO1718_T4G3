package com.lift.game.model.entities;

import com.lift.game.model.Side;

/**
 * Represents the basic model for an entity.
 */
public abstract class EntityModel {
    /**
     * Position in the y axis.
     */
    private float y;

    /**
     * Position in the x axis.
     */
    private float x;

    /**
     * Rotation of the model.
     */
    private float rotation = 0;

    /**
     * If this field is true the object is going to be removed.
     */
    private boolean flaggedForRemoval = false;

    /**
     * New person.
     */
    private boolean new_entity = true;


    /**
     * Side of the screen the entity is on.
     */
    private final Side side;

    /**
     * Default entity constructor.
     *
     * @param x    position.
     * @param y    position.
     * @param side Side of the screen.
     */
    protected EntityModel(float x, float y, Side side) {
        super();
        this.y = y;
        this.x = x;
        this.side = side;
    }

    /**
     * Returns the y position of the entity.
     *
     * @return Y position of the entity.
     */
    public float getY() {
        return y;
    }

    /**
     * Returns the x position of the entity.
     *
     * @return X position of the entity.
     */
    public float getX() {
        return x;
    }

    /**
     * Returns the rotation of the entity.
     *
     * @return Rotation of the entity.
     */
    public float getRotation() {
        return rotation;
    }

    /**
     * Changes the position of an entity.
     *
     * @param x        New value for the X coordinate.
     * @param y        New value for the Y coordinate.
     * @param rotation New value for the rotation.
     */
    public void setPosition(float x, float y, float rotation) {
        this.x = x;
        this.y = y;
        this.rotation = rotation;
    }

    /**
     * @return True if the entity is flagged for removal.
     */
    public boolean isFlaggedForRemoval() {
        return flaggedForRemoval;
    }

    /**
     * Changes the value of the removal flag .
     *
     * @param flaggedForRemoval New value for the removal flag.
     */
    public void setFlaggedForRemoval(boolean flaggedForRemoval) {
        this.flaggedForRemoval = flaggedForRemoval;
    }

    /**
     * Returns the side of the entity.
     * @return Side of the entity.
     */
    public Side getSide() {
        return side;
    }

    /**
     * Returns true if the entity id new.
     * @return   True if the entity id new.
     */
    public boolean isNew() {
        return new_entity;
    }

    /**
     * Changes the flag for a person being new.
     * @param new_person New value for the flag.
     */
    public void setNew(boolean new_person) {
        this.new_entity = new_person;
    }

}
