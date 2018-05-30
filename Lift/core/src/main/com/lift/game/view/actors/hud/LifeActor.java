package com.lift.game.view.actors.hud;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.lift.game.model.GameModel;
import com.lift.game.view.TextureManager;

import java.util.ArrayList;

/**
 * Represents the life indicator.
 */
public class LifeActor extends Actor {

    /**
     * Heart positions.
     */
    private final ArrayList<Vector2> pos;

    /**
     * Sprite to be displayed.
     */
    private final Sprite sprite;

    /**
     * Region to be displayed.
     */
    private TextureRegion heartRegion;


    /**
     * Display according to this model.
     */
    private final GameModel gameModel;

    /**
     * Creates the actor.
     * @param camera Aligns according to this camera.
     * @param gameModel Model it is going to represent.
     */
    public LifeActor(Camera camera, GameModel gameModel) {

        this.sprite = createSprite();

        this.pos = new ArrayList<Vector2>();
        this.pos.add(new Vector2(camera.viewportWidth/2 - 3.5f*heartRegion.getRegionWidth()/2, camera.viewportHeight - camera.viewportHeight/8));
        this.pos.add(new Vector2(camera.viewportWidth/2 - heartRegion.getRegionWidth()/2, camera.viewportHeight - camera.viewportHeight/8));
        this.pos.add(new Vector2(camera.viewportWidth/2 + 1.5f*heartRegion.getRegionWidth()/2, camera.viewportHeight - camera.viewportHeight/8));
        this.gameModel = gameModel;
    }

    /**
     * Creates the sprite.
     * @return Created sprite.
     */
    private Sprite createSprite() {
        Texture lifeTexture = TextureManager.getInstance().getAssetManager().get("heart.png");
        heartRegion = new TextureRegion(lifeTexture);
        return new Sprite(heartRegion);
    }


    @Override
    public void draw(Batch batch, float parentAlpha) {
        for (int i = 0; i < gameModel.getLives() && i < this.pos.size(); i++) {
            this.sprite.setPosition(this.pos.get(i).x,this.pos.get(i).y);
            this.sprite.draw(batch, parentAlpha);
        }

    }
}

