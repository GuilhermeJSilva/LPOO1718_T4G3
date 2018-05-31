package com.lift.game.view.stages;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.lift.game.LiftGame;
import com.lift.game.view.TextureManager;
import com.lift.game.view.actors.ButtonCreator;
import com.lift.game.view.clickListeners.NewGameClick;

/**
 * Stage to access the menu.
 */
public class MenuStage extends Stage {
    /**
     * Global offset of the buttons.
     */
    private final float GLOBAL_OFFSET;

    /**
     * Spacing between buttons.
     */
    private final float SPACING_OFFSET;

    /**
     * Creates the stage and all its actors.
     * @param game Owner of the stage.
     * @param camera Camera to align.
     */
    public MenuStage(LiftGame game, OrthographicCamera camera) {
        super(new FitViewport(camera.viewportWidth, camera.viewportHeight), game.getSpriteBatch());
        GLOBAL_OFFSET = -camera.viewportHeight / 6.5f;
        SPACING_OFFSET = camera.viewportHeight / 20f;
        addPlayButton(game, camera);
        addScoreButton(camera);
        addThemeButton(camera);
        addHighScore(game, camera);
        addLiftTitle(camera);
        Gdx.input.setInputProcessor(this);

    }

    /**
     * Adds the title to the stage.
     * @param camera Camera to align.
     */
    private void addLiftTitle(OrthographicCamera camera) {
        Texture titleTexture = TextureManager.getInstance().getAssetManager().get("lifttitle.png");
        Image title = new Image(titleTexture);
        int x = (int)(camera.viewportWidth / 2 - title.getWidth() / 2);
        int y = (int)(camera.viewportHeight - 3 * title.getHeight() / 2);
        title.setPosition(x, y);
        this.addActor(title);
    }

    /**
     * Adds high score to the stage.
     * @param game Owner of the label.
     * @param camera Camera to align.
     */
    private void addHighScore(LiftGame game, OrthographicCamera camera) {
        Float highScore = game.getGamePreferences().getHighscore();
        if(highScore != 0 ) {
            Label.LabelStyle label1Style = new Label.LabelStyle();
            label1Style.font = TextureManager.getInstance().getAssetManager().get("fonts/font2.otf", BitmapFont.class);
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

    /**
     * Adds play button to the stage.
     * @param game Owner of the button.
     * @param camera Camera to align.
     */
    private void addPlayButton(LiftGame game, OrthographicCamera camera) {
        ImageButton button = ButtonCreator.createButton("PLAY.png");
        button.setPosition(camera.viewportWidth / 2 - button.getWidth() / 2, camera.viewportHeight / 2 + button.getHeight() / 2 + GLOBAL_OFFSET + SPACING_OFFSET);
        button.addListener(new NewGameClick(game));
        this.addActor(button);
    }

    /**
     * Adds theme button to the stage.
     * @param camera Camera to align.
     */
    private void addThemeButton(OrthographicCamera camera) {
        ImageButton button = ButtonCreator.createButton("SETTINGS.png");
        button.setPosition(camera.viewportWidth / 2 - button.getWidth() / 2, camera.viewportHeight / 2 - button.getHeight() / 2 + GLOBAL_OFFSET);
        button.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                System.out.println("WIP");
            }
        });
        this.addActor(button);
    }

    /**
     * Adds score button to the stage.
     * @param camera Camera to align.
     */
    private void addScoreButton(OrthographicCamera camera) {
        ImageButton button = ButtonCreator.createButton("SCORE.png");
        button.setPosition(camera.viewportWidth / 2 - button.getWidth() / 2, camera.viewportHeight / 2 - 3 * button.getHeight() / 2 + GLOBAL_OFFSET - SPACING_OFFSET);
        button.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                System.out.println("WIP");
            }
        });
        this.addActor(button);
    }

    /**
     * Updates the high score label.
     * @param highscore New high score.
     */
    public void updateHighScore(float highscore) {
        for(Actor a :  this.getActors()) {
            if(a instanceof Label)
                ((Label) a).setText("High Score: " + highscore);
        }
    }
}
