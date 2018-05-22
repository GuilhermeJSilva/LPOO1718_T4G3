package com.lift.game.view.stages;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.lift.game.LiftGame;
import com.lift.game.model.GameModel;
import com.lift.game.model.entities.PlatformModel;
import com.lift.game.model.entities.person.PersonModel;
import com.lift.game.model.entities.person.Side;
import com.lift.game.view.actors.game_actors.ElevatorActor;
import com.lift.game.view.actors.game_actors.PlatformActor;
import com.lift.game.view.actors.game_actors.person.PersonActor;

import java.util.ArrayList;
import java.util.HashMap;

public class GameStage extends Stage {
    //TODO: ADD POOL TO PEOPLE ????

    public GameStage(LiftGame game, Camera camera) {
        super(new FitViewport(camera.viewportWidth, camera.viewportHeight), game.getSpriteBatch());

        initiateIndicatorPositions(camera);

        this.addActor(new ElevatorActor(game, GameModel.getInstance().getElevator(Side.Left)));
        this.addActor(new ElevatorActor(game, GameModel.getInstance().getElevator(Side.Right)));

        ArrayList<PlatformModel> platformModels = GameModel.getInstance().getLeft_floors();
        addPlatforms(game, platformModels);

        platformModels = GameModel.getInstance().getRight_floors();
        addPlatforms(game, platformModels);

        addPeopleActors(game);

    }

    private void initiateIndicatorPositions(Camera camera) {
        HashMap<Side, ArrayList<Vector2>> indicatorPositions = new HashMap<Side, ArrayList<Vector2>>();
        ArrayList<Vector2> arrayPositions = new ArrayList<Vector2>();

        float leftIndicatorHeight = camera.viewportHeight / 2 + camera.viewportHeight / 4 + camera.viewportHeight / 60;
        arrayPositions.add(new Vector2(camera.viewportWidth / 2 - PersonActor.INDICATOR_WIDTH, leftIndicatorHeight));
        arrayPositions.add(new Vector2(camera.viewportWidth / 2, leftIndicatorHeight));
        arrayPositions.add(new Vector2(camera.viewportWidth / 2 + PersonActor.INDICATOR_WIDTH, leftIndicatorHeight));
        indicatorPositions.put(Side.Left, arrayPositions);

        arrayPositions = new ArrayList<Vector2>();

        float rightIndicatorRight = camera.viewportHeight / 2 - camera.viewportHeight / 6 - camera.viewportHeight / 56;
        arrayPositions.add(new Vector2(camera.viewportWidth / 2 - PersonActor.INDICATOR_WIDTH, rightIndicatorRight));
        arrayPositions.add(new Vector2(camera.viewportWidth / 2, rightIndicatorRight));
        arrayPositions.add(new Vector2(camera.viewportWidth / 2 + PersonActor.INDICATOR_WIDTH, rightIndicatorRight));
        indicatorPositions.put(Side.Right, arrayPositions);

        PersonActor.setIndicatorPositions(indicatorPositions);
    }

    private void addPlatforms(LiftGame game, ArrayList<PlatformModel> platformModels) {
        for (PlatformModel pm : platformModels) {
            this.addActor(new PlatformActor(game, pm));
        }
    }

    private void addPeopleActors(LiftGame game) {
        for (PersonModel personModel : GameModel.getInstance().getPeople()) {
            if (personModel.isNew_person()) {
                this.addActor(new PersonActor(game, personModel));
                personModel.setNew_person(false);
            }
        }
    }

    public void updateStage(LiftGame game) {
        addPeopleActors(game);
    }

    @Override
    public void draw() {
        PersonActor.resetCounters();
        super.draw();
    }
}
