package com.lift.game.view.actors.hub;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.lift.game.LiftGame;
import com.lift.game.model.GameModel;

import java.util.ArrayList;

//TODO Only one sprite????
public class LifeActor extends Actor {

    private ArrayList<Sprite> sprite;

    private TextureRegion heartRegion;

    public LifeActor(LiftGame game, Camera camera) {

        heartRegion = create_elevator_region(game);
        this.sprite = new ArrayList<Sprite>();
        this.sprite.add(createSprite(game, camera.viewportWidth/2 - 3.5f*heartRegion.getRegionWidth()/2, camera.viewportHeight - camera.viewportHeight/8));
        this.sprite.add(createSprite(game, camera.viewportWidth/2 - heartRegion.getRegionWidth()/2, camera.viewportHeight - camera.viewportHeight/8));
        this.sprite.add(createSprite(game, camera.viewportWidth/2 + 1.5f*heartRegion.getRegionWidth()/2, camera.viewportHeight - camera.viewportHeight/8));
    }


    public Sprite createSprite(LiftGame game, float x, float y) {
        Sprite tmp = new Sprite(heartRegion);
        tmp.setPosition(x,y);
        return tmp;
    }

    private TextureRegion create_elevator_region(LiftGame game) {
        Texture lifeTexture = game.getAssetManager().get("heart.png");
        return new TextureRegion(lifeTexture);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        int n_lives = GameModel.getInstance().getLives();
        for (int i = 0; i < n_lives; i++) {
            this.sprite.get(i).draw(batch, parentAlpha);
        }

    }
}

