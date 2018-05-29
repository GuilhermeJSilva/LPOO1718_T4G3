package com.lift.game.view.stages;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.lift.game.GameState;
import com.lift.game.LiftGame;
import com.lift.game.view.GameView;
import com.lift.game.view.actors.ButtonCreator;

/**
 * Shown when the game is paused.
 */
public class PausedStage extends Stage {
    /**
     * Label to show the game is paused.
     */
    private Label pausedLabel;

    /**
     * Go back to main menu button.
     */
    private ImageButton mainMenuButton;

    /**
     * Creates the stage with the paused options.
     * @param game Uses the asset manager of this game.
     * @param camera Aligns the label with this camera.
     */
    public PausedStage(LiftGame game, OrthographicCamera camera) {
        super(new FitViewport(camera.viewportWidth, camera.viewportHeight), game.getSpriteBatch());

        addPausedLabel(game, camera);
        createMainMenuButton(game, camera);
    }

    /**
     * Creates the paused label.
     * @param game Uses the asset manager of this game.
     * @param camera Aligns the label with this camera.
     */
    private void addPausedLabel(LiftGame game, OrthographicCamera camera) {
        Label.LabelStyle label1Style = new Label.LabelStyle();
        label1Style.font = game.getAssetManager().get("fonts/font2.otf", BitmapFont.class);
        label1Style.fontColor = Color.WHITE;

        this.pausedLabel = new Label("PAUSED", label1Style);
        float x = camera.viewportWidth / 2 - pausedLabel.getWidth() / 2;
        float y = camera.viewportHeight / 2 +  pausedLabel.getHeight();
        this.pausedLabel.setPosition(x, y);
        this.pausedLabel.setAlignment(Align.center);
        this.addActor(this.pausedLabel);
    }

    /**
     * Creates the main menu button.
     * @param game Uses the asset manager of this game.
     * @param camera Aligns the button with this camera.
     */
    private void createMainMenuButton(final LiftGame game, OrthographicCamera camera) {
        mainMenuButton = ButtonCreator.createButton(game, "PLAY.png");
        float x = camera.viewportWidth / 2 - mainMenuButton.getWidth() / 2;
        float y = camera.viewportHeight / 2 -  mainMenuButton.getHeight() / 2 ;
        this.mainMenuButton.setPosition(x, y);

        mainMenuButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setGameState(GameState.InMenu);
                MenuStage menuStage = ((GameView) game.getScreen()).getMenuStage();
                menuStage.updateHighScore(game);
                Gdx.input.setInputProcessor(menuStage);
            }
        });
        this.addActor(mainMenuButton);
    }
}
