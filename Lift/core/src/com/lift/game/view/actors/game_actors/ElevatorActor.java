package com.lift.game.view.actors.game_actors;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.lift.game.LiftGame;
import com.lift.game.controller.entities.ElevatorBody;
import com.lift.game.model.entities.ElevatorModel;
import com.lift.game.view.actors.EntityActor;

import static com.lift.game.view.GameView.PIXEL_TO_METER;

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
    	super(game, model);
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
	      return new TextureRegion(elevatorTexture,(int)(ElevatorBody.width /2/PIXEL_TO_METER), (int)(ElevatorBody.height/2/PIXEL_TO_METER));
	}

}
