package main.com.lift.game.controller.entities.pstrategies;

import java.util.HashMap;

import main.com.lift.game.controller.GameController;
import main.com.lift.game.model.entities.person.PersonModel;
import main.com.lift.game.model.entities.person.PersonType;


public class StrategySelector {


    private HashMap<PersonType,MovementStrategy> strategies;

    private MovementStrategy nullStrategy;

    public StrategySelector(GameController gameController) {
        this.strategies = new HashMap<PersonType,MovementStrategy>();
        this.strategies.put(PersonType.Drunken, new DrunkenMovement(gameController));
        this.strategies.put(PersonType.Regular, new RegularMovement(gameController));
        this.nullStrategy = new NullStrategy(gameController);
    }

    public MovementStrategy getStrategy(PersonModel personModel) {
        PersonType personType;
        if(personModel != null) {
            personType = personModel.getPersonType();
            if (strategies.containsKey(personType)) {
                return strategies.get(personType);
            }
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
