package com.lift.game.view.stages;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.lift.game.LiftGame;

public class PausedStage extends Stage {
    Label pausedLabel;

    public PausedStage(LiftGame game, OrthographicCamera camera) {
        super(new FitViewport(camera.viewportWidth, camera.viewportHeight), game.getSpriteBatch());

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
}
