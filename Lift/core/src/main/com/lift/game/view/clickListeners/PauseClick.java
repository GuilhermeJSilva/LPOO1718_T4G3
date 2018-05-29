package com.lift.game.view.clickListeners;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.lift.game.GameState;
import com.lift.game.LiftGame;
import com.lift.game.view.GameView;

/**
 * Pauses the game.
 */
public class PauseClick extends ClickListener {
    /**
     * Game that controls.
     */
    private LiftGame game;

    /**
     * Creates the Click listener.
     * @param game Game to control.
     */
    public PauseClick(LiftGame game) {
        this.game = game;
    }

    @Override
    public void clicked(InputEvent event, float x, float y) {
        if (game.getGameState() == GameState.Playing) {
            game.setGameState(GameState.Paused);
            Gdx.input.setInputProcessor(((GameView)game.getScreen()).getPausedStage());
        }
        else
            game.setGameState(GameState.Playing);
    }
}
