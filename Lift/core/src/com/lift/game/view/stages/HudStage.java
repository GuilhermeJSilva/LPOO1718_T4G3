package com.lift.game.view.stages;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.lift.game.LiftGame;
import com.lift.game.model.GameModel;
import com.lift.game.model.entities.PlatformModel;
import com.lift.game.view.actors.game_actors.ElevatorActor;
import com.lift.game.view.actors.hub.CoinLabelActor;
import com.lift.game.view.actors.hub.ScoreLabelActor;
import com.lift.game.view.actors.polygon_actor.BasePolyActor;
import com.lift.game.view.actors.polygon_actor.DiamondPoly;

import java.util.ArrayList;

public class HudStage extends Stage {

    public HudStage(LiftGame game, Camera camera) {
        super(new FitViewport(camera.viewportWidth, camera.viewportHeight));
        this.addActor(new ScoreLabelActor(game, camera));
        this.addActor(new CoinLabelActor(game, camera));
        System.out.println(camera.viewportWidth/2 + " " +  camera.viewportHeight/2);
        this.addActor(new DiamondPoly(100,100,25, 30, 0x00ff00ff));
    }

    public void updateStage(float delta) {
        for (Actor a:
             this.getActors()) {
            if(a instanceof BasePolyActor) {
                ((BasePolyActor) a).decPercentage(delta);
            }
        }
    }
}
