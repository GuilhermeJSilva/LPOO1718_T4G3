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

public class CoinLabelActor extends Actor {
    /**
     *  Label that contains the score.
     */
    private Label coin_label;

    /**
     * Contructs the label for the score.
     */
    public CoinLabelActor(LiftGame game, Camera camera) {
        Label.LabelStyle label2Style = new Label.LabelStyle();
        label2Style.font = game.getAssetManager().get("fonts/font.ttf", BitmapFont.class);
        label2Style.fontColor = Color.WHITE;

        this.coin_label =  new Label("1000",label2Style );
        float x = camera.viewportWidth / 2 - this.coin_label.getWidth() / 2;
        float y = this.coin_label.getHeight() / 8;
        this.coin_label.setPosition(x, y);
        this.coin_label.setAlignment(Align.center);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        this.coin_label.draw(batch, parentAlpha);
    }
}