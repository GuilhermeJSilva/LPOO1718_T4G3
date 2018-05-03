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
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.lift.game.LiftGame;
import com.lift.game.controller.GameController;
import com.lift.game.model.GameModel;
import com.lift.game.model.entities.ElevatorModel;
import com.lift.game.model.entities.PlatformModel;
import com.lift.game.view.entities.ElevatorView;
import com.lift.game.view.entities.EntityView;
import com.lift.game.view.entities.PlatormView;

import java.util.ArrayList;

public class GameView extends ScreenAdapter {
    /**
     * Used to debug the position of the physics fixtures
     */
    private static final boolean DEBUG_PHYSICS = false;

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
     * Score label.
     */
    private Label score_label;

    /**
     * Coin label.
     */
    private Label coin_label;

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
        createLabels();
    }

    /**
     * Creates the game's label's.
     */
    private void createLabels() {
        Label.LabelStyle label1Style = new Label.LabelStyle();
        label1Style.font = this.game.getAssetManager().get("fonts/font.ttf", BitmapFont.class);
        label1Style.fontColor = Color.BLACK;

        this.score_label = new Label("30", label1Style);

        float x = camera.viewportWidth / 2 - this.score_label.getWidth() / 2;
        float y = camera.viewportHeight - this.score_label.getHeight();
        this.score_label.setPosition(x, y);

        Label.LabelStyle label2Style = new Label.LabelStyle();
        label2Style.font = this.game.getAssetManager().get("fonts/font2.ttf", BitmapFont.class);
        label2Style.fontColor = Color.WHITE;
        this.coin_label =  new Label("1000",label2Style );
        x = camera.viewportWidth / 2 - this.coin_label.getWidth() / 2;
        y = this.coin_label.getHeight() / 4;
        this.coin_label.setPosition(x, y);
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
        manager.setLoader(BitmapFont.class, ".ttf", new FreetypeFontLoader(resolver));

        FreetypeFontLoader.FreeTypeFontLoaderParameter mySmallFont = new FreetypeFontLoader.FreeTypeFontLoaderParameter();
        mySmallFont.fontFileName = "fonts/font.ttf";
        mySmallFont.fontParameters.size = 175;
        manager.load("fonts/font.ttf", BitmapFont.class, mySmallFont);

        FreetypeFontLoader.FreeTypeFontLoaderParameter myBigFont = new FreetypeFontLoader.FreeTypeFontLoaderParameter();
        myBigFont.fontFileName = "fonts/font.ttf";
        myBigFont.fontParameters.size = 100;
        manager.load("fonts/font2.ttf", BitmapFont.class, myBigFont);
    }

    /**
     * Renders this screen.
     *
     * @param delta time since last renders in seconds.
     */
    @Override
    public void render(float delta) {
        //GameController.getInstance().removeFlagged();
        handleInputs(delta);

        GameController.getInstance().update(delta);
        camera.position.set(camera.viewportWidth / 2f, camera.viewportHeight / 2f, 0);
        camera.update();
        game.getBatch().setProjectionMatrix(camera.combined);

        Gdx.gl.glClearColor(.135f, .206f, .235f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        game.getBatch().begin();
        drawBackground();
        drawEntities();
        drawLabels();
        game.getBatch().end();

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
     * Draws the score_label.
     */
    private void drawLabels() {
        this.score_label.draw(game.getBatch(), 1);
        this.coin_label.draw(game.getBatch(), 1);
    }


    /**
     * Draws the entities to the screen.
     */

    private void drawEntities() {

        ElevatorModel elevator = GameModel.getInstance().getLeft_elevator();
        EntityView view = new ElevatorView(game);
        view.update(elevator);
        view.draw(game.getBatch());

        elevator = GameModel.getInstance().getRight_elevator();
        view = new ElevatorView(game);
        view.update(elevator);
        view.draw(game.getBatch());

        ArrayList<PlatformModel> platformModels = GameModel.getInstance().getLeft_floors();
        for (PlatformModel pm : platformModels) {
            PlatormView pview = new PlatormView(game);
            pview.update(pm);
            pview.draw(game.getBatch());
        }

        platformModels = GameModel.getInstance().getRight_floors();
        for (PlatformModel pm : platformModels) {
            PlatormView pview = new PlatormView(game);
            pview.update(pm);
            pview.draw(game.getBatch());
        }
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
