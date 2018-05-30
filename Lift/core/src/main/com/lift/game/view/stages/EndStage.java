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
 * Stage to be shown when the game is over.
 */
public class EndStage extends Stage {
    /**
     * Shows the score obtained in the game.
     */
    private Label scoreLabel;

    /**
     * Constructs the stage and all its elements.
     * @param game Game it is going to represent.
     * @param camera Aligns according to this camera.
     */
    public EndStage(LiftGame game, OrthographicCamera camera) {
        super(new FitViewport(camera.viewportWidth, camera.viewportHeight), game.getSpriteBatch());
        createScoreLabel(game, camera);
        this.addActor(new ButtonsGroup(game, camera));
    }

    /**
     * Creates the score label.
     * @param game Information necessary to create label.
     * @param camera Aligns according to this camera.
     */
    private void createScoreLabel(LiftGame game, OrthographicCamera camera) {
        Label.LabelStyle label1Style = new Label.LabelStyle();
        label1Style.font = TextureManager.getInstance().getAssetManager().get("fonts/font2.otf", BitmapFont.class);
        label1Style.fontColor = Color.WHITE;

        this.scoreLabel = new Label("Score: " + game.getGameModel().getScore(), label1Style);
        float x = camera.viewportWidth / 2 - scoreLabel.getWidth() / 2;
        float y = camera.viewportHeight / 2 +  scoreLabel.getHeight();
        this.scoreLabel.setPosition(x, y);
        this.scoreLabel.setAlignment(Align.center);
        this.addActor(scoreLabel);

    }

    /**
     * Updates the score shown.
     * @param score New score to be shown.
     */
    public void update(Double score) {
        this.scoreLabel.setText("Score: " + score);
    }
}
