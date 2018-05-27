package main.com.lift.game.model.entities.person;

import java.util.Random;

class RandomTypeGenerator {

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
