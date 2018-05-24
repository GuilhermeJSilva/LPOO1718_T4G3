package com.lift.game.view.actors.game_actors.person;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.lift.game.LiftGame;
import com.lift.game.controller.entities.PersonBody;
import com.lift.game.model.entities.person.PersonModel;
import com.lift.game.model.entities.person.PersonState;
import com.lift.game.model.entities.person.PersonType;
import com.lift.game.model.entities.person.Side;
import com.lift.game.view.IndicatorCreator;
import com.lift.game.view.TextureManager;
import com.lift.game.view.actors.EntityActor;
import com.lift.game.view.actors.polygon_actor.BasePolyActor;

import java.util.ArrayList;
import java.util.HashMap;

import static com.lift.game.view.GameView.PIXEL_TO_METER;

public class PersonActor extends EntityActor {
    public static final float INDICATOR_WIDTH = 2.5f / PIXEL_TO_METER;

    private static final float INDICATOR_HEIGHT = (3 / PIXEL_TO_METER);

    private BasePolyActor patientIndicator;

    private PersonModel model;

    private LiftGame game;

    float stateTime = 0;

    private static HashMap<Side, ArrayList<Vector2>> indicatorPositions;

    private static int indicator_number[] = {0, 0};

    public static void setIndicatorPositions(HashMap<Side, ArrayList<Vector2>> indicatorPositions) {
        PersonActor.indicatorPositions = indicatorPositions;
    }

    public PersonActor(LiftGame game, PersonModel model) {
        super(model);
        this.game = game;
        this.model = model;
        this.sprite = new Sprite(new Texture((int)(PersonBody.WIDTH / PIXEL_TO_METER), (int)(PersonBody.HEIGHT / PIXEL_TO_METER), Pixmap.Format.RGB888));
        int color = game.getTextureManager().getColor(model.getDestination());
        this.patientIndicator = IndicatorCreator.createIndicator(new Vector2(sprite.getX() + sprite.getWidth() / 2, sprite.getY() + 3 * sprite.getHeight() / 2), model.getPersonType(), color, game.getPolygonBatch());

        this.setBounds(this.sprite.getX(), this.sprite.getY(), PersonBody.WIDTH / PIXEL_TO_METER, PersonBody.HEIGHT / PIXEL_TO_METER);
        if (!this.addListener(new PersonClickListener(model))) {
            System.err.println("Failed to install listener");
        }
    }


    @Override
    protected void update() {
        super.update();
        patientIndicator.setPercentage(this.model.getSatisfaction() / PersonModel.STARTING_SATISFACTION);
        patientIndicator.setPosition(sprite.getX() + sprite.getWidth() / 2, sprite.getY() + 3 * sprite.getHeight() / 2);
        this.setBounds(this.sprite.getX(), this.sprite.getY(), PersonBody.WIDTH / PIXEL_TO_METER, PersonBody.HEIGHT / PIXEL_TO_METER);
    }


    private void updateIndicator() {
        ArrayList<Vector2> tmp = indicatorPositions.get(model.getSide());
        int pos = model.getSide() == Side.Left ? 0 : 1;
        patientIndicator.setPercentage(this.model.getSatisfaction() / PersonModel.STARTING_SATISFACTION);
        patientIndicator.setPosition(tmp.get(indicator_number[pos]).x, tmp.get(indicator_number[pos]).y);
        indicator_number[pos]++;
        indicator_number[pos] %= tmp.size();
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {

        if (removed()) {
            if (model.getPersonState() != PersonState.InElevator) {
                drawPerson(batch, parentAlpha);
            } else {
                drawIndicatorOnly(batch, parentAlpha);
            }
        }
    }


    private void drawIndicatorOnly(Batch batch, float parentAlpha) {
        this.updateIndicator();
        batch.end();
        patientIndicator.draw(batch, parentAlpha);
        batch.begin();
    }

    private void drawPerson(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        stateTime += Gdx.graphics.getDeltaTime();

        TextureRegion currentFrame = game.getTextureManager().getPersonTexture(model.getPersonType(), stateTime, this.getRunningDirection());
        sprite.setRegion(currentFrame);
        if(model.getPersonState() != PersonState.Reached) {
            batch.end();
            patientIndicator.draw(batch, parentAlpha);
            batch.begin();
        }
    }

    private Side getRunningDirection() {
        if(model.getSide() == Side.Left) {
            if(model.getPersonState() != PersonState.Reached)
                return Side.Right;
            else
                return Side.Left;
        } else {
            if(model.getPersonState() != PersonState.Reached)
                return Side.Left;
            else
                return Side.Right;
        }
    }

    public static void resetCounters() {
        for (int i = 0; i < indicator_number.length; i++) {
            indicator_number[i] = 0;
        }
    }

}
