package com.lift.game.view.actors.game_actors;

import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.lift.game.LiftGame;
import com.lift.game.controller.entities.PersonBody;
import com.lift.game.model.entities.EntityModel;
import com.lift.game.model.entities.PowerUpModel;
import com.lift.game.view.actors.EntityActor;

import static com.lift.game.controller.powerups.types.BasicPowerUP.RADIUS_OF_THE_BODY;
import static com.lift.game.view.GameView.PIXEL_TO_METER;

/**
 * Actor to show power ups on screen.
 */
public class PowerUpActor extends EntityActor {
    /**
     * Constructor for the actor.
     * @param model Model the actor is going to represent.
     * @param game Game the actors belongs to.
     */
    public PowerUpActor(LiftGame game, EntityModel model) {
        super(model);
        this.sprite = new Sprite(new Texture((int)(RADIUS_OF_THE_BODY * 2/ PIXEL_TO_METER), (int)(RADIUS_OF_THE_BODY * 2 / PIXEL_TO_METER), Pixmap.Format.RGB888));
        this.sprite.setRegion(game.getTextureManager().getPUTexture(((PowerUpModel) model).getPowerUpType()));
    }
}
