package com.lift.game.model.entities;

import com.lift.game.model.entities.person.Side;

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
    private Side side;

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

    public float getRotation() {
        return rotation;
    }

    public void setPosition(float x, float y, float rotation) {
        this.x = x;
        this.y = y;
        this.rotation = rotation;
    }

    public boolean isFlaggedForRemoval() {
        return flaggedForRemoval;
    }

    public void setFlaggedForRemoval(boolean flaggedForRemoval) {
        this.flaggedForRemoval = flaggedForRemoval;
    }

    public Side getSide() {
        return side;
    }


    public boolean isNew() {
        return new_entity;
    }

    public void setNew(boolean new_person) {
        this.new_entity = new_person;
    }

}
