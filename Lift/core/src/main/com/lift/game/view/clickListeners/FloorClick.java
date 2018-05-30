package com.lift.game.view.clickListeners;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.lift.game.LiftGame;
import com.lift.game.model.Side;

/**
 * Detects if a floor was clicked.
 */
public class FloorClick extends ClickListener {

    /**
     * Game to control.
     */
    private final LiftGame game;

    /**
     * Side of the screen clicked.
     */
    private final Side side;

    /**
     * Floor of the actor.
     */
    private final Integer target_floor;

    /**
     * Detects if a floor was clicked.
     * @param game Game to control.
     * @param side Side of the screen the click is on.
     * @param target_floor Number of the floor the click was on.
     */
    public FloorClick(LiftGame game, int target_floor, Side side) {
        this.side = side;
        this.target_floor =  target_floor;
        this.game = game;
    }

    @Override
    public void clicked(InputEvent event, float x, float y) {
        game.getGameModel().getElevator(side).setTarget_floor(target_floor);
        if (game.getGameController().getElevator(side).setTarget_floor(game.getGameController(),target_floor)) {
            game.getGameModel().getElevator(side).setStopped(false);
        }

    }
}
