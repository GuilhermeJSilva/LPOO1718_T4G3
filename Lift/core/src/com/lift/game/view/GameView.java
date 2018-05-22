package com.lift.game.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.FileHandleResolver;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGeneratorLoader;
import com.badlogic.gdx.graphics.g2d.freetype.FreetypeFontLoader;
import com.badlogic.gdx.graphics.g3d.Shader;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.lift.game.LiftGame;
import com.lift.game.controller.GameController;
import com.lift.game.model.GameModel;
import com.lift.game.model.entities.PlatformModel;
import com.lift.game.view.stages.EndStage;
import com.lift.game.view.stages.GameStage;
import com.lift.game.view.stages.HudStage;
import com.lift.game.view.stages.StartStage;

import java.util.ArrayList;

public class GameView extends ScreenAdapter {
    /**
     * How much meters does a pixel represent.
     */
    public final static float PIXEL_TO_METER = 0.0417f;
    /**
     * Used to debug the position of the physics fixtures.
     */
    private static final boolean DEBUG_PHYSICS = false;

    /**
     * The height of the viewport in meters.
     */
    public static final float VIEWPORT_HEIGHT = 80;

    /**
     * The game this screen belongs to.
     */
    private final LiftGame game;

    /**
     * The camera used to show the viewport.
     */
    private final OrthographicCamera camera;

    /**
     * Handles the basic inputs.
     */
    private final InputHandler inputHandler = new InputHandler();

    /**
     * Stage for the hud.
     */
    private HudStage hud;

    /**
     * Stage for all game entities.
     */
    private GameStage game_stage;

    /**
     * Stage for the start of the game.
     */
    private StartStage startStage;

    /**
     * Stage for the end of the game.
     */
    private EndStage endStage;

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
     * State of the game.
     */
    private GameState gameState;

    /**
     * Shader to blur the game when the player is in the menus.
     */
    private Shader blurShader;

    /**
     * Creates this screen.
     *
     * @param liftGame Game the view belongs to.
     */
    public GameView(LiftGame liftGame) {
        this.game = liftGame;
        loadAssets();
        camera = createCamera();
        this.hud = new HudStage(this.game, this.camera);
        this.game_stage = new GameStage(this.game, this.camera);
        this.startStage = new StartStage(this.game, this.camera);
        this.endStage =  new EndStage(this.game, this.camera);
        this.gameState = GameState.StartScreen;
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
        manager.load("heart.png", Texture.class);
        manager.load("gajos.png", Texture.class);
        manager.finishLoading();

    }



    /**
     * Renders this screen.
     *
     * @param delta time since last renders in seconds.
     */
    @Override
    public void render(float delta) {
        GameController.getInstance().removeFlagged();

        if (gameState == GameState.Playing) {
            updateGame(delta);
        }

        resetCamera();
        drawAllStages(delta);

        if (checkEndGame() && gameState == GameState.Playing) {
            this.gameState = GameState.EndScreen;
            this.endStage.update();
            float highScore = game.getGamePreferences().getFloat("highscore", 0f);

            if(GameModel.getInstance().getScore() > highScore) {
                System.out.println(GameModel.getInstance().getScore() + " " + highScore);
                game.getGamePreferences().putFloat("highscore", GameModel.getInstance().getScore().floatValue());
                game.getGamePreferences().flush();
            }
            Gdx.input.setInputProcessor(this.endStage);
        }

    }

    private void drawAllStages(float delta) {
        this.game_stage.draw();
        this.hud.draw();

        if (gameState == GameState.StartScreen) {
            if(this.startStage.update(delta) <= 0.0) {
                this.gameState = GameState.Playing;
                Gdx.input.setInputProcessor(this.game_stage);
            }
            this.startStage.draw();
        } else if (gameState == GameState.EndScreen) {
            this.endStage.draw();
        }

        if (DEBUG_PHYSICS) {
            debugCamera = camera.combined.cpy();
            debugCamera.scl(1 / PIXEL_TO_METER);
            debugRenderer.render(GameController.getInstance().getWorld(), debugCamera);
        }
    }

    private boolean checkEndGame() {
        return GameModel.getInstance().getTime_left() <= 0 || GameModel.getInstance().getLives() <= 0;
    }

    private void updateGame(float delta) {
        inputHandler.handleInputs();
        GameController.getInstance().update(delta);
        this.game_stage.updateStage(this.game);
        this.hud.updateStage(delta / 5);
    }

    private void resetCamera() {
        camera.position.set(camera.viewportWidth / 2f, camera.viewportHeight / 2f, 0);
        camera.update();
        game.getSpriteBatch().setProjectionMatrix(camera.combined);
        game.getPolygonBatch().setProjectionMatrix(camera.combined);

        Gdx.gl.glClearColor(.135f, .206f, .235f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        game.getSpriteBatch().begin();
        drawBackground();
        game.getSpriteBatch().end();
    }

    /**
     * Draws the background.
     */
    private void drawBackground() {
        Texture background = game.getAssetManager().get("lift4.png", Texture.class);
        background.setWrap(Texture.TextureWrap.ClampToEdge, Texture.TextureWrap.ClampToEdge);
        game.getSpriteBatch().draw(background, 0, 0, 0, 0, (int) (camera.viewportWidth), (int) (camera.viewportHeight));
    }


}
