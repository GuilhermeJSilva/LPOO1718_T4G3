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
    private LiftGame game;

    /**
     * Side of the screen clicked.
     */
    private Side side;

    /**
     * Floor of the actor.
     */
    private Integer target_floor;

    /**
     * Detects if a floor was clicked.
     * @param game Game to control.
     */
    public FloorClick(LiftGame game, int target_floor, Side side) {
        this.side = side;
        this.target_floor =  target_floor;
        this.game = game;
    }

    @Override
    public void clicked(InputEvent event, float x, float y) {
        System.out.println(side + " " + target_floor);
        game.getGameModel().getElevator(side).setTarget_floor(target_floor);
        game.getGameController().getElevator(side).setTarget_floor(game.getGameController(),target_floor);

    }
}
