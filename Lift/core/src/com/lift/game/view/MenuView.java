package com.lift.game.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.lift.game.LiftGame;
import com.lift.game.view.stages.MenuStage;

public class MenuView extends ScreenAdapter {
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
        manager.finishLoading();
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
        game.getBatch().setProjectionMatrix(camera.combined);

        Gdx.gl.glClearColor(.135f, .206f, .235f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        game.getBatch().begin();
        this.drawBackground();
        game.getBatch().end();
        this.menuStage.draw();
    }


    /**
     * Draws the background.
     */
    private void drawBackground() {
        Texture background = game.getAssetManager().get("Plano de Fundo.png", Texture.class);
        background.setWrap(Texture.TextureWrap.ClampToEdge, Texture.TextureWrap.ClampToEdge);
        game.getBatch().draw(background, 0, 0, 0, 0, (int) (VIEWPORT_WIDTH/PIXEL_TO_METER), (int) (VIEWPORT_HEIGHT / PIXEL_TO_METER));
    }

}
