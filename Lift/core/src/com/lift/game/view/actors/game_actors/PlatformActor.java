package com.lift.game.view.actors.game_actors;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.lift.game.LiftGame;
import com.lift.game.controller.entities.PlatformBody;
import com.lift.game.model.entities.PlatformModel;
import com.lift.game.view.actors.EntityActor;

import static com.lift.game.view.GameView.PIXEL_TO_METER;

public class PlatformActor extends EntityActor {

    /**
     * The platform's texture.
     */
    private TextureRegion paltormRegion;

    /**
     * Constructs a platform model.
     * @param game Platform's game.
     */
    public PlatformActor(LiftGame game, PlatformModel model) {
        super(game, model);
    }

    /**
     * Creates a sprite representing a platform.
     *
     * @param game the game this view belongs to
     * @return the sprite representing The platorm.
     */
    @Override
    public Sprite createSprite(LiftGame game) {
        paltormRegion = create_elevator_region(game);
        return new Sprite(paltormRegion);
    }

    /**
     * Creates a texture region
     * @param game
     * @return
     */
    private TextureRegion create_elevator_region(LiftGame game) {
        Texture elevatorTexture = game.getAssetManager().get("elevator.png");
        return new TextureRegion(elevatorTexture,(int)(PlatformBody.PLATFORM_LENGTH /2/PIXEL_TO_METER), (int)(PlatformBody.PLATFORM_HEIGHT /2/PIXEL_TO_METER));
    }

}
