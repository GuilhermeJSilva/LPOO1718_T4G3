package com.lift.game.view.stages;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.lift.game.LiftGame;
import com.lift.game.model.GameModel;
import com.lift.game.model.entities.PlatformModel;
import com.lift.game.model.entities.person.PersonModel;
import com.lift.game.view.actors.game_actors.ElevatorActor;
import com.lift.game.view.actors.game_actors.PlatformActor;
import com.lift.game.view.actors.game_actors.person.PersonActor;

import java.util.ArrayList;

public class GameStage extends Stage {
    //TODO: ADD POOL TO PEOPLE

    public GameStage(LiftGame game, Camera camera) {
        super(new FitViewport(camera.viewportWidth, camera.viewportHeight));

        this.addActor(new ElevatorActor(game, GameModel.getInstance().getLeft_elevator()));
        this.addActor(new ElevatorActor(game,GameModel.getInstance().getRight_elevator()));

        ArrayList<PlatformModel> platformModels = GameModel.getInstance().getLeft_floors();
        addPlatforms(game, platformModels);

        platformModels = GameModel.getInstance().getRight_floors();
        addPlatforms(game, platformModels);

        Gdx.input.setInputProcessor(this);
    }

    private void addPlatforms(LiftGame game, ArrayList<PlatformModel> platformModels) {
        for (PlatformModel pm : platformModels) {
            this.addActor(new PlatformActor(game, pm));
            addPeopleActors(game, pm);
        }
    }

    private void addPeopleActors(LiftGame game, PlatformModel pm) {
        for(PersonModel personModel : pm.getWaiting_people()) {
            if(personModel.isNew_person()) {
                this.addActor(new PersonActor(game, personModel));
                personModel.setNew_person(false);
            }
        }
    }

    public void updateStage(LiftGame game) {
        for (PlatformModel pm :GameModel.getInstance().getLeft_floors()){
            addPeopleActors(game, pm);
        }

        for (PlatformModel pm :GameModel.getInstance().getRight_floors()){
            addPeopleActors(game, pm);
        }
    }

}
