package com.lift.game.view.actors.game_actors.person;

import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.lift.game.LiftGame;
import com.lift.game.controller.entities.PersonBody;
import com.lift.game.model.entities.EntityModel;
import com.lift.game.model.entities.person.PersonModel;
import com.lift.game.view.actors.EntityActor;
import com.lift.game.view.actors.polygon_actor.DiamondPoly;

import static com.lift.game.view.GameView.PIXEL_TO_METER;

public class PersonActor extends EntityActor {

    /**
     * The person's texture.
     */
    private TextureRegion personRegion;

    private DiamondPoly patientIndicator;

    private PersonModel model;

    public PersonActor(LiftGame game, PersonModel model) {
        super(game, model);
        this.setBounds(this.sprite.getX(), this.sprite.getY(), personRegion.getRegionWidth(), personRegion.getRegionHeight());
        this.patientIndicator = new DiamondPoly(sprite.getX() +  sprite.getWidth() / 2, sprite.getY() + 3 *sprite.getHeight() / 2, (int) (2.5/ PIXEL_TO_METER), (int) (3/ PIXEL_TO_METER), 0x00ff00ff, game.getPolygonBatch());
        if (!this.addListener(new PersonClickListener(model))) {
            System.err.println("Failed to install listener");
        }
        this.model = model;
    }

    @Override
    public Sprite createSprite(LiftGame game) {
        personRegion = create_person_region(game);
        return new Sprite(personRegion);
    }

    private TextureRegion create_person_region(LiftGame game) {
        Pixmap pix = new Pixmap((int) (PersonBody.WIDTH / PIXEL_TO_METER), (int) (PersonBody.HEIGHT / PIXEL_TO_METER), Pixmap.Format.RGBA8888);
        pix.setColor(0xaa00aaff); // DE is red, AD is green and BE is blue.
        pix.fill();
        Texture textureSolid = new Texture(pix);
        return new TextureRegion(textureSolid, (int) (PersonBody.WIDTH / PIXEL_TO_METER), (int) (PersonBody.HEIGHT / PIXEL_TO_METER));
    }


    @Override
    protected void update() {
        super.update();
        patientIndicator.setPercentage(this.model.getSatisfaction()/PersonModel.STARTING_SATISFACTION);
        patientIndicator.setPosition(sprite.getX() + sprite.getWidth() / 2, sprite.getY() + 3* sprite.getHeight() / 2);
        this.setBounds(this.sprite.getX(), this.sprite.getY(), personRegion.getRegionWidth(), personRegion.getRegionHeight());
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        batch.end();
        patientIndicator.draw(batch, parentAlpha);
        batch.begin();
    }
}
