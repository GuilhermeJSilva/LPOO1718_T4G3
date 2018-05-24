package com.lift.game.view;

import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.lift.game.LiftGame;
import com.lift.game.controller.entities.PersonBody;
import com.lift.game.controller.entities.PlatformBody;
import com.lift.game.model.entities.person.PersonType;
import com.lift.game.model.entities.person.Side;

import java.util.ArrayList;

import static com.lift.game.view.GameView.PIXEL_TO_METER;

public class TextureManager {
    //TODO all textures saved in a hash map

    private static final int FRAME_COLS = 12;

    private static final int FRAME_ROWS = 1;

    private Animation<TextureRegion> walkAnimation;

    private Animation<TextureRegion> reverseWalkAnimation;

    private Texture walkSheet;

    private ArrayList<Texture> platformTextures;

    private ArrayList<Integer> colors;

    private Texture defaultPersonTexture;

    private int defaultColor;

    private Texture defaultPlatformTexture;

    private Texture background;

    private Texture light;

    private Texture structure;

    private Float backgroundDelta = 0f;

    private LiftGame game;

    public TextureManager(LiftGame game) {
        this.game = game;
        initializeColors();
        initializeTextures();
        initializeDefault();
        initializePeopleAnimation();
        this.background = game.getAssetManager().get("fundo.png");
        this.structure = game.getAssetManager().get("structure.png");
        this.light = game.getAssetManager().get("SUN.png");
    }

    private void initializeDefault() {
        defaultColor = 0x000000ff;
        Pixmap pix = new Pixmap((int) (PersonBody.WIDTH / PIXEL_TO_METER), (int) (PersonBody.HEIGHT / PIXEL_TO_METER), Pixmap.Format.RGBA8888);
        pix.setColor(defaultColor);
        pix.fill();
        defaultPersonTexture =  new Texture(pix);

        pix = new Pixmap((int) (PlatformBody.PLATFORM_LENGTH / PIXEL_TO_METER), (int) (PlatformBody.PLATFORM_HEIGHT / PIXEL_TO_METER), Pixmap.Format.RGBA8888);
        pix.setColor(defaultColor);
        pix.fill();
        defaultPlatformTexture = new Texture(pix);
    }

    private void initializeTextures() {
        platformTextures = new ArrayList<Texture>();

        for (int color : colors) {
            Pixmap pix = new Pixmap((int) (PlatformBody.PLATFORM_LENGTH / PIXEL_TO_METER), (int) (PlatformBody.PLATFORM_HEIGHT / PIXEL_TO_METER), Pixmap.Format.RGBA8888);
            pix.setColor(color);
            pix.fill();
            platformTextures.add(new Texture(pix));
        }
    }

    private void initializePeopleAnimation() {
        walkSheet = game.getAssetManager().get("gajos.png");

        TextureRegion[][] tmp = TextureRegion.split(walkSheet,
                walkSheet.getWidth() / FRAME_COLS,
                walkSheet.getHeight() / FRAME_ROWS);

        TextureRegion[] walkFrames = new TextureRegion[FRAME_COLS * FRAME_ROWS];
        TextureRegion[] reverseWalkFrames = new TextureRegion[FRAME_COLS * FRAME_ROWS];
        int index = 0;
        for (int i = 0; i < FRAME_ROWS; i++) {
            for (int j = 0; j < FRAME_COLS; j++) {
                walkFrames[index++] = tmp[i][j];
                reverseWalkFrames[index - 1] = new TextureRegion(tmp[i][j]);
                reverseWalkFrames[index - 1].flip(true, false);
            }
        }
        walkAnimation = new Animation<TextureRegion>(0.125f, walkFrames);
        reverseWalkAnimation = new Animation<TextureRegion>(0.125f, reverseWalkFrames);
    }

    private void initializeColors() {
        colors = new ArrayList<Integer>();
        colors.add(0x129824ff);
        colors.add(0x4ffb41ff);
        colors.add(0x0000ffff);
        colors.add(0xffff00ff);
        colors.add(0xff00ffff);
        colors.add(0x00ffffff);
    }


    public int getColor(int destination) {
        if (destination >= 0 && destination < colors.size())
            return colors.get(destination);
        return defaultColor;
    }

    public Texture getPlatformTexture(int floor) {
        if (floor >= 0 && floor < platformTextures.size())
            return platformTextures.get(floor);
        return defaultPlatformTexture;
    }

    public TextureRegion getPersonTexture(PersonType personType, float stateTime, Side direction) {
        if(direction == Side.Right)
        return walkAnimation.getKeyFrame(stateTime, true);
        return  reverseWalkAnimation.getKeyFrame(stateTime, true);
    }

    public TextureRegion getBackground(float delta) {
        Float initialValue = background.getHeight() - (1920 + backgroundDelta + delta);
        if(initialValue < 0)
            initialValue = 0f;
        else if(initialValue > background.getHeight() - 1920)
            initialValue = background.getHeight() - 1920f;
        else
            backgroundDelta += delta;
        return new TextureRegion(background, 0 , initialValue.intValue(), 1080, 1920);

    }

    public TextureRegion getStructure() {
        return new TextureRegion(structure, 0 , 0, 1080, 1920);
    }

    public void resetBackground() {
        this.backgroundDelta = 0f;
    }

    public TextureRegion getSun() {
        return new TextureRegion(light, 0 , 0, 1080, 1920);
    }
}
