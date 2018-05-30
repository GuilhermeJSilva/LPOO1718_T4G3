package com.lift.game.view.actors;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.lift.game.view.TextureManager;

/**
 * Helper class to create buttons.
 */
public class ButtonCreator {

    /**
     * Creates an image button based on file.
     * @param fileName Name of the file containing the image.
     * @return Created button.
     */
    public static ImageButton createButton(String fileName) {
        Texture texture = TextureManager.getInstance().getAssetManager().get(fileName);
        Drawable drawable = new TextureRegionDrawable(new TextureRegion(texture));
        return new ImageButton(drawable);
    }
}