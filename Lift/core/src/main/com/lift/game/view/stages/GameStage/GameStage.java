package com.lift.game.view.stages.GameStage;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.lift.game.LiftGame;
import com.lift.game.view.actors.game_actors.PersonActor;
import com.lift.game.view.actors.game_actors.PowerUpActor;

/**
 * Stage to be shown when the game is being played.
 */
public class GameStage extends Stage {
    /**
     * Group of elements that belong to the hud.
     */
    private final HudGroup hudActorGroup;

    /**
     * Group of elements that make the game.
     */
    private final GameGroup gameGroup;

    /**
     * Creates the stage.
     * @param game Built according to this game-
     * @param camera Algned to this camera.
     */
    public GameStage(LiftGame game, Camera camera) {
        super(new FitViewport(camera.viewportWidth, camera.viewportHeight), game.getSpriteBatch());
        gameGroup = new GameGroup(game, camera);
        gameGroup.toBack();
        this.addActor(gameGroup);
        hudActorGroup = new HudGroup(game, camera);
        hudActorGroup.toFront();
        this.addActor(hudActorGroup);

    }

    /**
     * Updates the stage.
     * @param game Updates are accordingly to this game.
     * @param delta Time since the last update.
     */
    public void updateStage(LiftGame game, float delta) {
        gameGroup.addPeopleActors(game);
        gameGroup.addPUActors(game);
        this.hudActorGroup.updateStage(game, delta);
    }

    @Override
    public void draw() {
        PersonActor.resetCounters();
        PowerUpActor.resetPositionCounter();
        super.draw();
    }
}
