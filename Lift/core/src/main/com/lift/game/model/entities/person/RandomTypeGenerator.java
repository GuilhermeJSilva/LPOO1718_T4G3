package com.lift.game.model.entities.person;

import java.util.Random;

/**
 * Generates a random type.
 */
class RandomTypeGenerator {

    /**
     * Added percentages of types.
     */
    private static final float added_percentages[] = {0.8f, 1f};

    /**
     * Returns a random type.
     * @return Random type.
     */
    static PersonType getRandomType() {
        return PersonType.values()[getTypeIndex()];
    }

    /**
     * Returns a random index.
     * @return Random index.
     */
    private static int getTypeIndex() {
        float value =  new Random().nextFloat();
        int i = 0;
        for (float percentage: added_percentages) {
            if(value <= percentage) {
                return i;
            }
            i++;
        }
        return 0;
    }
}
