package com.lift.game.view.stages.GameStage;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.lift.game.LiftGame;
import com.lift.game.controller.powerups.types.BasicPowerUP;
import com.lift.game.model.entities.PlatformModel;
import com.lift.game.model.entities.PowerUpModel;
import com.lift.game.model.entities.person.PersonModel;
import com.lift.game.model.Side;
import com.lift.game.view.GameView;
import com.lift.game.view.actors.polygon_actor.IndicatorCreator;
import com.lift.game.view.actors.game_actors.ElevatorActor;
import com.lift.game.view.actors.game_actors.PersonActor;
import com.lift.game.view.actors.game_actors.PlatformActor;
import com.lift.game.view.actors.game_actors.PowerUpActor;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Keeps all the actors that are respective to the game.
 */
@SuppressWarnings("SuspiciousNameCombination")
class GameGroup extends Group {
    /**
     * Constructs the stage.
     * @param game The stage is constructed according to this.
     * @param camera Alligns the stage according to this camera.
     */
    GameGroup(LiftGame game, Camera camera) {
        this.initiateIndicatorPositions(camera);
        this.initiateActivePUPositions(camera);

        this.addActor(new ElevatorActor(game.getGameModel().getElevator(Side.Left)));
        this.addActor(new ElevatorActor(game.getGameModel().getElevator(Side.Right)));

        ArrayList<PlatformModel> platformModels = game.getGameModel().getLeft_floors();
        this.addPlatforms(game, platformModels);

        platformModels = game.getGameModel().getRight_floors();
        this.addPlatforms(game, platformModels);

        this.addPeopleActors(game);
    }

    /**
     * Initializes the positions for the patience indicators.
     * @param camera To align the indicators.
     */
    private void initiateIndicatorPositions(Camera camera) {
        HashMap<Side, ArrayList<Vector2>> indicatorPositions = new HashMap<Side, ArrayList<Vector2>>();
        ArrayList<Vector2> arrayPositions = new ArrayList<Vector2>();

        float xLeft = camera.viewportWidth / 2 - IndicatorCreator.INDICATOR_WIDTH / 2 - camera.viewportWidth / 15f;
        arrayPositions.add(new Vector2(xLeft, camera.viewportHeight/ 2 + 1.5f * IndicatorCreator.INDICATOR_HEIGHT));
        arrayPositions.add(new Vector2(xLeft, camera.viewportHeight/ 2 - 0.5f * IndicatorCreator.INDICATOR_HEIGHT));
        arrayPositions.add(new Vector2(xLeft, camera.viewportHeight/ 2 - 2.5f * IndicatorCreator.INDICATOR_HEIGHT));
        indicatorPositions.put(Side.Left, arrayPositions);

        arrayPositions = new ArrayList<Vector2>();


        float xRight = camera.viewportWidth / 2 - IndicatorCreator.INDICATOR_WIDTH / 2 + camera.viewportWidth / 10;
        arrayPositions.add(new Vector2(xRight, camera.viewportHeight/ 2 + 1.5f * IndicatorCreator.INDICATOR_HEIGHT));
        arrayPositions.add(new Vector2(xRight, camera.viewportHeight/ 2 - 0.5f * IndicatorCreator.INDICATOR_HEIGHT));
        arrayPositions.add(new Vector2(xRight, camera.viewportHeight/ 2 - 2.5f * IndicatorCreator.INDICATOR_HEIGHT));
        indicatorPositions.put(Side.Right, arrayPositions);

        PersonActor.setIndicatorPositions(indicatorPositions);
    }

    /**
     * Initializes the positions for the power up indicators.
     * @param camera Camera to align to.
     */
    private void initiateActivePUPositions(Camera camera) {
        ArrayList<Vector2> activePositions = new ArrayList<Vector2>();
        float y = camera.viewportHeight / 2 - BasicPowerUP.RADIUS_OF_THE_BODY / GameView.PIXEL_TO_METER + camera.viewportHeight / 6;
        activePositions.add(new Vector2(camera.viewportWidth / 2 - 2* BasicPowerUP.RADIUS_OF_THE_BODY / GameView.PIXEL_TO_METER, y));
        activePositions.add(new Vector2(camera.viewportWidth / 2 - 4 * BasicPowerUP.RADIUS_OF_THE_BODY / GameView.PIXEL_TO_METER, y));
        activePositions.add(new Vector2(camera.viewportWidth / 2 + 0 * BasicPowerUP.RADIUS_OF_THE_BODY / GameView.PIXEL_TO_METER, y));
        PowerUpActor.setActivePositions(activePositions);
    }

    /**
     * Adds all platforms to the stage.
     * @param game Game of the actors.
     * @param platformModels Models to add.
     */
    private void addPlatforms(LiftGame game, ArrayList<PlatformModel> platformModels) {
        for (PlatformModel pm : platformModels) {
            this.addActor(new PlatformActor(game, pm));
        }
    }

    /**
     * Adds all people actors to the game.
     * @param game Game of the actors.
     */
    void addPeopleActors(LiftGame game) {
        for (PersonModel personModel : game.getGameModel().getPeople()) {
            if (personModel.isNew()) {
                this.addActor(new PersonActor(game, personModel));
                personModel.setNew(false);
            }
        }
    }

    /**
     * Adds all power up actors to the game.
     * @param game Game of the actors.
     */
    void addPUActors(LiftGame game) {
        for (PowerUpModel powerUpModel : game.getGameModel().getPowerUpModels()) {
            if (powerUpModel.isNew()) {
                this.addActor(new PowerUpActor(powerUpModel));
                powerUpModel.setNew(false);
            }
        }
    }
}