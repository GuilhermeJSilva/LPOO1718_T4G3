package com.lift.game.view.actors.game_actors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.lift.game.LiftGame;
import com.lift.game.controller.entities.PersonBody;
import com.lift.game.model.entities.person.PersonModel;
import com.lift.game.model.entities.person.PersonState;
import com.lift.game.model.Side;
import com.lift.game.view.actors.polygon_actor.IndicatorCreator;
import com.lift.game.view.TextureManager;
import com.lift.game.view.actors.EntityActor;
import com.lift.game.view.actors.polygon_actor.BasePolyActor;
import com.lift.game.view.clickListeners.FloorClick;

import java.util.ArrayList;
import java.util.HashMap;

import static com.lift.game.view.GameView.PIXEL_TO_METER;

/**
 * Represents a person in the scene.
 */
public class PersonActor extends EntityActor {

    /**
     * Patient indicator.
     */
    private final BasePolyActor patientIndicator;

    /**
     * Animation time.
     */
    float stateTime = 0;

    /**
     * Indicator positions for people in the elevator.
     */
    private static HashMap<Side, ArrayList<Vector2>> indicatorPositions;

    /**
     * Indicator counters.
     */
    private static final int[] indicator_number = {0, 0};

    /**
     * Changes the positions for the indicators of people in the elevator.
     * @param indicatorPositions New value for the positions.
     */
    public static void setIndicatorPositions(HashMap<Side, ArrayList<Vector2>> indicatorPositions) {
        PersonActor.indicatorPositions = indicatorPositions;
    }

    /**
     * Constructs a person actor.
     * @param game Game it belongs to.
     * @param model Model it represents.
     */
    public PersonActor(LiftGame game, PersonModel model) {
        super(model);
        this.sprite = new Sprite(new Texture((int)(PersonBody.WIDTH / PIXEL_TO_METER), (int)(PersonBody.HEIGHT / PIXEL_TO_METER), Pixmap.Format.RGB888));
        int color = TextureManager.getInstance().getColor(((PersonModel) this.model).getDestination());
        this.patientIndicator = IndicatorCreator.createIndicator(new Vector2(sprite.getX() + sprite.getWidth() / 2, sprite.getY() + 3 * sprite.getHeight() / 2), model.getPersonType(), color, game.getPolygonBatch());
        this.setBounds(this.sprite.getX(), this.sprite.getY(), PersonBody.WIDTH / PIXEL_TO_METER, PersonBody.HEIGHT / PIXEL_TO_METER);
        this.addCaptureListener(new FloorClick(game, model.getFloor(), model.getSide()));
    }


    @Override
    protected void update() {
        super.update();
        patientIndicator.setPercentage(((PersonModel) this.model).getSatisfaction() / PersonModel.STARTING_SATISFACTION);
        patientIndicator.setPosition(sprite.getX() + sprite.getWidth() / 2, sprite.getY() + 3 * sprite.getHeight() / 2);
        this.setBounds(this.sprite.getX(), this.sprite.getY(), PersonBody.WIDTH / PIXEL_TO_METER, PersonBody.HEIGHT / PIXEL_TO_METER);
    }


    /**
     * Updates the indicator.
     */
    private void updateIndicator() {
        ArrayList<Vector2> tmp = indicatorPositions.get(this.model.getSide());
        int pos = this.model.getSide() == Side.Left ? 0 : 1;
        patientIndicator.setPercentage(((PersonModel) this.model).getSatisfaction() / PersonModel.STARTING_SATISFACTION);
        patientIndicator.setPosition(tmp.get(indicator_number[pos]).x, tmp.get(indicator_number[pos]).y);
        indicator_number[pos]++;
        indicator_number[pos] %= tmp.size();
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {

        if (removed()) {
            if (((PersonModel) this.model).getPersonState() != PersonState.InElevator) {
                drawPerson(batch, parentAlpha);
            } else {
                drawIndicatorOnly(batch, parentAlpha);
            }
        }
    }


    /**
     * Draws only the indicator.
     * @param batch Batch.
     * @param parentAlpha Parent Alpha.
     */
    private void drawIndicatorOnly(Batch batch, float parentAlpha) {
        this.updateIndicator();
        batch.end();
        patientIndicator.draw(batch, parentAlpha);
        batch.begin();
    }

    /**
     * Draws the person.
     * @param batch Batch.
     * @param parentAlpha Parent Alpha.
     */
    private void drawPerson(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        stateTime += Gdx.graphics.getDeltaTime();

        TextureRegion currentFrame = TextureManager.getInstance().getPersonTexture(((PersonModel) this.model).getPersonType(), stateTime, this.getRunningDirection());
        sprite.setRegion(currentFrame);
        if(((PersonModel) this.model).getPersonState() != PersonState.Reached) {
            batch.end();
            patientIndicator.draw(batch, parentAlpha);
            batch.begin();
        }
    }

    /**
     * Returns the direction the person is running.
     * @return Direction the person is running.
     */
    private Side getRunningDirection() {
        if(this.model.getSide() == Side.Left) {
            if(((PersonModel) this.model).getPersonState() != PersonState.Reached)
                return Side.Right;
            else
                return Side.Left;
        } else {
            if(((PersonModel) this.model).getPersonState() != PersonState.Reached)
                return Side.Left;
            else
                return Side.Right;
        }
    }

    /**
     * Resets the indicator counters.
     */
    public static void resetCounters() {
        for (int i = 0; i < indicator_number.length; i++) {
            indicator_number[i] = 0;
        }
    }

}
