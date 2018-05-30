package com.lift.game.view.actors.game_actors;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.lift.game.controller.entities.ElevatorBody;
import com.lift.game.model.entities.ElevatorModel;
import com.lift.game.view.TextureManager;
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
     * @param model Model drawn by this actor.
     */
    public ElevatorActor(ElevatorModel model) {
    	super(model);
    	this.sprite = createSprite();
    }
    
    /**
     * Creates a sprite representing this elevator.
     *
     * @return the sprite representing The elevator.
     */
    public Sprite createSprite() {
        elevatorRegion = create_elevator_region();
        return new Sprite(elevatorRegion);
    }

    /**
     * Creates a texture region.
     * @return Elevator texture region.
     */
	private TextureRegion create_elevator_region() {
		  Texture elevatorTexture = TextureManager.getInstance().getAssetManager().get("elevator.png");
	      return new TextureRegion(elevatorTexture,(int)(ElevatorBody.ELEVATOR_WIDTH /PIXEL_TO_METER), (int)(ElevatorBody.ELEVATOR_HEIGHT /PIXEL_TO_METER));
	}

}
