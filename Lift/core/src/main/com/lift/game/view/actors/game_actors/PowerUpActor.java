package com.lift.game.view.actors.game_actors;

import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.lift.game.controller.powerups.PowerUpState;
import com.lift.game.model.entities.EntityModel;
import com.lift.game.model.entities.PowerUpModel;
import com.lift.game.view.TextureManager;
import com.lift.game.view.actors.EntityActor;

import java.util.ArrayList;

import static com.lift.game.controller.powerups.types.BasicPowerUP.RADIUS_OF_THE_BODY;
import static com.lift.game.view.GameView.PIXEL_TO_METER;

/**
 * Actor to show power ups on screen.
 */
public class PowerUpActor extends EntityActor {
    /**
     * Array of positions for active power ups.
     */
    private static ArrayList<Vector2> activePositions;
    /**
     * Position counter for power ups.
     */
    private static Integer position_counter = 0;

    /**
     * Sets the positions for the active power ups.
     * @param activePositions Positions.
     */
    public static void setActivePositions(ArrayList<Vector2> activePositions) {
        PowerUpActor.activePositions = activePositions;
    }

    /**
     * Resets the position counter for the active power ups.
     */
    public static void resetPositionCounter() {
        position_counter = 0;
    }

    /**
     * Constructor for the actor.
     * @param model Model the actor is going to represent.
     *
     */
    public PowerUpActor(EntityModel model) {
        super(model);
        this.sprite = new Sprite(new Texture((int)(RADIUS_OF_THE_BODY * 2/ PIXEL_TO_METER), (int)(RADIUS_OF_THE_BODY * 2 / PIXEL_TO_METER), Pixmap.Format.RGB888));
        this.sprite.setRegion(TextureManager.getInstance().getPUTexture(((PowerUpModel) model).getPowerUpType()));
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        if (removed()) {
            this.update();
            if(((PowerUpModel) model).getPowerUpState() == PowerUpState.Active) {
                sprite.setPosition(activePositions.get(position_counter).x, activePositions.get(position_counter).y);
                position_counter++;
                position_counter %= activePositions.size();
            }
            sprite.draw(batch, parentAlpha);
        }
    }
}
