package com.lift.game.view.actors.hub;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.Align;
import com.lift.game.LiftGame;
import com.lift.game.model.GameModel;

public class ScoreLabelActor extends Actor {
    /**
     *  Label that contains the score.
     */
    private Label score_label;

    /**
     * Display according to this model.
     */
    private GameModel gameModel;

    /**
     * Constructs the label for the score.
     */
    public ScoreLabelActor(LiftGame game, Camera camera) {
        Label.LabelStyle label1Style = new Label.LabelStyle();
        label1Style.font = game.getAssetManager().get("fonts/font2.otf", BitmapFont.class);
        label1Style.fontColor = Color.BLACK;

        this.score_label = new Label("30.0", label1Style);
        float x = camera.viewportWidth / 2 - this.score_label.getWidth() / 2f;
        float y = camera.viewportHeight - this.score_label.getHeight();
        this.score_label.setPosition(x, y);
        this.score_label.setAlignment(Align.center);
        this.gameModel = game.getGameModel();
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        this.score_label.setText(Double.toString((Math.floor(gameModel.getTime_left() * 10) / 10f)));
        this.score_label.draw(batch, parentAlpha);
    }
}
