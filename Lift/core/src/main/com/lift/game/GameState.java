package com.lift.game;

/**
 * Describes the state of the game.
 */
public enum GameState {
    /**
     * The game is in the screen before the game starts.
     */
    StartScreen,
    /**
     * A game is occurring but is paused.
     */
    Paused,
    /**
     * A games is occurring.
     */
    Playing,
    /**
     * A game is in the game screen, a game just finished.
     */
    EndScreen,
    /**
     * The game is in the main menu.
     */
    InMenu
}
