package com.lift.game.view.entities;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.lift.game.LiftGame;

/**
 * A view representing an elevator.
 */
public class ElevatorView extends EntityView{
	/**
     * The elevator's texture.
     */
    private TextureRegion elevatorRegion;
    
    /**
     * Constructs a elevator model.
     * @param game Elevator's game.
     */
    public ElevatorView(LiftGame game) {
    	super(game);
    }
    
    /**
     * Creates a sprite representing this elevator.
     *
     * @param game the game this view belongs to
     * @return the sprite representing The elevator.
     */
    @Override
    public Sprite createSprite(LiftGame game) {
        elevatorRegion = create_elevator_region(game);

        return new Sprite(elevatorRegion);
    }

    /**
     * Creates a texture region
     * @param game
     * @return
     */
	private TextureRegion create_elevator_region(LiftGame game) {
		  Texture elevatorTexture = game.getAssetManager().get("elevator.png");
		  //System.out.println(elevatorTexture.getWidth()*PIXEL_TO_METER + " "+ elevatorTexture.getHeight()*PIXEL_TO_METER);
	      return new TextureRegion(elevatorTexture,elevatorTexture.getWidth(), elevatorTexture.getHeight());
	}

}
