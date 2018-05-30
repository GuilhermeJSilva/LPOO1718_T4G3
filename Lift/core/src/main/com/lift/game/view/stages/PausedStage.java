package com.lift.game.view.stages;

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
 * Shown when the game is paused.
 */
public class PausedStage extends Stage {
    /**
     * Label to show the game is paused.
     */
    private Label pausedLabel;



    /**
     * Creates the stage with the paused options.
     * @param game Uses the asset manager of this game.
     * @param camera Aligns the label with this camera.
     */
    public PausedStage(LiftGame game, OrthographicCamera camera) {
        super(new FitViewport(camera.viewportWidth, camera.viewportHeight), game.getSpriteBatch());

        addPausedLabel(camera);
        this.addActor(new ButtonsGroup(game, camera));
    }

    /**
     * Creates the paused label.
     * @param camera Aligns the label with this camera.
     */
    private void addPausedLabel(OrthographicCamera camera) {
        Label.LabelStyle label1Style = new Label.LabelStyle();
        label1Style.font = TextureManager.getInstance().getAssetManager().get("fonts/font2.otf", BitmapFont.class);
        label1Style.fontColor = Color.WHITE;

        this.pausedLabel = new Label("PAUSED", label1Style);
        float x = camera.viewportWidth / 2 - pausedLabel.getWidth() / 2;
        float y = camera.viewportHeight / 2 +  pausedLabel.getHeight();
        this.pausedLabel.setPosition(x, y);
        this.pausedLabel.setAlignment(Align.center);
        this.addActor(this.pausedLabel);
    }


}
