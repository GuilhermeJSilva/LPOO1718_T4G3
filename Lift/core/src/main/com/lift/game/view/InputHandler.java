package main.com.lift.game.view;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;

import main.com.lift.game.controller.GameController;
import main.com.lift.game.model.entities.PlatformModel;
import main.com.lift.game.model.entities.person.Side;

public class InputHandler {
    private GameController gameController;
    public InputHandler(GameController gameController) {
        this.gameController = gameController;
    }

    /**
     * Handles any inputs and passes them to the controller.
     */
    void handleInputs() {
        if (Gdx.input.justTouched() || Gdx.input.isKeyJustPressed(Input.Buttons.LEFT)) {
            ArrayList<PlatformModel> floors = gameController.getGameModel().getLeft_floors();
            if (Gdx.input.getX() > Gdx.graphics.getWidth() / 2) {
                floors = gameController.getGameModel().getRight_floors();
            }
            int floor = determine_floor_number(floors);
            if (floor != -1) {
                if (Gdx.input.getX() > Gdx.graphics.getWidth() / 2) {
                    if (gameController.getElevator(Side.Right).getTarget_floor() != floor) {
                        gameController.getGameModel().getElevator(Side.Right).setTarget_floor(floor);
                        gameController.getElevator(Side.Right).setTarget_floor(gameController,floor);

                    }
                } else if (gameController.getElevator(Side.Left).getTarget_floor() != floor) {
                    gameController.getGameModel().getElevator(Side.Left).setTarget_floor(floor);
                    gameController.getElevator(Side.Left).setTarget_floor(gameController,floor);
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