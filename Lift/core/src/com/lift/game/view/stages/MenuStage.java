package com.lift.game.view.stages;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.lift.game.LiftGame;
import com.lift.game.view.GameView;

public class MenuStage extends Stage {
    private float GLOBAL_OFFSET;
    private float SPACING_OFFSET;
    public MenuStage(LiftGame game, OrthographicCamera camera) {
        super(new FitViewport(camera.viewportWidth, camera.viewportHeight));
        GLOBAL_OFFSET = -camera.viewportHeight / 6.5f;
        SPACING_OFFSET = camera.viewportHeight / 20f;
        addPlayButton(game, camera);
        addScoreButton(game, camera);
        addSettingsButton(game, camera);
        Gdx.input.setInputProcessor(this);

    }

    private void addPlayButton(final LiftGame game, OrthographicCamera camera) {
        ImageButton button = createButton(game, camera, "PLAY.png");
        button.setPosition(camera.viewportWidth / 2 - button.getWidth() / 2, camera.viewportHeight / 2 + button.getHeight() / 2 + GLOBAL_OFFSET + SPACING_OFFSET);
        button.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                game.setScreen(new GameView(game));
            }
        });
        this.addActor(button);
    }

    private void addSettingsButton(LiftGame game, OrthographicCamera camera) {
        ImageButton button = createButton(game, camera, "SETTINGS.png");
        button.setPosition(camera.viewportWidth / 2 - button.getWidth() / 2, camera.viewportHeight / 2 - button.getHeight() / 2 + GLOBAL_OFFSET);
        button.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                System.out.println("WIP");
            }
        });
        this.addActor(button);
    }

    private void addScoreButton(LiftGame game, OrthographicCamera camera) {
        ImageButton button = createButton(game, camera, "SCORE.png");
        button.setPosition(camera.viewportWidth / 2 - button.getWidth() / 2, camera.viewportHeight / 2 - 3 * button.getHeight() / 2 + GLOBAL_OFFSET - SPACING_OFFSET);
        button.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                System.out.println("WIP");
            }
        });
        this.addActor(button);
    }

    private ImageButton createButton(LiftGame game, OrthographicCamera camera, String fileName) {
        Texture texture = game.getAssetManager().get(fileName);
        Drawable drawable = new TextureRegionDrawable(new TextureRegion(texture));
        ImageButton button = new ImageButton(drawable);

        return button;
    }
}
