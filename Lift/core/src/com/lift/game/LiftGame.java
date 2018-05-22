package com.lift.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.g2d.PolygonSpriteBatch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.lift.game.controller.GameController;
import com.lift.game.model.GameModel;
import com.lift.game.view.MenuView;
import com.lift.game.view.TextureManager;

/**
 * The game main class.
 */
public class LiftGame extends Game {
    private PolygonSpriteBatch polygonBatch;
    private SpriteBatch spriteBatch;
	private AssetManager assetManager;
	private TextureManager textureManager;
    private Music music;
    private Preferences gamePreferences;

    /**
     * Creates the game. Initializes the sprite spriteBatch and asset manager.
     * Also starts the game until we have a main menu.
     */
	@Override
	public void create () {
		spriteBatch = new SpriteBatch();
        polygonBatch = new PolygonSpriteBatch();
		assetManager = new AssetManager();
        music = Gdx.audio.newMusic(Gdx.files.internal("themesong.mp3"));
        gamePreferences = Gdx.app.getPreferences("Game Preferences");
        startGame();
	}

    /**
     * Starts the game.
     */
    private void startGame() {
        setScreen(new MenuView(this));
        music.play();
        music.setVolume(gamePreferences.getFloat("volume", 0.5f));
    }

    /**
     * Disposes of all assets.
     */
    @Override
	public void dispose () {
		spriteBatch.dispose();
        polygonBatch.dispose();
		assetManager.dispose();
	}

    /**
     * Returns the asset manager used to load all textures and sounds.
     *
     * @return the asset manager
     */
	public AssetManager getAssetManager() {
		return assetManager;
	}

    /**
     * Returns the sprite spriteBatch used to improve drawing performance.
     *
     * @return the sprite spriteBatch
     */
	public SpriteBatch getSpriteBatch() {
		return spriteBatch;
	}

    public PolygonSpriteBatch getPolygonBatch() {
        return polygonBatch;
    }

    public void resetGame() {
        GameModel.resetModel();
        GameController.resetController();
    }

    public TextureManager getTextureManager() {
	    if(textureManager == null)
	        textureManager = new TextureManager(this);
        return textureManager;
    }

    public Preferences getGamePreferences() {
        return gamePreferences;
    }
}