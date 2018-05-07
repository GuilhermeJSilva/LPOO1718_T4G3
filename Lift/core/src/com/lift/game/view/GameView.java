package com.lift.game.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.FileHandleResolver;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGeneratorLoader;
import com.badlogic.gdx.graphics.g2d.freetype.FreetypeFontLoader;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.lift.game.LiftGame;
import com.lift.game.controller.GameController;
import com.lift.game.model.GameModel;
import com.lift.game.model.entities.ElevatorModel;
import com.lift.game.model.entities.PlatformModel;
import com.lift.game.model.entities.person.PersonModel;
import com.lift.game.view.actors.game_actors.ElevatorActor;
import com.lift.game.view.actors.game_actors.PlatformActor;
import com.lift.game.view.actors.game_actors.person.PersonActor;
import com.lift.game.view.actors.hub.CoinLabelActor;
import com.lift.game.view.actors.hub.ScoreLabelActor;

import java.util.ArrayList;

public class GameView extends ScreenAdapter {
    /**
     * Used to debug the position of the physics fixtures
     */
    private static final boolean DEBUG_PHYSICS = true;

    /**
     * How much meters does a pixel represent.
     */
    public final static float PIXEL_TO_METER = 0.0417f;

    /**
     * The width of the viewport in meters.
     */
    private static final float VIEWPORT_WIDTH = 45;

    /**
     * The height of the viewport in meters.
     */
    private static final float VIEWPORT_HEIGHT = 80;

    /**
     * The game this screen belongs to.
     */
    private final LiftGame game;

    /**
     * Stage for the hud.
     */
    private Stage hud;

    /**
     * Stage for all game entities.
     */
    private GameStage game_stage;

    /**
     * The camera used to show the viewport.
     */
    private final OrthographicCamera camera;

    /**
     * A renderer used to debug the physical fixtures.
     */
    private Box2DDebugRenderer debugRenderer;

    /**
     * The transformation matrix used to transform meters into pixels in order to
     * show fixtures in their correct places.
     */
    private Matrix4 debugCamera;

    /**
     * Creates this screen.
     *
     * @param liftGame The game this screen belongs to
     */
    public GameView(LiftGame liftGame) {
        this.game = liftGame;
        loadAssets();

        camera = createCamera();
        createHudStage();
        this.game_stage = new GameStage(this.game,this.camera);
    }

    /**
     * Creates the hud's stage.
     */
    private void createHudStage() {
        this.hud = new Stage(new FitViewport(camera.viewportWidth, camera.viewportHeight));
        this.hud.addActor(new ScoreLabelActor(this.game, this.camera));
        this.hud.addActor(new CoinLabelActor(this.game, this.camera));
    }


    /**
     * Creates the camera used to show the viewport.
     *
     * @return the camera
     */
    private OrthographicCamera createCamera() {
        OrthographicCamera camera = new OrthographicCamera(VIEWPORT_HEIGHT / PIXEL_TO_METER * ((float) Gdx.graphics.getWidth() / (float) Gdx.graphics.getHeight()), VIEWPORT_HEIGHT / PIXEL_TO_METER);
        camera.position.set(camera.viewportWidth / 2f, camera.viewportHeight / 2f, 0);
        camera.update();

        if (DEBUG_PHYSICS) {
            debugRenderer = new Box2DDebugRenderer();
            debugCamera = camera.combined.cpy();
            debugCamera.scl(1 / PIXEL_TO_METER);
        }

        return camera;
    }

    /**
     * Loads the assets needed by this screen.
     */
    private void loadAssets() {
        AssetManager manager = this.game.getAssetManager();
        manager.load("lift4.png", Texture.class);
        manager.load("elevator.png", Texture.class);

        loadFonts(manager);

        manager.finishLoading();

    }

    private void loadFonts(AssetManager manager) {
        FileHandleResolver resolver = new InternalFileHandleResolver();
        manager.setLoader(FreeTypeFontGenerator.class, new FreeTypeFontGeneratorLoader(resolver));
        manager.setLoader(BitmapFont.class, ".otf", new FreetypeFontLoader(resolver));

        FreetypeFontLoader.FreeTypeFontLoaderParameter mySmallFont = new FreetypeFontLoader.FreeTypeFontLoaderParameter();
        mySmallFont.fontFileName = "fonts/font2.otf";
        mySmallFont.fontParameters.size = 150;
        manager.load("fonts/font2.otf", BitmapFont.class, mySmallFont);

        manager.setLoader(BitmapFont.class, ".ttf", new FreetypeFontLoader(resolver));
        FreetypeFontLoader.FreeTypeFontLoaderParameter myBigFont = new FreetypeFontLoader.FreeTypeFontLoaderParameter();
        myBigFont.fontFileName = "fonts/font.ttf";
        myBigFont.fontParameters.size = 100;
        manager.load("fonts/font.ttf", BitmapFont.class, myBigFont);
    }

    /**
     * Renders this screen.
     *
     * @param delta time since last renders in seconds.
     */
    @Override
    public void render(float delta) {
        //GameController.getInstance().removeFlagged();

        //Move into the elevator actor
        handleInputs(delta);

        GameController.getInstance().update(delta);
        this.game_stage.updateStage(this.game);

        camera.position.set(camera.viewportWidth / 2f, camera.viewportHeight / 2f, 0);
        camera.update();
        game.getBatch().setProjectionMatrix(camera.combined);

        Gdx.gl.glClearColor(.135f, .206f, .235f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        game.getBatch().begin();
        drawBackground();
        game.getBatch().end();

        this.game_stage.draw();
        this.hud.draw();


        if (DEBUG_PHYSICS) {
            debugCamera = camera.combined.cpy();
            debugCamera.scl(1 / PIXEL_TO_METER);
            debugRenderer.render(GameController.getInstance().getWorld(), debugCamera);
        }
    }

    /**
     * Draws the background.
     */
    private void drawBackground() {
        Texture background = game.getAssetManager().get("lift4.png", Texture.class);
        background.setWrap(Texture.TextureWrap.ClampToEdge, Texture.TextureWrap.ClampToEdge);
        game.getBatch().draw(background, 0, 0, 0, 0, (int) (VIEWPORT_WIDTH / PIXEL_TO_METER), (int) (VIEWPORT_HEIGHT / PIXEL_TO_METER));
    }

    /**
     * Handles any inputs and passes them to the controller.
     *
     * @param delta time since last time inputs where handled in seconds
     */
    private void handleInputs(float delta) {
        if (Gdx.input.isTouched() || Gdx.input.isButtonPressed(Input.Buttons.LEFT)) {

            ArrayList<PlatformModel> floors = GameModel.getInstance().getLeft_floors();
            if (Gdx.input.getX() > Gdx.graphics.getWidth() / 2) {
                floors = GameModel.getInstance().getRight_floors();
            }
            int floor = determine_floor_number(floors);

            if (floor != -1) {
                if (Gdx.input.getX() > Gdx.graphics.getWidth() / 2) {
                    if (GameController.getInstance().getRight_elevator().getTarget_floor() != floor)
                        GameController.getInstance().getRight_elevator().setTarget_floor(floor);
                } else if (GameController.getInstance().getLeft_elevator().getTarget_floor() != floor)
                    GameController.getInstance().getLeft_elevator().setTarget_floor(floor);
            }

        }
    }

    /**
     * Determines the number of the floor.
     *
     * @param floors Floors.
     * @return Number of the floor.
     */
    private int determine_floor_number(ArrayList<PlatformModel> floors) {
        float y_pos = (Gdx.graphics.getHeight() - Gdx.input.getY()) * VIEWPORT_HEIGHT / Gdx.graphics.getHeight();
        int floor = -1;
        float distance = Float.MAX_VALUE;
        for (PlatformModel pm : floors) {
            if (Math.abs(pm.getY() - y_pos) < distance & y_pos > pm.getY())
                floor = floors.indexOf(pm);
        }
        return floor;
    }
}
