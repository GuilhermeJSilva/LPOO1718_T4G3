package com.lift.game.view.stages;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.lift.game.LiftGame;
import com.lift.game.view.actors.ButtonCreator;
import com.lift.game.view.clickListeners.MainMenuCLick;
import com.lift.game.view.clickListeners.NewGameClick;

/**
 * Contains the play and replay button.
 */
public class ButtonsGroup extends Group {
    /**
     * Go back to main menu button.
     */
    private ImageButton mainMenuButton;

    /**
     * Restart game button.
     */
    private ImageButton restartButton;

    /**
     * Creates the group.
     * @param game Uses the asset manager of this game.
     * @param camera Aligns the buttons with this camera.
     */
    public ButtonsGroup(LiftGame game, OrthographicCamera camera) {
        createMainMenuButton(game, camera);
        createRestartButton(game, camera);
    }

    /**
     * Creates the main menu button.
     * @param game Uses the asset manager of this game.
     * @param camera Aligns the button with this camera.
     */
    private void createMainMenuButton(LiftGame game, OrthographicCamera camera) {
        mainMenuButton = ButtonCreator.createButton("menu.png");
        float x = camera.viewportWidth / 2 - 3 * mainMenuButton.getWidth() / 2;
        float y = camera.viewportHeight / 2 -   mainMenuButton.getHeight() / 2 ;
        this.mainMenuButton.setPosition(x, y);

        mainMenuButton.addListener(new MainMenuCLick(game));
        this.addActor(mainMenuButton);
    }

    /**
     * Creates the restart button.
     * @param game Uses the asset manager of this game.
     * @param camera Aligns the button with this camera.
     */
    private void createRestartButton(LiftGame game, OrthographicCamera camera) {
        restartButton = ButtonCreator.createButton("replay.png");
        float x = camera.viewportWidth / 2 + restartButton.getWidth() / 2;
        float y = camera.viewportHeight / 2 - restartButton.getHeight() / 2 ;
        this.restartButton.setPosition(x, y);

        restartButton.addListener(new NewGameClick(game));
        this.addActor(restartButton);
    }

}
