package com.lift.game.view.actors.game_actors.person;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.lift.game.LiftGame;
import com.lift.game.controller.entities.ElevatorBody;
import com.lift.game.controller.entities.PersonBody;
import com.lift.game.model.entities.EntityModel;
import com.lift.game.view.actors.game_actors.EntityActor;

import static com.lift.game.view.GameView.PIXEL_TO_METER;

public class PersonActor extends EntityActor {

    /**
     * The person's texture.
     */
    private TextureRegion personRegion;
    
    public PersonActor(LiftGame game, EntityModel model) {
        super(game, model);
        this.setBounds(this.sprite.getX(),this.sprite.getY(),personRegion.getRegionWidth(), personRegion.getRegionHeight());

        if( ! this.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                System.out.println("Clicked at: " + x + " " + y);
            }
        })) {
            System.out.println("Failed to install listener");
        }
    }

    @Override
    public Sprite createSprite(LiftGame game) {
        personRegion = create_person_region(game);
        return new Sprite(personRegion);
    }

    private TextureRegion create_person_region(LiftGame game) {
        Texture elevatorTexture = game.getAssetManager().get("elevator.png");
        return new TextureRegion(elevatorTexture,(int)(PersonBody.WIDTH /2/PIXEL_TO_METER), (int)(PersonBody.HEIGHT/2/PIXEL_TO_METER));
    }

    @Override
    protected void update() {
        super.update();
        this.setBounds(this.sprite.getX(),this.sprite.getY(),personRegion.getRegionWidth(), personRegion.getRegionHeight());
    }
}
