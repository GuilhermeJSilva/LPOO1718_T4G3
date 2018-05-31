package com.lift.game.view.stages;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.lift.game.LiftGame;
import com.lift.game.view.TextureManager;

/**
 * Represents the stage before the game starts.
 */
public class StartStage extends Stage {
    /**
     * Label for the countdown.
     */
    private Label timerLabel;
    /**
     * Time left for the game to start.
     */
    private Double timeToStart = 5.0;

    /**
     * Camera to align.
     */
    private final Camera camera;

    /**
     * @param game Game it represents.
     * @param camera Camera to align.
     */
    public StartStage(LiftGame game, OrthographicCamera camera) {
        super(new FitViewport(camera.viewportWidth, camera.viewportHeight), game.getSpriteBatch());
        this.camera = camera;
        createTimerLabel();
        this.addActor(timerLabel);
    }

    /**
     * Creates the countdown timer.
     */
    private void createTimerLabel() {
        Label.LabelStyle label1Style = new Label.LabelStyle();
        label1Style.font = TextureManager.getInstance().getAssetManager().get("fonts/font2.otf", BitmapFont.class);
        label1Style.fontColor = Color.WHITE;

        this.timerLabel = new Label(timeToStart.toString(), label1Style);
        float x = camera.viewportWidth / 2 - timerLabel.getWidth() / 2;
        float y = camera.viewportHeight / 2 - timerLabel.getHeight() / 2;
        this.timerLabel.setPosition(x, y);
        this.timerLabel.setAlignment(Align.center);

    }

    /**
     * Updates the countdown label.
     * @param delta Time it passed.
     * @return Time left to start.
     */
    public Double update( double delta) {
        timeToStart -= delta;
        if (timeToStart < 0) {
            timeToStart = 0.0;
        }
        this.timerLabel.setFontScale((float)(2f * (1 - (timeToStart - ((Long)Math.round(timeToStart))))));
        timerLabel.setText(((Long)Math.round(timeToStart)).toString());
        float x = camera.viewportWidth / 2 - timerLabel.getWidth() / 2;
        float y = camera.viewportHeight / 2 - timerLabel.getHeight() / 2;
        this.timerLabel.setPosition(x, y);
        return timeToStart;
    }




}
