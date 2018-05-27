package main.com.lift.game.view.actors.game_actors;
import static main.com.lift.game.view.GameView.PIXEL_TO_METER;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import main.com.lift.game.LiftGame;
import main.com.lift.game.controller.entities.ElevatorBody;
import main.com.lift.game.model.entities.ElevatorModel;
import main.com.lift.game.view.actors.EntityActor;

/**
 * A view representing an elevator.
 */
public class ElevatorActor extends EntityActor {
	/**
     * The elevator's texture.
     */
    private TextureRegion elevatorRegion;
    
    /**
     * Constructs a elevator model.
     * @param game Elevator's game.
     */
    public ElevatorActor(LiftGame game, ElevatorModel model) {
    	super(model);
    	this.sprite = createSprite(game);
    }
    
    /**
     * Creates a sprite representing this elevator.
     *
     * @param game the game this view belongs to
     * @return the sprite representing The elevator.
     */
    public Sprite createSprite(LiftGame game) {
        elevatorRegion = create_elevator_region(game);
        return new Sprite(elevatorRegion);
    }

    /**
     * Creates a texture region.
     * @param game Game.
     * @return Elevator texture region.
     */
	private TextureRegion create_elevator_region(LiftGame game) {
		  Texture elevatorTexture = game.getAssetManager().get("elevator.png");
	      return new TextureRegion(elevatorTexture,(int)(ElevatorBody.width/PIXEL_TO_METER), (int)(ElevatorBody.height/PIXEL_TO_METER));
	}

}
