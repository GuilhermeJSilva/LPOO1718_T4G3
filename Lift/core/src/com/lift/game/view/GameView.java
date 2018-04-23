package com.lift.game.view;

import static com.lift.game.controller.GameController.BUILDING_HEIGHT;
import static com.lift.game.controller.GameController.BUILDING_WIDTH;

import javax.swing.text.ViewFactory;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.lift.game.LiftGame;
import com.lift.game.controller.GameController;
import com.lift.game.model.GameModel;
import com.lift.game.model.entities.ElevatorModel;

public class GameView extends ScreenAdapter {
	/**
	 * Used to debug the position of the physics fixtures
	 */
	private static final boolean DEBUG_PHYSICS = false;

	/**
	 * How much meters does a pixel represent.
	 */
	public final static float PIXEL_TO_METER = 0.04f;

	/**
	 * The width of the viewport in meters. The height is automatically calculated
	 * using the screen ratio.
	 */
	private static final float VIEWPORT_WIDTH = 45;

	/**
	 * The game this screen belongs to.
	 */
	private final LiftGame game;

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
	 * @param game
	 *            The game this screen belongs to
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
        OrthographicCamera camera = new OrthographicCamera(VIEWPORT_WIDTH / PIXEL_TO_METER, VIEWPORT_WIDTH / PIXEL_TO_METER * ((float) Gdx.graphics.getHeight() / (float)Gdx.graphics.getWidth()));

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
        this.game.getAssetManager().load( "background.png" , Texture.class);
        this.game.getAssetManager().load( "elevator.png" , Texture.class);

        this.game.getAssetManager().finishLoading();
    }
    
    /**
     * Renders this screen.
     *
     * @param delta time since last renders in seconds.
     */
    @Override
    public void render(float delta) {
        //GameController.getInstance().removeFlagged();
        //GameController.getInstance().generateNewPeople();

        //handleInputs(delta);

        //GameController.getInstance().update(delta);
    	camera.position.set(camera.viewportWidth / 2f, camera.viewportHeight / 2f, 0);
        camera.update();
        game.getBatch().setProjectionMatrix(camera.combined);
        
        Gdx.gl.glClearColor( 103/255f, 69/255f, 117/255f, 1 );
        Gdx.gl.glClear( GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT );

        game.getBatch().begin();
        drawBackground();
        //drawEntities();
        game.getBatch().end();

        if (DEBUG_PHYSICS) {
            debugCamera = camera.combined.cpy();
            debugCamera.scl(1 / PIXEL_TO_METER);
            debugRenderer.render(GameController.getInstance().getWorld(), debugCamera);
        }
    }
    
    /**
     * Draws the background
     */
    private void drawBackground() {
        Texture background = game.getAssetManager().get("background.png", Texture.class);
        background.setWrap(Texture.TextureWrap.ClampToEdge, Texture.TextureWrap.ClampToEdge);
        game.getBatch().draw(background, 0, 0, 0, 0, (int)(BUILDING_WIDTH / PIXEL_TO_METER), (int) (BUILDING_HEIGHT / PIXEL_TO_METER));
    }
    
    /**
     * Draws the entities to the screen.
     */
    /*
    private void drawEntities() {

        ElevatorModel ship = GameModel.getInstance().getElevator();
        EntityView view = ViewFactory.makeView(game, ship);
        view.update(ship);
        view.draw(game.getBatch());
    }*/
}
