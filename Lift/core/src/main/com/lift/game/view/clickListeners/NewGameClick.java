package com.lift.game.view.clickListeners;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.lift.game.GameState;
import com.lift.game.LiftGame;
import com.lift.game.view.GameView;

/**
 * Starts a new game
 */
public class NewGameClick extends ClickListener {
    /**
     * Game that controls.
     */
    private final LiftGame game;

    /**
     * Creates the Click listener.
     * @param game Game to control.
     */
    public NewGameClick(LiftGame game) {
        this.game = game;
    }

    @Override
    public void clicked(InputEvent event, float x, float y) {
        super.clicked(event, x, y);
        game.setGameState(GameState.StartScreen);
        ((GameView)game.getScreen()).resetGameStages();

    }
}
