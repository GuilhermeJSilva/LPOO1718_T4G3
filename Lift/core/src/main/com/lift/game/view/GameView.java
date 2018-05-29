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
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.lift.game.GameState;
import com.lift.game.LiftGame;
import com.lift.game.model.GameModel;
import com.lift.game.view.stages.*;
import com.lift.game.view.stages.GameStage.GameStage;

/**
 * Main view for the game.
 */
public class GameView extends ScreenAdapter {
    /**
     * How much meters does a pixel represent.
     */
    public final static float PIXEL_TO_METER = 0.0417f;
    /**
     * Used to debug the position of the physics fixtures.
     */
    private static final boolean DEBUG_PHYSICS = true;

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
    private  InputHandler inputHandler;

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
     * Menu stage.
     */
    private MenuStage menuStage;

    /**
     * Paused stage.
     */
    private PausedStage pausedStage;

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
     * @param liftGame Game the view belongs to.
     */
    public GameView(LiftGame liftGame) {
        this.game = liftGame;
        loadAssets();
        camera = createCamera();
        this.game_stage = new GameStage(this.game, this.camera);
        this.startStage = new StartStage(this.game, this.camera);
        this.endStage =  new EndStage(this.game, this.camera);
        this.menuStage = new MenuStage(this.game, this.camera);
        this.pausedStage = new PausedStage(this.game, this.camera);
        this.inputHandler = new InputHandler(game.getGameController());
        Gdx.input.setInputProcessor(this.menuStage);
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
        manager.load("elevator.png", Texture.class);
        manager.load("heart.png", Texture.class);
        manager.load("regular.png", Texture.class);
        manager.load("Plano de Fundo.png", Texture.class);
        manager.load("PLAY.png", Texture.class);
        manager.load("SCORE.png", Texture.class);
        manager.load("SETTINGS.png", Texture.class);
        manager.load("fundo.png", Texture.class);
        manager.load("structure.png", Texture.class);
        manager.load("SUN.png", Texture.class);
        manager.load("lifttitle.png", Texture.class);
        manager.load("PAUSE.png", Texture.class);
        manager.load("MUTE.png", Texture.class);
        manager.load("drunk.png", Texture.class);
        manager.load("pregnant.png", Texture.class);

        loadFonts(manager);
        manager.finishLoading();

    }

    /**
     * Loads the game fonts.
     * @param manager Asset manager to use.
     */
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
        game.getGameController().removeFlagged();

        if (game.getGameState() == GameState.Playing || game.getGameState() == GameState.EndScreen) {
            updateGame(delta);
        }

        resetCamera(delta);
        drawAllStages(delta);

        if (game.getGameModel().endGame() && game.getGameState() == GameState.Playing) {
            this.game.setGameState(GameState.EndScreen);
            this.endStage.update(this.game);
            this.game.getGamePreferences().updateHighScore(game.getGameModel().getScore().floatValue());
            System.out.println(game.getGameModel().getCoins());
            this.game.getGamePreferences().increaseCoins(game.getGameModel().getCoins());
            game.getGameModel().incCoins(-game.getGameModel().getCoins());
            Gdx.input.setInputProcessor(this.endStage);
        }

    }

    /**
     * Draws all stages.
     * @param delta Time since the last render.
     */
    private void drawAllStages(float delta) {
        if(game.getGameState() == GameState.InMenu)
            this.menuStage.draw();
        else {
            this.game_stage.draw();
            if(game.getGameState() == GameState.Paused)
                this.pausedStage.draw();
        }

        if (game.getGameState() == GameState.StartScreen) {
            if(this.startStage.update(delta) <= 0.0) {
                this.game.setGameState(GameState.Playing);
                Gdx.input.setInputProcessor(this.game_stage);
            }
            this.startStage.draw();
        } else if (game.getGameState() == GameState.EndScreen) {
            this.endStage.draw();
        }

        if (DEBUG_PHYSICS) {
            debugCamera = camera.combined.cpy();
            debugCamera.scl(1 / PIXEL_TO_METER);
            debugRenderer.render(game.getGameController().getWorld(), debugCamera);
        }
    }

    /**
     * Updates the game according
     * @param delta Time passed since the last render.
     */
    private void updateGame(float delta) {
        if(game.getGameState() == GameState.Playing)
            inputHandler.handleInputs();
        game.getGameController().update(this.game.getGameState(), delta);
        this.game_stage.updateStage(this.game, delta);
    }

    /**
     * Resets the camera to its initial state.
     */
    private void resetCamera(float delta) {
        camera.position.set(camera.viewportWidth / 2f, camera.viewportHeight / 2f, 0);
        camera.update();
        game.getSpriteBatch().setProjectionMatrix(camera.combined);
        game.getPolygonBatch().setProjectionMatrix(camera.combined);

        Gdx.gl.glClearColor(.135f, .206f, .235f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        game.getSpriteBatch().begin();
        drawBackground(delta);
        game.getSpriteBatch().end();
    }

    /**
     * Draws the background.
     */
    private void drawBackground(float delta) {
        float backgroundMovement = 0;
        if(game.getGameState() == GameState.Playing)
            backgroundMovement = delta * 20;
        else if(game.getGameState() !=  GameState.Paused)
            backgroundMovement = - delta * 400;
        game.getSpriteBatch().draw(this.game.getTextureManager().getBackground(backgroundMovement), 0,0);
        if (game.getGameState() != GameState.InMenu) {
            game.getSpriteBatch().draw(this.game.getTextureManager().getStructure(), 0,0);
            game.getSpriteBatch().draw(this.game.getTextureManager().getSun(), 0,0);
        }
    }

    public MenuStage getMenuStage() {
        return menuStage;
    }

    public void resetGameStages() {
        this.game.resetGame();
        this.inputHandler =  new InputHandler(this.game.getGameController());
        this.game_stage = new GameStage(this.game, this.camera);
        this.startStage = new StartStage(this.game, this.camera);
        this.endStage =  new EndStage(this.game, this.camera);
    }

    public GameModel getGameModel() {
        return game.getGameModel();
    }

    public GameStage getGame_stage() {
        return game_stage;
    }

    public PausedStage getPausedStage() {
        return pausedStage;
    }
}
