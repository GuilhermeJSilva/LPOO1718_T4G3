package com.lift.game.view.stages.gameStage;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.lift.game.LiftGame;
import com.lift.game.GameState;
import com.lift.game.view.actors.ButtonCreator;
import com.lift.game.view.actors.hud.CoinLabelActor;
import com.lift.game.view.actors.hud.LifeActor;
import com.lift.game.view.actors.hud.TimeLabelActor;
import com.lift.game.view.clickListeners.MuteClick;
import com.lift.game.view.clickListeners.PauseClick;

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
        this.addActor(new TimeLabelActor(game, camera));
        this.addActor(new CoinLabelActor(game, camera));
        this.addActor(new LifeActor(camera, game.getGameModel()));

        addPauseButton(game, camera);
        addMuteButton(game, camera);

    }

    /**
     * Updates the elements of stage.
     *  @param game  Updates according to this game.
     *
     */
    public void updateStage(LiftGame game) {
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
        ImageButton pauseButton = ButtonCreator.createButton("pause1.png");
        int x = (int) (camera.viewportWidth / 2 + 1 * pauseButton.getWidth() / 2);
        int y = (int) (camera.viewportHeight - pauseButton.getHeight() * 5);
        pauseButton.setPosition(x, y);
        pauseButton.addListener(new PauseClick(game));
        this.addActor(pauseButton);
    }

    /**
     * Adds pause button to group.
     *
     * @param game  Game to make get the images.
     * @param camera Aligns according to this camera.
     */
    private void addMuteButton(final LiftGame game, Camera camera) {
        ImageButton muteButton = ButtonCreator.createButton("sound.png");
        int x = (int) (camera.viewportWidth / 2 -  2.6 * muteButton.getWidth()/2);
        int y = (int) (camera.viewportHeight - muteButton.getHeight() * 3.7);
        muteButton.setPosition(x, y);
        muteButton.addListener(new MuteClick(game));
        this.addActor(muteButton);
    }
}
