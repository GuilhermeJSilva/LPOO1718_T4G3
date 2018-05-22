package com.lift.game.view.actors.game_actors;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.lift.game.LiftGame;
import com.lift.game.controller.entities.PlatformBody;
import com.lift.game.model.entities.PlatformModel;
import com.lift.game.view.TextureManager;
import com.lift.game.view.actors.EntityActor;

import static com.lift.game.view.GameView.PIXEL_TO_METER;

public class PlatformActor extends EntityActor {

    /**
     * The platform's texture.
     */
    private TextureRegion platformRegion;

    /**
     * Constructs a platform model.
     * @param game Platform's game.
     */
    public PlatformActor(LiftGame game, PlatformModel model) {
        super(model);
        this.sprite = createSprite(game, model);
    }

    /**
     * Creates a sprite representing a platform.
     *
     * @param game the game this view belongs to
     * @return the sprite representing The platorm.
     */
    public Sprite createSprite(LiftGame game, PlatformModel model) {
        platformRegion = create_platform_region(game, model);
        return new Sprite(platformRegion);
    }


    /**
     * Creates a texture region.
     * @param game Uses the asset maaget from this game.
     * @return Texture region of the platform.
     */
    private TextureRegion create_platform_region(LiftGame game, PlatformModel model) {
        Texture platformTexture = game.getTextureManager().getPlatformTexture(model.getFloor_number());
        return new TextureRegion(platformTexture,(int)(PlatformBody.PLATFORM_LENGTH/PIXEL_TO_METER), (int)(PlatformBody.PLATFORM_HEIGHT/PIXEL_TO_METER));
    }

}
