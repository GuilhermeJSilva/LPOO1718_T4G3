package com.lift.game.view.clickListeners;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.lift.game.LiftGame;

/**
 * Mutes the music.
 */
public class MuteClick extends ClickListener {
    /**
     * Game that controls.
     */
    private LiftGame game;

    /**
     * Creates the Click listener.
     * @param game Game to control.
     */
    public MuteClick(LiftGame game) {
        this.game = game;
    }

    @Override
    public void clicked(InputEvent event, float x, float y) {
        if (game.getMusic().isPlaying()) {
            game.getMusic().pause();
        } else {
            game.getMusic().play();
        }
    }
}
