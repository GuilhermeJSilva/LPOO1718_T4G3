package com.lift.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.g2d.PolygonSpriteBatch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.lift.game.controller.GameController;
import com.lift.game.model.GameModel;
import com.lift.game.view.GameView;
import com.lift.game.view.TextureManager;

import javax.activity.ActivityCompletedException;

/**
 * The game main class.
 */
public class LiftGame extends Game {
    /**
     * Batch to print polygon.
     */
    private PolygonSpriteBatch polygonBatch;

    /**
     * Batch to print sprites.
     */
    private SpriteBatch spriteBatch;

    /**
     * Game music.
     */
    private Music music;

    /**
     * Preference manager of the game.
     */
    private PreferenceManager gamePreferences;

    /**
     * Model of the game.
     */
    private GameModel gameModel;

    /**
     * Controller of the game.
     */
    private GameController gameController;

    /**
     * State of the game.
     */
    private GameState gameState;

    /**
     * Interface for google play services.
     */
    private PlayInterface googlePlay;

    public LiftGame(PlayInterface googlePlay) {
        this.googlePlay = googlePlay;
    }


    /**
     * Creates the game. Initializes the sprite spriteBatch and asset manager.
     * Also starts the game until we have a main menu.
     */
    @Override
    public void create() {
        spriteBatch = new SpriteBatch();
        polygonBatch = new PolygonSpriteBatch();
        music = Gdx.audio.newMusic(Gdx.files.internal("themesong.mp3"));
        gamePreferences = new PreferenceManager(Gdx.app.getPreferences("Game Preferences"), this);
        this.gameState = GameState.InMenu;
        this.gameModel = new GameModel();
        this.gameController = new GameController(this.gameModel);
        startGame();
    }

    /**
     * Starts the game.
     */
    private void startGame() {
        setScreen(new GameView(this));
        //music.play();
        music.setVolume(gamePreferences.getVolume());
    }

    /**
     * Disposes of all assets.
     */
    @Override
    public void dispose() {
        spriteBatch.dispose();
        polygonBatch.dispose();
        TextureManager.getInstance().dispose();
    }

    /**
     * Returns the sprite spriteBatch used to improve drawing performance.
     *
     * @return the sprite spriteBatch
     */
    public SpriteBatch getSpriteBatch() {
        return spriteBatch;
    }

    /**
     * Returns the polygon batch.
     *
     * @return Polygon batch.
     */
    public PolygonSpriteBatch getPolygonBatch() {
        return polygonBatch;
    }

    /**
     * Returns the game preferences.
     *
     * @return Game preferences.
     */
    public PreferenceManager getGamePreferences() {
        return gamePreferences;
    }

    /**
     * Returns the state of the game.
     *
     * @return State of the game.
     */
    public GameState getGameState() {
        return gameState;
    }

    /**
     * Changes the game state.
     *
     * @param gameState New value for the game state.
     */
    public void setGameState(GameState gameState) {
        this.gameState = gameState;
    }

    /**
     * Returns the music that its playing.
     *
     * @return Music that its playing.
     */
    public Music getMusic() {
        return music;
    }

    /**
     * Returns the game model.
     *
     * @return Game model.
     */
    public GameModel getGameModel() {
        return gameModel;
    }

    /**
     * Returns the game controller.
     *
     * @return Game controller.
     */
    public GameController getGameController() {
        return gameController;
    }

    /**
     * Resets the game's model and controller.
     */
    public void resetGame() {
        this.gameModel = new GameModel();
        this.gameController = new GameController(this.gameModel);
    }

    /**
     * Returns the play interface.
     * @return Play interface.
     */
    public PlayInterface getGooglePlay() {
        return googlePlay;
    }
}