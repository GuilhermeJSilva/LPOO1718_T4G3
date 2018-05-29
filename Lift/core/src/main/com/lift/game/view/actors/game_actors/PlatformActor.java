package com.lift.game.view.actors.game_actors;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.lift.game.LiftGame;
import com.lift.game.controller.entities.PlatformBody;
import com.lift.game.model.entities.PlatformModel;
import com.lift.game.view.actors.EntityActor;
import com.lift.game.view.clickListeners.FloorClick;

import static com.lift.game.controller.GameController.METERS_PER_FLOOR;
import static com.lift.game.view.GameView.PIXEL_TO_METER;

/**
 * Visually represents a platform.
 */
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
        this.update();
        this.addCaptureListener(new FloorClick(game, model.getFloor_number(),model.getSide()));
    }

    /**
     * Creates a sprite representing a platform.
     *
     * @param game the game this view belongs to
     * @return the sprite representing The platform.
     */
    public Sprite createSprite(LiftGame game, PlatformModel model) {
        Texture platformTexture = game.getTextureManager().getPlatformTexture(model.getFloor_number());
        TextureRegion textureRegion = new TextureRegion(platformTexture,(int)(PlatformBody.PLATFORM_LENGTH/PIXEL_TO_METER), (int)(PlatformBody.PLATFORM_HEIGHT/PIXEL_TO_METER));
        return new Sprite(textureRegion);
    }

    /**
     * Updates this view based on a certain model.
     */
    @Override
    protected void update() {
        sprite.setCenter(model.getX() / PIXEL_TO_METER, model.getY() / PIXEL_TO_METER);
        sprite.setRotation((float)Math.toDegrees(model.getRotation()));
        this.setBounds(sprite.getX(), sprite.getY(), sprite.getWidth(), METERS_PER_FLOOR/PIXEL_TO_METER);
    }
}
