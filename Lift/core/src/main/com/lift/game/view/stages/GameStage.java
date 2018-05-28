package com.lift.game.view.stages;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.lift.game.LiftGame;
import com.lift.game.model.GameModel;
import com.lift.game.model.entities.PlatformModel;
import com.lift.game.model.entities.PowerUpModel;
import com.lift.game.model.entities.person.PersonModel;
import com.lift.game.model.entities.person.Side;
import com.lift.game.view.GameState;
import com.lift.game.view.actors.ButtonCreator;
import com.lift.game.view.actors.game_actors.ElevatorActor;
import com.lift.game.view.actors.game_actors.PersonActor;
import com.lift.game.view.actors.game_actors.PlatformActor;
import com.lift.game.view.actors.game_actors.PowerUpActor;

import java.util.ArrayList;
import java.util.HashMap;

import static com.lift.game.view.IndicatorCreator.INDICATOR_WIDTH;
import static com.lift.game.controller.powerups.types.BasicPowerUP.RADIUS_OF_THE_BODY;
import static com.lift.game.view.GameView.PIXEL_TO_METER;


public class GameStage extends Stage {

    public GameStage(GameModel gameModel, LiftGame game, Camera camera) {
        super(new FitViewport(camera.viewportWidth, camera.viewportHeight), game.getSpriteBatch());

        initiateIndicatorPositions(camera);
        initiateActivePUPositions(camera);

        this.addActor(new ElevatorActor(game, gameModel.getElevator(Side.Left)));
        this.addActor(new ElevatorActor(game, gameModel.getElevator(Side.Right)));

        ArrayList<PlatformModel> platformModels = gameModel.getLeft_floors();
        addPlatforms(game, platformModels);

        platformModels = gameModel.getRight_floors();
        addPlatforms(game, platformModels);

        addPeopleActors(gameModel, game);

        addPauseButton(game, camera);
        addMuteButton(game, camera);

    }

    private void initiateIndicatorPositions(Camera camera) {
        HashMap<Side, ArrayList<Vector2>> indicatorPositions = new HashMap<Side, ArrayList<Vector2>>();
        ArrayList<Vector2> arrayPositions = new ArrayList<Vector2>();

        float leftIndicatorHeight = camera.viewportHeight / 2 + camera.viewportHeight / 4 + camera.viewportHeight / 60;
        arrayPositions.add(new Vector2(camera.viewportWidth / 2 - INDICATOR_WIDTH, leftIndicatorHeight));
        arrayPositions.add(new Vector2(camera.viewportWidth / 2, leftIndicatorHeight));
        arrayPositions.add(new Vector2(camera.viewportWidth / 2 + INDICATOR_WIDTH, leftIndicatorHeight));
        indicatorPositions.put(Side.Left, arrayPositions);

        arrayPositions = new ArrayList<Vector2>();

        float rightIndicatorRight = camera.viewportHeight / 2 - camera.viewportHeight / 6 - camera.viewportHeight / 56;
        arrayPositions.add(new Vector2(camera.viewportWidth / 2 - INDICATOR_WIDTH, rightIndicatorRight));
        arrayPositions.add(new Vector2(camera.viewportWidth / 2, rightIndicatorRight));
        arrayPositions.add(new Vector2(camera.viewportWidth / 2 + INDICATOR_WIDTH, rightIndicatorRight));
        indicatorPositions.put(Side.Right, arrayPositions);

        PersonActor.setIndicatorPositions(indicatorPositions);
    }

    private void initiateActivePUPositions(Camera camera) {
        ArrayList<Vector2> activePositions = new ArrayList<Vector2>();
        float activeWidth = camera.viewportWidth / 2 - RADIUS_OF_THE_BODY / PIXEL_TO_METER;
        activePositions.add(new Vector2(activeWidth, camera.viewportHeight / 2 - 1.5f * RADIUS_OF_THE_BODY / PIXEL_TO_METER));
        activePositions.add(new Vector2(activeWidth, camera.viewportHeight / 2));
        activePositions.add(new Vector2(activeWidth, camera.viewportHeight / 2 + 1.5f * RADIUS_OF_THE_BODY / PIXEL_TO_METER));
        PowerUpActor.setActivePositions(activePositions);
    }


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


    private void addPlatforms(LiftGame game, ArrayList<PlatformModel> platformModels) {
        for (PlatformModel pm : platformModels) {
            this.addActor(new PlatformActor(game, pm));
        }
    }

    private void addPeopleActors(GameModel gameModel, LiftGame game) {
        for (PersonModel personModel : gameModel.getPeople()) {
            if (personModel.isNew()) {
                this.addActor(new PersonActor(game, personModel));
                personModel.setNew(false);
            }
        }
    }

    private void addPUActors(GameModel gameModel, LiftGame game) {
        for (PowerUpModel powerUpModel : gameModel.getPowerUpModels()) {
            if (powerUpModel.isNew()) {
                this.addActor(new PowerUpActor(game, powerUpModel));
                powerUpModel.setNew(false);
            }
        }
    }

    public void updateStage(GameModel gameModel, LiftGame game) {
        addPeopleActors(gameModel, game);
        addPUActors(gameModel, game);
    }

    @Override
    public void draw() {
        PersonActor.resetCounters();
        PowerUpActor.resetPositionCounter();
        super.draw();
    }
}
