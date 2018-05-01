package com.lift.game.view.entities;

import static com.lift.game.view.GameView.PIXEL_TO_METER;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.lift.game.LiftGame;
import com.lift.game.model.entities.EntityModel;

/**
 * Holds a sprite's position based on the respective entity model.
 */
public abstract class EntityView {

	/**
	 * The sprite representing the view.
	 */
	Sprite sprite;

	/**
	 * Constructs a view in a given game.
	 * 
	 * @param game
	 *            The view belongs to this game.
	 */
	public EntityView(LiftGame game) {
		sprite = createSprite(game);
	}

	/**
	 * Draws the sprite using a sprite batch.
	 * 
	 * @param batch
	 *            The sprite branch used for drawing.
	 */
	public void draw(SpriteBatch batch) {
		sprite.draw(batch);
	}

	/**
	 * Creates the view's sprite.
	 * 
	 * @param game
	 *            The view belongs to this game.
	 * @return This view's sprite.
	 */
	public abstract Sprite createSprite(LiftGame game);

	/**
	 * Updates this view based on a certain model.
	 *
	 * @param model
	 *            the model used to update this view
	 */
	public void update(EntityModel model) {
		sprite.setCenter(model.getX() / PIXEL_TO_METER, model.getY() / PIXEL_TO_METER);
		sprite.setRotation(0f);
	}
}
