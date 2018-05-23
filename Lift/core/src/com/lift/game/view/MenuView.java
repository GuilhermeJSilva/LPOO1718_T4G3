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
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.lift.game.LiftGame;
import com.lift.game.view.stages.MenuStage;

public class MenuView extends ScreenAdapter {
    /**
     * How much meters does a pixel represent.
     */
    private final static float PIXEL_TO_METER = 0.0417f;
    /**
     * The width of the viewport in meters.
     */
    private static final float VIEWPORT_WIDTH = 45;
    /**
     * The height of the viewport in meters.
     */
    private static final float VIEWPORT_HEIGHT = 80;
    
    /**
     * The camera used to show the viewport.
     */
    private final OrthographicCamera camera;

    private LiftGame game;

    private Stage menuStage;

    public MenuView(LiftGame game) {
        this.game = game;
        loadAssets();
        camera = this.createCamera();
        menuStage = new MenuStage(this.game, this.camera);
    }

    private void loadAssets() {
        AssetManager manager = this.game.getAssetManager();
        manager.load("Plano de Fundo.png", Texture.class);
        manager.load("PLAY.png", Texture.class);
        manager.load("SCORE.png", Texture.class);
        manager.load("SETTINGS.png", Texture.class);
        manager.load("fundo.png", Texture.class);
        manager.load("structure.png", Texture.class);
        manager.load("SUN.png", Texture.class);
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
     * Creates the camera used to show the viewport.
     *
     * @return the camera
     */
    private OrthographicCamera createCamera() {
        OrthographicCamera camera = new OrthographicCamera(VIEWPORT_HEIGHT / PIXEL_TO_METER * ((float) Gdx.graphics.getWidth() / (float) Gdx.graphics.getHeight()), VIEWPORT_HEIGHT / PIXEL_TO_METER);
        camera.position.set(camera.viewportWidth / 2f, camera.viewportHeight / 2f, 0);
        camera.update();

        return camera;
    }

    @Override
    public void render(float delta) {
        camera.position.set(camera.viewportWidth / 2f, camera.viewportHeight / 2f, 0);
        camera.update();
        game.getSpriteBatch().setProjectionMatrix(camera.combined);

        Gdx.gl.glClearColor(.135f, .206f, .235f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        game.getSpriteBatch().begin();
        this.drawBackground();
        game.getSpriteBatch().end();
        this.menuStage.draw();
    }


    /**
     * Draws the background.
     */
    private void drawBackground() {
        Texture background = game.getAssetManager().get("Plano de Fundo.png", Texture.class);
        background.setWrap(Texture.TextureWrap.ClampToEdge, Texture.TextureWrap.ClampToEdge);
        game.getSpriteBatch().draw(background, 0, 0, 0, 0, (int) (VIEWPORT_WIDTH/PIXEL_TO_METER), (int) (VIEWPORT_HEIGHT / PIXEL_TO_METER));
    }

}
