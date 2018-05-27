package main.com.lift.game.view.stages;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.FitViewport;
import main.com.lift.game.LiftGame;

public class StartStage extends Stage {
    private Label timerLabel;
    private Double timeToStart = 5.0;

    public StartStage(LiftGame game, OrthographicCamera camera) {
        super(new FitViewport(camera.viewportWidth, camera.viewportHeight), game.getSpriteBatch());
        createTimerLabel(game, camera);
        this.addActor(timerLabel);
    }

    private void createTimerLabel(LiftGame game, OrthographicCamera camera) {
        Label.LabelStyle label1Style = new Label.LabelStyle();
        label1Style.font = game.getAssetManager().get("fonts/font2.otf", BitmapFont.class);
        label1Style.fontColor = Color.WHITE;

        this.timerLabel = new Label(timeToStart.toString(), label1Style);
        float x = camera.viewportWidth / 2 - timerLabel.getWidth() / 2;
        float y = camera.viewportHeight / 2 - timerLabel.getHeight() / 2;
        this.timerLabel.setPosition(x, y);
        this.timerLabel.setAlignment(Align.center);

    }

    public Double update(double delta) {
        timeToStart -= delta;
        if (timeToStart < 0) {
            timeToStart = 0.0;
        }
        this.timerLabel.setFontScale((float)(2f * (1 - (timeToStart - ((Long)Math.round(timeToStart))))));
        timerLabel.setText(((Long)Math.round(timeToStart)).toString());
        return timeToStart;
    }

    public void resetCounter() {
        timeToStart = 5.0;
    }



}
