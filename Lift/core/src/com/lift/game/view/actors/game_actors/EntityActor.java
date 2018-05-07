package com.lift.game.view.actors.game_actors;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.lift.game.LiftGame;
import com.lift.game.model.entities.EntityModel;

import javax.swing.text.html.parser.Entity;

import static com.lift.game.view.GameView.PIXEL_TO_METER;

/**
 * Holds a sprite's position based on the respective entity model.
 */
public abstract class EntityActor extends Actor {

	/**
	 * The sprite representing the view.
	 */
	Sprite sprite;

    /**
     * The actor represents this model.
     */
    EntityModel model;

	/**
	 * Constructs an actor in a given game.
	 * 
	 * @param game
	 *            The actor belongs to this game.
	 */
	protected EntityActor(LiftGame game,  EntityModel model) {
		sprite = createSprite(game);
		this.model = model;
	}

	/**
	 * Creates the view's sprite.
	 * 
	 * @param game
	 *            The view belongs to this game.
	 * @return This view's sprite.
	 */
	protected abstract Sprite createSprite(LiftGame game);

	/**
	 * Updates this view based on a certain model.
	 */
	private void update() {
		sprite.setCenter(model.getX() / PIXEL_TO_METER, model.getY() / PIXEL_TO_METER);
		sprite.setRotation(0f);
	}

    @Override
    public void draw(Batch batch, float parentAlpha) {
        this.update();
        sprite.draw(batch, parentAlpha);
    }
}
