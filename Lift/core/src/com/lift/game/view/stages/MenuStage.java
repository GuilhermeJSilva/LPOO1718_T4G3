package com.lift.game.view.stages;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.lift.game.LiftGame;

public class MenuStage extends Stage {
    public MenuStage(LiftGame game, OrthographicCamera camera) {
        super(new FitViewport(camera.viewportWidth, camera.viewportHeight));
        addPlayButton(game, camera);
        Gdx.input.setInputProcessor(this);

    }

    private void addPlayButton(LiftGame game, OrthographicCamera camera) {
        Texture texture = game.getAssetManager().get("elevator.png");
        Drawable drawable = new TextureRegionDrawable(new TextureRegion(texture));
        ImageButton button = new ImageButton(drawable);
        button.addListener(new ChangeListener() {
            @Override
            public void changed (ChangeEvent event, Actor actor) {
                System.out.println("Button Pressed");
            }
        });
        button.setPosition(camera.viewportWidth/2 - button.getWidth()/2, camera.viewportHeight/2 - button.getHeight()/2);
        this.addActor(button);
    }
}
