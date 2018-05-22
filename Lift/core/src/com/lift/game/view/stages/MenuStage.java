package com.lift.game.view.stages;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.lift.game.LiftGame;
import com.lift.game.model.GameModel;
import com.lift.game.view.GameView;
import com.lift.game.view.actors.ButtonCreator;

public class MenuStage extends Stage {
    private float GLOBAL_OFFSET;
    private float SPACING_OFFSET;

    public MenuStage(LiftGame game, OrthographicCamera camera) {
        super(new FitViewport(camera.viewportWidth, camera.viewportHeight), game.getSpriteBatch());
        GLOBAL_OFFSET = -camera.viewportHeight / 6.5f;
        SPACING_OFFSET = camera.viewportHeight / 20f;
        addPlayButton(game, camera);
        addScoreButton(game, camera);
        addSettingsButton(game, camera);
        addHighScore(game, camera);
        Gdx.input.setInputProcessor(this);

    }

    private void addHighScore(LiftGame game, OrthographicCamera camera) {
        Float highScore = game.getGamePreferences().getFloat("highscore", 0f);
        if(highScore != 0 ) {
            Label.LabelStyle label1Style = new Label.LabelStyle();
            label1Style.font = game.getAssetManager().get("fonts/font2.otf", BitmapFont.class);
            label1Style.fontColor = Color.WHITE;

            Label scoreLabel = new Label("High Score: " + highScore, label1Style);
            float x = camera.viewportWidth / 2 - scoreLabel.getWidth() / 2;
            float y = camera.viewportHeight / 2 + scoreLabel.getHeight();
            scoreLabel.setFontScale(0.8f);
            scoreLabel.setPosition(x, y);
            scoreLabel.setAlignment(Align.center);
            this.addActor(scoreLabel);
        }
    }

    private void addPlayButton(final LiftGame game, OrthographicCamera camera) {
        ImageButton button = ButtonCreator.createButton(game, camera, "PLAY.png");
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
        ImageButton button = ButtonCreator.createButton(game, camera, "SETTINGS.png");
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
        ImageButton button = ButtonCreator.createButton(game, camera, "SCORE.png");
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
        return ButtonCreator.createButton(game, camera, fileName);
    }
}
