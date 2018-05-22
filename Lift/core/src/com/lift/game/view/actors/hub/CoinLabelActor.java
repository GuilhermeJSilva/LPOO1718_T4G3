package com.lift.game.view.actors.hub;

import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.Align;
import com.lift.game.LiftGame;
import com.lift.game.PreferenceManager;

public class CoinLabelActor extends Actor {
    /**
     *  Label that contains the score.
     */
    private Label coin_label;



    /**
     * Constructs the label for the score.
     */
    public CoinLabelActor(LiftGame game, Camera camera) {
        Label.LabelStyle label2Style = new Label.LabelStyle();
        label2Style.font = game.getAssetManager().get("fonts/font.ttf", BitmapFont.class);
        label2Style.fontColor = Color.WHITE;

        this.coin_label =  new Label(Integer.toString(game.getGamePreferences().getCoins()),label2Style );
        float x = camera.viewportWidth / 2 - this.coin_label.getWidth() / 2;
        float y = this.coin_label.getHeight() / 8;
        this.coin_label.setPosition(x, y);
        this.coin_label.setAlignment(Align.center);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        this.coin_label.draw(batch, parentAlpha);
    }

    public void update(Integer coins) {
        this.coin_label.setText(Integer.toString(coins));
    }
}