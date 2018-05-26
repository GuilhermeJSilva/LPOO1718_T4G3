package com.lift.game.view;

import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.lift.game.LiftGame;
import com.lift.game.controller.entities.PlatformBody;
import com.lift.game.model.entities.PowerUpType;
import com.lift.game.model.entities.person.PersonType;
import com.lift.game.model.entities.person.Side;

import java.util.ArrayList;
import java.util.HashMap;

import static com.lift.game.controller.powerups.types.BasicPowerUP.RADIUS_OF_THE_BODY;
import static com.lift.game.view.GameView.PIXEL_TO_METER;

/**
 * Manages all texture that belong to recurring elements of the game.
 */
public class TextureManager {
    /**
     * Number of columns to divide sprite sheets.
     */
    private static final int FRAME_COLS = 12;

    /**
     * Number of rows to divide sprite sheets.
     */
    private static final int FRAME_ROWS = 1;

    /**
     * Textures for the power ups.
     */
    private HashMap<PowerUpType, Texture> powerUpTexture;

    /**
     * Walking animations stored by type of person.
     */
    private HashMap<PersonType, Animation<TextureRegion>> walkAnimation;

    /**
     * Reverse walking animations stored by type of person.
     */
    private HashMap<PersonType, Animation<TextureRegion>> reverseWalkAnimation;

    /**
     * Textures for platforms, the index of the platform corresponds to the number of the floor.
     */
    private ArrayList<Texture> platformTextures;

    /**
     * Colors to display the people's patient indicator and to indicate the color of each floor.
     */
    private ArrayList<Integer> colors;

    /**
     * If the desired color is not contained in the color array this color is used.
     */
    private int defaultColor;

    /**
     * If the desired platform texture is not in the platform this texture is used.
     */
    private Texture defaultPlatformTexture;

    /**
     * Default power up texture.
     */
    private Texture defaultPowerUpTexture;

    /**
     * Background texture.
     */
    private Texture background;

    /**
     * Light to be displayed above the background.
     */
    private Texture light;

    /**
     * Represents the structure of the elevators.
     */
    private Texture structure;

    /**
     * Is used to determine which portion of the background is showing.
     */
    private Float backgroundDelta = 0f;

    /**
     * Game that this texture manager belongs to, is used to get the asset manager.
     */
    private LiftGame game;

    /**
     * Constructs the manager and loads all textures.
     *
     * @param game Owner of this texture manager.
     */
    public TextureManager(LiftGame game) {
        this.game = game;
        initializeColors();
        initializePlatformTextures();
        initializeDefault();
        initializePeopleAnimation();
        initializePowerUpTextures();
        this.background = game.getAssetManager().get("fundo.png");
        this.structure = game.getAssetManager().get("structure.png");
        this.light = game.getAssetManager().get("SUN.png");
    }

    /**
     * Initializes the power up textures.
     */
    private void initializePowerUpTextures() {
        this.powerUpTexture = new HashMap<PowerUpType, Texture>();
        //TODO Change to the real textures.
        Pixmap pix = new Pixmap((int) (RADIUS_OF_THE_BODY / PIXEL_TO_METER), (int) (RADIUS_OF_THE_BODY / PIXEL_TO_METER), Pixmap.Format.RGBA8888);
        pix.setColor(0xaa00aaff);
        pix.fill();
        this.powerUpTexture.put(PowerUpType.LifePowerUp, new Texture(pix));
        pix = new Pixmap((int) (RADIUS_OF_THE_BODY / PIXEL_TO_METER), (int) (RADIUS_OF_THE_BODY / PIXEL_TO_METER), Pixmap.Format.RGBA8888);
        pix.setColor(0x00aaaaff);
        pix.fill();
        this.powerUpTexture.put(PowerUpType.ElevatorVelocity, new Texture(pix));

    }

    /**
     * Initializes the default colors and textures.
     */
    private void initializeDefault() {
        defaultColor = 0x000000ff;
        Pixmap pix = new Pixmap((int) (PlatformBody.PLATFORM_LENGTH / PIXEL_TO_METER), (int) (PlatformBody.PLATFORM_HEIGHT / PIXEL_TO_METER), Pixmap.Format.RGBA8888);
        pix.setColor(defaultColor);
        pix.fill();
        defaultPlatformTexture = new Texture(pix);
        pix = new Pixmap((int) (RADIUS_OF_THE_BODY / PIXEL_TO_METER), (int) (RADIUS_OF_THE_BODY / PIXEL_TO_METER), Pixmap.Format.RGBA8888);
        pix.setColor(defaultColor);
        pix.fill();
        defaultPowerUpTexture = new Texture(pix);

    }

    /**
     * Initializes the textures for all platforms.
     */
    private void initializePlatformTextures() {
        platformTextures = new ArrayList<Texture>();

        for (int color : colors) {
            Pixmap pix = new Pixmap((int) (PlatformBody.PLATFORM_LENGTH / PIXEL_TO_METER), (int) (PlatformBody.PLATFORM_HEIGHT / PIXEL_TO_METER), Pixmap.Format.RGBA8888);
            pix.setColor(color);
            pix.fill();
            platformTextures.add(new Texture(pix));
        }
    }

    /**
     * Initializes the animations for all person type.
     */
    private void initializePeopleAnimation() {
        this.walkAnimation = new HashMap<PersonType, Animation<TextureRegion>>();
        this.reverseWalkAnimation = new HashMap<PersonType, Animation<TextureRegion>>();

        walkAnimation(PersonType.Regular, "regular.png");
        walkAnimation(PersonType.Drunken, "drunk.png");
    }

    /**
     * Initializes the animations for a given type of person.
     *
     * @param personType The animation is assigned to this person type.
     * @param fileName   Filename of the sprite sheet.
     */
    private void walkAnimation(PersonType personType, String fileName) {
        Texture walkSheet = game.getAssetManager().get(fileName);

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
        walkAnimation.put(personType, new Animation<TextureRegion>(0.125f, walkFrames));
        reverseWalkAnimation.put(personType, new Animation<TextureRegion>(0.125f, reverseWalkFrames));
    }

    /**
     * Initializes the colors.
     */
    private void initializeColors() {
        colors = new ArrayList<Integer>();
        colors.add(0x129824ff);
        colors.add(0x4ffb41ff);
        colors.add(0x0000ffff);
        colors.add(0xffff00ff);
        colors.add(0xff00ffff);
        colors.add(0x00ffffff);
    }

    /**
     * Returns the color for a certain floor, if there is no color for that floor the default color is returned.
     *
     * @param floor Floor number.
     * @return Color for the respective floor.
     */
    public int getColor(int floor) {
        if (floor >= 0 && floor < colors.size())
            return colors.get(floor);
        return defaultColor;
    }

    /**
     * Returns the platform texture to a certain floor, if there is no platform texture assigned for that floor, the
     * default texture is returned.
     *
     * @param floor Floor number.
     * @return Texture of the respective floor.
     */
    public Texture getPlatformTexture(int floor) {
        if (floor >= 0 && floor < platformTextures.size())
            return platformTextures.get(floor);
        return defaultPlatformTexture;
    }

    /**
     * Returns the texture region corresponding to the person type, state of the animation and direction of the movement.
     *
     * @param personType Type of person.
     * @param stateTime  State of the animation.
     * @param direction  Direction of the movement.
     * @return Texture corresponding to the given parameters.
     */
    public TextureRegion getPersonTexture(PersonType personType, float stateTime, Side direction) {
        if (walkAnimation.containsKey(personType)) {
            if (direction == Side.Right)
                return walkAnimation.get(personType).getKeyFrame(stateTime, true);
            return reverseWalkAnimation.get(personType).getKeyFrame(stateTime, true);
        } else {
            if (direction == Side.Right)
                return walkAnimation.get(PersonType.Regular).getKeyFrame(stateTime, true);
            return reverseWalkAnimation.get(PersonType.Regular).getKeyFrame(stateTime, true);
        }
    }

    /**
     * Advances the display of the background and returns the region to be displayed.
     *
     * @param delta Parameter to update the part of the background to be displayed.
     * @return Texture region to be displayed.
     */
    public TextureRegion getBackground(float delta) {
        Float initialValue = background.getHeight() - (1920 + backgroundDelta + delta);
        if (initialValue < 0)
            initialValue = 0f;
        else if (initialValue > background.getHeight() - 1920)
            initialValue = background.getHeight() - 1920f;
        else
            backgroundDelta += delta;
        return new TextureRegion(background, 0, initialValue.intValue(), 1080, 1920);

    }

    /**
     * Returns the texture corresponding to elevator structure.
     * @return Elevator structure texture.
     */
    public TextureRegion getStructure() {
        return new TextureRegion(structure, 0, 0, 1080, 1920);
    }


    /**
     * Returns the texture to be displayed above the background.
     * @return Texture to be displayed above the background.
     */
    public TextureRegion getSun() {
        return new TextureRegion(light, 0, 0, 1080, 1920);
    }

    /**
     * Returns a power up texture based on the power up type.
     * @param powerUpType Type of power up.
     * @return Texture associated to that type.
     */
    public Texture getPUTexture(PowerUpType powerUpType) {
        System.out.println(powerUpType);
        if(powerUpTexture.containsKey(powerUpType)) {
            return powerUpTexture.get(powerUpType);
        }
        return defaultPowerUpTexture;
    }
}
