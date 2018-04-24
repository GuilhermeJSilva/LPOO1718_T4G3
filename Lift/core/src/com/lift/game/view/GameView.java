package com.lift.game.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.lift.game.LiftGame;
import com.lift.game.controller.GameController;
import com.lift.game.model.GameModel;
import com.lift.game.model.entities.ElevatorModel;
import com.lift.game.view.entities.ElevatorView;
import com.lift.game.view.entities.EntityView;

public class GameView extends ScreenAdapter {
	/**
	 * Used to debug the position of the physics fixtures
	 */
	private static final boolean DEBUG_PHYSICS = true;

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
        //OrthographicCamera camera = new OrthographicCamera(VIEWPORT_WIDTH / PIXEL_TO_METER, (VIEWPORT_HEIGHT_PER_FLOOR * GameModel.getInstance().getN_levels() + 5) / PIXEL_TO_METER);
    	System.out.println(VIEWPORT_WIDTH/PIXEL_TO_METER);
    	System.out.println(VIEWPORT_HEIGHT/PIXEL_TO_METER);
        OrthographicCamera camera = new OrthographicCamera(VIEWPORT_WIDTH/PIXEL_TO_METER, VIEWPORT_HEIGHT/PIXEL_TO_METER);
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


        handleInputs(delta);

        GameController.getInstance().update(delta);
    	camera.position.set(camera.viewportWidth / 2f, camera.viewportHeight / 2f, 0);
        camera.update();
        game.getBatch().setProjectionMatrix(camera.combined);

        game.getBatch().begin();
        drawBackground();
        drawEntities();
        game.getBatch().end();

        if (DEBUG_PHYSICS) {
            debugCamera = camera.combined.cpy();
            debugCamera.scl(1 / PIXEL_TO_METER);
            debugRenderer.render(GameController.getInstance().getWorld(), debugCamera);
        }
    }
    
    /**
     * Draws the background.
     */
    private void drawBackground() {
        Texture background = game.getAssetManager().get("background.png", Texture.class);
        background.setWrap(Texture.TextureWrap.ClampToEdge, Texture.TextureWrap.ClampToEdge);
        game.getBatch().draw(background, 0, 0, 0, 0, (int)(VIEWPORT_WIDTH/PIXEL_TO_METER), (int) (VIEWPORT_HEIGHT/PIXEL_TO_METER));
    }
    
    /**
     * Draws the entities to the screen.
     */
    
    private void drawEntities() {

        ElevatorModel elevator = GameModel.getInstance().getElevator();
        EntityView view = new ElevatorView(game);
        view.update(elevator);
        view.draw(game.getBatch());
    }
    
    /**
     * Handles any inputs and passes them to the controller.
     *
     * @param delta time since last time inputs where handled in seconds
     */
    private void handleInputs(float delta) {
        if (Gdx.input.isTouched() || Gdx.input.isButtonPressed(Input.Buttons.LEFT)) {
        	float tmp = Gdx.graphics.getHeight() * 4f / VIEWPORT_HEIGHT;
        	//System.out.println((Gdx.graphics.getHeight() - Gdx.input.getY() - tmp));
        	if((Gdx.graphics.getHeight() - Gdx.input.getY() - tmp) < 0)
        		return;
        	int floor = (int)( (Gdx.graphics.getHeight() - Gdx.input.getY() - tmp)/((Gdx.graphics.getHeight() - tmp)/GameModel.getInstance().getN_levels())); 
        	//System.out.println("Button pressed, target: "+ floor);
			GameController.getInstance().getElevator().setTarget_floor(floor);
        }
    }
}
