package com.lift.game.model.entities.person;

import java.util.Random;

class RandomTypeGenerator {
    /*TODO Change that some types are more common than others
    * TODO Generate a double between 0 and 1 and then allign the percenatges with the percentage
    * TODO desired for each PersonType.
    */
    static PersonType getRandomType() {
        int pick = new Random().nextInt(PersonType.values().length);
        return PersonType.values()[pick];
    }
}
