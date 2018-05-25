package com.lift.game;

import com.badlogic.gdx.Preferences;
import com.lift.game.model.GameModel;


/**
 * Manages all preferences.
 */
public class PreferenceManager {
    /**
     * Contains the preferences it manages.
     */
    private Preferences preferences;

    /**
     * Owner of the preferences.
     */
    private LiftGame game;

    /**
     * Constructs the Manager.
     *
     * @param preferences Preferences to manage.
     * @param game Owner of the manager.
     */
    public PreferenceManager(Preferences preferences, LiftGame game) {
        this.preferences = preferences;
        this.game =  game;
    }

    /**
     * Updates the high score.
     */
    public void updateHighScore(float new_score) {
        float highScore = preferences.getFloat("highscore", 0f);

        if (game.getGameModel().getScore() > highScore) {
            preferences.putFloat("highscore", game.getGameModel().getScore().floatValue());
            preferences.flush();

        }
    }

    /**
     * Return the game volume.
     * @return Game volume.
     */
    public float getVolume() {
        return preferences.getFloat("volume", 0.5f);
    }

    /**
     * Returns the game's highscore.
     * @return Highscore.
     */
    public float getHighscore() {
        return preferences.getFloat("highscore", 0.0f);
    }

    /**
     * Returns the game's coins.
     * @return Coins.
     */
    public int getCoins() {
        return preferences.getInteger("coins", 0);
    }

    /**
     * Increases the coins.
     * @param coins Increment.
     */
    public void increaseCoins(int coins) {
        preferences.putInteger("coins", getCoins() + coins);
        preferences.flush();
    }

    /**
     * Updates the saved volume.
     * @param volume New volume value.
     */
    public void updateVolume(float volume) {
        preferences.putFloat("volume",  volume);
    }
}
