package com.lift.game.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
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
     * The font used.
     */

    private BitmapFont fonte;

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
        this.game.getAssetManager().load("lift4.png", Texture.class);
        this.game.getAssetManager().load("elevator.png", Texture.class);

        this.game.getAssetManager().finishLoading();
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/font.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 175;
        fonte = generator.generateFont(parameter); // font size 12 pixels

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
        drawScore();
        drawCoins();
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
     * Draws the score.
     */
    private void drawScore() {

        fonte.setColor(Color.BLACK);
        int width = 130;
        fonte.draw(game.getBatch(), "30.0", Gdx.graphics.getWidth() / 2 - width, Gdx.graphics.getHeight() - 20);

    }

    private void drawCoins() {

        fonte.setColor(Color.WHITE);
        int width = 130;
        fonte.draw(game.getBatch(), "1000", Gdx.graphics.getWidth() / 2 - width, 100);

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
        System.out.println(floor);
        return floor;
    }
}
