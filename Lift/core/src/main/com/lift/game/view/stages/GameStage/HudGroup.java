package com.lift.game.view.stages.GameStage;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.lift.game.LiftGame;
import com.lift.game.GameState;
import com.lift.game.view.actors.ButtonCreator;
import com.lift.game.view.actors.hub.CoinLabelActor;
import com.lift.game.view.actors.hub.LifeActor;
import com.lift.game.view.actors.hub.ScoreLabelActor;

/**
 * Draws all non responsive modules of the game.
 */
public class HudGroup extends Group {
    /**
     * Constructs the stage according to camera and a game.
     *
     * @param game   Game.
     * @param camera Aligns its elements according to this camera.
     */
    public HudGroup(LiftGame game, Camera camera) {
        this.addActor(new ScoreLabelActor(game, camera));
        this.addActor(new CoinLabelActor(game, camera));
        this.addActor(new LifeActor(game, camera));

        addPauseButton(game, camera);
        addMuteButton(game, camera);

    }

    /**
     * Updates the elements of stage.
     *
     * @param game  Updates according to this game.
     * @param delta Time passed since the last screen render.
     */
    public void updateStage(LiftGame game, float delta) {
        for (Actor a :
                this.getChildren()) {
            if (a instanceof CoinLabelActor) {
                if (game.getGameState() == GameState.Playing)
                    ((CoinLabelActor) a).update(game.getGamePreferences().getCoins() + game.getGameModel().getCoins());
                else
                    ((CoinLabelActor) a).update(game.getGamePreferences().getCoins());

            }
        }
    }

    /**
     * Adds a pause button to the group.
     *
     * @param game   Game to get textures from.
     * @param camera Camera to align.
     */
    private void addPauseButton(final LiftGame game, Camera camera) {
        ImageButton pauseButton = ButtonCreator.createButton(game, "PAUSE.png");
        int x = (int) (camera.viewportWidth / 2 - pauseButton.getWidth() / 2);
        int y = (int) (camera.viewportHeight / 4.1);
        pauseButton.setPosition(x, y);
        pauseButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (game.getGameState() == GameState.Playing)
                    game.setGameState(GameState.Paused);
                else
                    game.setGameState(GameState.Playing);
            }
        });
        this.addActor(pauseButton);
    }

    /**
     * Adds pause button to group.
     *
     * @param game   Game
     * @param camera
     */
    private void addMuteButton(final LiftGame game, Camera camera) {
        ImageButton muteButton = ButtonCreator.createButton(game, "MUTE.png");
        int x = (int) (camera.viewportWidth / 2 - muteButton.getWidth() / 2);
        int y = (int) (camera.viewportHeight / 5.6);
        muteButton.setPosition(x, y);
        muteButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (game.getMusic().isPlaying()) {
                    game.getMusic().pause();
                } else {
                    game.getMusic().play();
                }
            }
        });
        this.addActor(muteButton);
    }
}
