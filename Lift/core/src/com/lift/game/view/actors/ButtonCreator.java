package com.lift.game.view.actors;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.lift.game.LiftGame;

public class ButtonCreator {

    public static ImageButton createButton(LiftGame game, OrthographicCamera camera, String fileName) {
        Texture texture = game.getAssetManager().get(fileName);
        Drawable drawable = new TextureRegionDrawable(new TextureRegion(texture));

        return new ImageButton(drawable);
    }
}