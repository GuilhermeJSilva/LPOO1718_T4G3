package main.com.lift.game.view.actors;

import static main.com.lift.game.view.GameView.PIXEL_TO_METER;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Actor;

import main.com.lift.game.model.entities.EntityModel;

/**
 * Holds a sprite's position based on the respective entity model.
 */
public abstract class EntityActor extends Actor {

	/**
	 * The sprite representing the view.
	 */
	protected Sprite sprite;

    /**
     * The actor represents this model.
     */
    protected EntityModel model;


    protected EntityActor(EntityModel model) {
	    this.model = model;
    }


	/**
	 * Updates this view based on a certain model.
	 */
	protected void update() {
		sprite.setCenter(model.getX() / PIXEL_TO_METER, model.getY() / PIXEL_TO_METER);
		sprite.setRotation((float)Math.toDegrees(model.getRotation()));
	}

    @Override
    public void draw(Batch batch, float parentAlpha) {
        if (removed()) {
            this.update();
            sprite.draw(batch, parentAlpha);
        }
    }

    protected boolean removed() {
	    if(model.isFlaggedForRemoval()) {
            this.getParent().removeActor(this);
	        return false;
        }
        return true;
    }
}
