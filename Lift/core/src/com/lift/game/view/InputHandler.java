package com.lift.game.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.lift.game.controller.GameController;
import com.lift.game.model.GameModel;
import com.lift.game.model.entities.PlatformModel;
import com.lift.game.model.entities.person.Side;

import java.util.ArrayList;

public class InputHandler {
    public InputHandler() {
    }

    /**
     * Handles any inputs and passes them to the controller.
     */
    void handleInputs() {
        if (Gdx.input.justTouched() || Gdx.input.isKeyJustPressed(Input.Buttons.LEFT)) {
            ArrayList<PlatformModel> floors = GameModel.getInstance().getLeft_floors();
            if (Gdx.input.getX() > Gdx.graphics.getWidth() / 2) {
                floors = GameModel.getInstance().getRight_floors();
            }
            int floor = determine_floor_number(floors);
            if (floor != -1) {
                if (Gdx.input.getX() > Gdx.graphics.getWidth() / 2) {
                    if (GameController.getInstance().getElevator(Side.Right).getTarget_floor() != floor) {
                        GameModel.getInstance().getElevator(Side.Right).setTarget_floor(floor);
                        GameController.getInstance().getElevator(Side.Right).setTarget_floor(floor);

                    }
                } else if (GameController.getInstance().getElevator(Side.Left).getTarget_floor() != floor) {
                    GameModel.getInstance().getElevator(Side.Left).setTarget_floor(floor);
                    GameController.getInstance().getElevator(Side.Left).setTarget_floor(floor);
                }
            }

        }
    }

    /**
     * Determines the number of the floor.
     * @param floors Floors.
     * @return Number of the floor.
     */
    int determine_floor_number(ArrayList<PlatformModel> floors) {
        float y_pos = (Gdx.graphics.getHeight() - Gdx.input.getY()) * GameView.VIEWPORT_HEIGHT / Gdx.graphics.getHeight();
        int floor = -1;
        float distance = Float.MAX_VALUE;
        for (PlatformModel pm : floors) {
            if (Math.abs(pm.getY() - y_pos) < distance & y_pos > pm.getY())
                floor = floors.indexOf(pm);
        }
        return floor;
    }
}