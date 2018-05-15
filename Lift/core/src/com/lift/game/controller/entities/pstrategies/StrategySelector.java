package com.lift.game.controller.entities.pstrategies;

import com.lift.game.model.entities.person.PersonModel;
import com.lift.game.model.entities.person.PersonType;


public class StrategySelector {

    public static MovementStrategy getStrategy(PersonModel personModel) {
        PersonType personType;
        personType = personModel.getPersonType();
        switch (personType) {

            case Regular:
                return RegularMovement.getInstance();
            case Drunken:
                return DrunkenMovement.getInstance();
        }

        return null;
    }
}
