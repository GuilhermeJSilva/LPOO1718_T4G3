package com.lift.game.view.stages;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.lift.game.LiftGame;
import com.lift.game.view.actors.ButtonCreator;
import com.lift.game.view.clickListeners.MainMenuCLick;

public class EndStage extends Stage {
    private Label scoreLabel;

    private ImageButton mainMenuButton;

    public EndStage(LiftGame game, OrthographicCamera camera) {
        super(new FitViewport(camera.viewportWidth, camera.viewportHeight), game.getSpriteBatch());
        createScoreLabel(game, camera);
        createMainMenuButton(game, camera);
        this.addActor(scoreLabel);
        this.addActor(mainMenuButton);
    }

    private void createMainMenuButton(final LiftGame game, OrthographicCamera camera) {
        mainMenuButton = ButtonCreator.createButton(game, "PLAY.png");
        float x = camera.viewportWidth / 2 - mainMenuButton.getWidth() / 2;
        float y = camera.viewportHeight / 2 -  mainMenuButton.getHeight() / 2 ;
        this.mainMenuButton.setPosition(x, y);

        mainMenuButton.addListener(new MainMenuCLick(game));
    }

    private void createScoreLabel(LiftGame game, OrthographicCamera camera) {
        Label.LabelStyle label1Style = new Label.LabelStyle();
        label1Style.font = game.getAssetManager().get("fonts/font2.otf", BitmapFont.class);
        label1Style.fontColor = Color.WHITE;

        this.scoreLabel = new Label("Score: " + game.getGameModel().getScore(), label1Style);
        float x = camera.viewportWidth / 2 - scoreLabel.getWidth() / 2;
        float y = camera.viewportHeight / 2 +  scoreLabel.getHeight();
        this.scoreLabel.setPosition(x, y);
        this.scoreLabel.setAlignment(Align.center);

    }

    public void update(LiftGame game) {
        this.scoreLabel.setText("Score: " + game.getGameModel().getScore());
    }
}
