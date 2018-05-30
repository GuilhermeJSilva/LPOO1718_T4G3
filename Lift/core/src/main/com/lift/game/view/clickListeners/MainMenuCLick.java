package com.lift.game.view.clickListeners;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.lift.game.GameState;
import com.lift.game.LiftGame;
import com.lift.game.view.GameView;
import com.lift.game.view.stages.MenuStage;

/**
 * Goes to the main menu.
 */
public class MainMenuCLick extends ClickListener {
    /**
     * Game that controls.
     */
    private final LiftGame game;

    /**
     * Creates the Click listener.
     * @param game Game to control.
     */
    public MainMenuCLick(LiftGame game) {
        this.game = game;
    }

    @Override
    public void clicked(InputEvent event, float x, float y) {
        game.setGameState(GameState.InMenu);
        MenuStage menuStage = ((GameView) game.getScreen()).getMenuStage();
        menuStage.updateHighScore(game);
        Gdx.input.setInputProcessor(menuStage);
    }
}
