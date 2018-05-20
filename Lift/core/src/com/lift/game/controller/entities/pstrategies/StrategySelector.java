package com.lift.game.controller.entities.pstrategies;

import com.lift.game.model.entities.person.PersonModel;
import com.lift.game.model.entities.person.PersonType;

import java.util.HashMap;


public class StrategySelector {


    private HashMap<PersonType,MovementStrategy> strategies;

    private MovementStrategy nullStrategy;

    public StrategySelector() {
        this.strategies = new HashMap<PersonType,MovementStrategy>();
        this.strategies.put(PersonType.Drunken, new DrunkenMovement());
        this.strategies.put(PersonType.Regular, new RegularMovement());
        this.nullStrategy = new NullStrategy();
    }

    public MovementStrategy getStrategy(PersonModel personModel) {
        PersonType personType;
        personType = personModel.getPersonType();
        if(strategies.containsKey(personType)) {
            return strategies.get(personType);
        }
        return nullStrategy;
    }

    //TODO change to consider strategy priority
    public MovementStrategy getStrategy(PersonModel personModel1, PersonModel personModel2) {
        PersonType personType;
        personType = personModel1.getPersonType();
        if(strategies.containsKey(personType)) {
            return strategies.get(personType);
        }
        return nullStrategy;
    }
}
