package com.lift.game.view.stages;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.lift.game.LiftGame;
import com.lift.game.view.actors.hub.CoinLabelActor;
import com.lift.game.view.actors.hub.LifeActor;
import com.lift.game.view.actors.hub.ScoreLabelActor;
import com.lift.game.view.actors.polygon_actor.BasePolyActor;

/**
 * Draws all non responsive modules of the game.
 */
public class HudStage extends Stage {
    /**
     * Constructs the stage according to camera and a game.
     * @param game Game.
     * @param camera Aligns its elements according to this camera.
     */
    public HudStage(LiftGame game, Camera camera) {
        super(new FitViewport(camera.viewportWidth, camera.viewportHeight), game.getSpriteBatch());
        this.addActor(new ScoreLabelActor(game, camera));
        this.addActor(new CoinLabelActor(game, camera));
        this.addActor(new LifeActor(game,camera));

    }

    /**
     * Updates the elements of stage.
     * @param game Updates according to this game.
     * @param delta Time passed since the last screen render.
     */
    public void updateStage(LiftGame game, float delta) {
        for (Actor a:
             this.getActors()) {
            if(a instanceof BasePolyActor) {
                ((BasePolyActor) a).decPercentage(delta);
            } else if(a instanceof CoinLabelActor){
                ((CoinLabelActor) a).update(game.getGamePreferences().getCoins() + game.getGameModel().getCoins());
            }
        }
    }
}
