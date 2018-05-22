package com.lift.game.view;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.lift.game.controller.entities.PersonBody;
import com.lift.game.model.entities.person.PersonType;
import com.lift.game.view.actors.polygon_actor.BasePolyActor;

import java.util.ArrayList;
import java.util.HashMap;

import static com.lift.game.view.GameView.PIXEL_TO_METER;

public class TextureManager {
    private ArrayList<Texture> peopleTextures;

    private ArrayList<Integer> colors;

    private static TextureManager instance = null;

    private Texture defaultTexture;

    private int defaultColor;

    public static TextureManager getInstance() {
        if (instance == null)
            instance = new TextureManager();
        return instance;
    }

    private TextureManager() {
        initializeColors();
        initializeTextures();
        initializeDefault();
    }

    private void initializeDefault() {
        defaultColor = 0x000000ff;
        Pixmap pix = new Pixmap((int) (PersonBody.WIDTH / PIXEL_TO_METER), (int) (PersonBody.HEIGHT / PIXEL_TO_METER), Pixmap.Format.RGBA8888);
        pix.setColor(defaultColor);
        pix.fill();
        defaultTexture =  new Texture(pix);
    }

    private void initializeTextures() {
        peopleTextures = new ArrayList<Texture>();
        for (int color : colors) {
            Pixmap pix = new Pixmap((int) (PersonBody.WIDTH / PIXEL_TO_METER), (int) (PersonBody.HEIGHT / PIXEL_TO_METER), Pixmap.Format.RGBA8888);
            pix.setColor(color);
            pix.fill();
            peopleTextures.add(new Texture(pix));
        }
    }

    private void initializeColors() {
        colors = new ArrayList<Integer>();
        colors.add(0x129824aa);
        colors.add(0x4ffb41aa);
        colors.add(0x0000ffaa);
        colors.add(0xffff00aa);
        colors.add(0xff00ffaa);
        colors.add(0x00ffffaa);
    }

    public Texture getTexture(int destination) {
        if (destination >= 0 && destination < peopleTextures.size())
            return peopleTextures.get(destination);
        return defaultTexture;
    }

    public int getColor(int destination) {
        if (destination >= 0 && destination < colors.size())
            return colors.get(destination);
        return defaultColor;
    }
}
