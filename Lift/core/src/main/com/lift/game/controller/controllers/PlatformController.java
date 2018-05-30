package com.lift.game.controller.controllers;

import com.badlogic.gdx.physics.box2d.Body;
import com.lift.game.controller.GameController;
import com.lift.game.model.entities.PlatformModel;

import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;

import static com.lift.game.controller.entities.ScreenSensorBody.BOTTOM_SENSOR;

/**
 * Controls the platform movement.
 */
public class PlatformController {

    /**
     * The platforms jump to this ELEVATOR_HEIGHT.
     */
    public static final int JUMP_HEIGHT = 80;

    /**
     * Controls the platforms in this controller.
     */
    private final GameController gameController;

    /**
     * List of platforms to jump to the top of the screen.
     */
    private final List<Body> platformsToJump;

    /**
     * Creates the platform controller.
     * @param gameController Controller to be stored.
     */
    public PlatformController(GameController gameController) {
        this.gameController = gameController;
        this.platformsToJump = new LinkedList<Body>();
    }

    /**
     * Handles the collision between a platform and the bottom sensor.
     * @param platformBody Body of the platform that suffered a collision.
     * @param categoryBits Category bits of the sensor it hit.
     */
    public void handleCollision(Body platformBody, short categoryBits) {
        if(categoryBits == BOTTOM_SENSOR) {
            platformsToJump.add(platformBody);
        }
    }

    /**
     * Updates all the platforms.
     */
    public void update() {
        for(ListIterator<Body> iterator = platformsToJump.listIterator(); iterator.hasNext();) {
            Body  body = iterator.next();
            body.setTransform(body.getPosition().x, JUMP_HEIGHT, body.getAngle());
            PlatformModel platformModel = ((PlatformModel)body.getUserData());
            gameController.getPeopleAdministrator().jumpPeople(platformModel.getFloor_number(), platformModel.getSide());
            iterator.remove();
        }
    }
}
