package com.lift.game.model.entities.person;

import java.util.Random;

class RandomTypeGenerator {
    /*TODO Change that some types are more common than others
    * TODO Generate a double between 0 and 1 and then allign the percenatges with the percentage
    * TODO desired for each PersonType.
    */

    private static final float added_percentages[] = {0.8f, 1f};

    static PersonType getRandomType() {
        return PersonType.values()[getTypeIndex()];
    }

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
