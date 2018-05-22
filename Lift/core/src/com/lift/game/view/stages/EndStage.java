package com.lift.game.view.stages;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.lift.game.LiftGame;
import com.lift.game.model.GameModel;
import com.lift.game.view.MenuView;
import com.lift.game.view.actors.ButtonCreator;

public class EndStage extends Stage {
    private Label scoreLabel;

    //TODO Change to image button
    private ImageButton mainMenuButton;

    public EndStage(LiftGame game, OrthographicCamera camera) {
        super(new FitViewport(camera.viewportWidth, camera.viewportHeight), game.getSpriteBatch());
        createScoreLabel(game, camera);
        createMainMenuButton(game, camera);
        this.addActor(scoreLabel);
        this.addActor(mainMenuButton);
    }

    private void createMainMenuButton(final LiftGame game, OrthographicCamera camera) {
        mainMenuButton = ButtonCreator.createButton(game, camera, "PLAY.png");
        float x = camera.viewportWidth / 2 - mainMenuButton.getWidth() / 2;
        float y = camera.viewportHeight / 2 -  mainMenuButton.getHeight() / 2 ;
        this.mainMenuButton.setPosition(x, y);

        mainMenuButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new MenuView(game));
                game.resetGame();
            }
        });
    }

    private void createScoreLabel(LiftGame game, OrthographicCamera camera) {
        Label.LabelStyle label1Style = new Label.LabelStyle();
        label1Style.font = game.getAssetManager().get("fonts/font2.otf", BitmapFont.class);
        label1Style.fontColor = Color.WHITE;

        this.scoreLabel = new Label("Score: " + GameModel.getInstance().getScore(), label1Style);
        float x = camera.viewportWidth / 2 - scoreLabel.getWidth() / 2;
        float y = camera.viewportHeight / 2 +  scoreLabel.getHeight();
        this.scoreLabel.setPosition(x, y);
        this.scoreLabel.setAlignment(Align.center);

    }

    public void update() {
        this.scoreLabel.setText("Score: " + GameModel.getInstance().getScore());
    }
}
