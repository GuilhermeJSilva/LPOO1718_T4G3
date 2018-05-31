package com.lift.game.view;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.FileHandleResolver;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGeneratorLoader;
import com.badlogic.gdx.graphics.g2d.freetype.FreetypeFontLoader;

/**
 * Loads all game assets.
 */
public class GameAssetManager extends AssetManager {

    /**
     * Creates a new AssetManager with all default loaders.
     */
    public GameAssetManager() {
        super();
        this.loadAssets();
    }
    /**
     * Loads the assets needed by this screen.
     */
    private void loadAssets() {
        this.load("elevator.png", Texture.class);
        this.load("heart.png", Texture.class);
        this.load("regular.png", Texture.class);
        this.load("PLAY.png", Texture.class);
        this.load("SCORE.png", Texture.class);
        this.load("SETTINGS.png", Texture.class);
        this.load("fundo1-1.png", Texture.class);
        this.load("structure.png", Texture.class);
        this.load("SUN.png", Texture.class);
        this.load("lifttitle.png", Texture.class);
        this.load("pause1.png", Texture.class);
        this.load("sound.png", Texture.class);
        this.load("drunk.png", Texture.class);
        this.load("pregnant.png", Texture.class);
        this.load("replay.png", Texture.class);
        this.load("playbutton.png", Texture.class);

        this.load("blue.png", Texture.class);
        this.load("green.png", Texture.class);
        this.load("orange.png", Texture.class);
        this.load("purple.png", Texture.class);
        this.load("red.png", Texture.class);
        this.load("yellow.png", Texture.class);


        loadFonts();
        this.finishLoading();

    }

    /**
     * Loads the game fonts.
     */
    private void loadFonts() {
        FileHandleResolver resolver = new InternalFileHandleResolver();
        this.setLoader(FreeTypeFontGenerator.class, new FreeTypeFontGeneratorLoader(resolver));
        this.setLoader(BitmapFont.class, ".otf", new FreetypeFontLoader(resolver));

        FreetypeFontLoader.FreeTypeFontLoaderParameter mySmallFont = new FreetypeFontLoader.FreeTypeFontLoaderParameter();
        mySmallFont.fontFileName = "fonts/font2.otf";
        mySmallFont.fontParameters.size = 150;
        this.load("fonts/font2.otf", BitmapFont.class, mySmallFont);

        this.setLoader(BitmapFont.class, ".ttf", new FreetypeFontLoader(resolver));
        FreetypeFontLoader.FreeTypeFontLoaderParameter myBigFont = new FreetypeFontLoader.FreeTypeFontLoaderParameter();
        myBigFont.fontFileName = "fonts/font.ttf";
        myBigFont.fontParameters.size = 100;
        this.load("fonts/font.ttf", BitmapFont.class, myBigFont);
    }
}
