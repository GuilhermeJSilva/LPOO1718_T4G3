package com.lift.game.view.clickListeners;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.lift.game.LiftGame;

public class ScoreClick extends ClickListener {

    private LiftGame game;
    private ImageButton button;

    /**
     * Create a listener where {@link #clicked(InputEvent, float, float)} is only called for left clicks.
     */
    public ScoreClick(LiftGame game, ImageButton button) {
        this.game = game;
        this.button = button;
    }

    @Override
    public void clicked(InputEvent event, float x, float y) {
        if (!game.getGooglePlay().isSignedIn())
            game.getGooglePlay().signIn();

        if (game.getGooglePlay().isSignedIn()) {
            game.getGooglePlay().onShowLeaderboardsRequested();
        }
    }
}
