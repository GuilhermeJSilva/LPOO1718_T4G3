package com.lift.game.controller.entities.pstrategies;

import com.lift.game.controller.GameController;
import com.lift.game.model.entities.person.PersonModel;
import com.lift.game.model.entities.person.PersonType;

import java.util.HashMap;

/**
 * Selects the strategy to use.
 */
public class StrategySelector {

    /**
     * Contains all available strategies organized by type.
     */
    private final HashMap<PersonType, MovementStrategy> strategies;

    /**
     * Null object for strategy.
     */
    private final MovementStrategy nullStrategy;

    /**
     * Constructs a strategy selector.
     *
     * @param gameController Game controller to pass to the strategies.
     */
    public StrategySelector(GameController gameController) {
        this.strategies = new HashMap<PersonType, MovementStrategy>();
        this.strategies.put(PersonType.Drunken, new DrunkenMovement(gameController));
        this.strategies.put(PersonType.Regular, new RegularMovement(gameController));
        this.nullStrategy = new NullStrategy(gameController);
    }

    /**
     * Returns the strategy to use with one person.
     *
     * @param personModel Model of the person.
     * @return Strategy to use.
     */
    public MovementStrategy getStrategy(PersonModel personModel) {
        PersonType personType;
        if (personModel != null) {
            personType = personModel.getPersonType();
            if (strategies.containsKey(personType)) {
                return strategies.get(personType);
            }
        }
        return nullStrategy;
    }

    /**
     * Returns the strategy to use with two people.
     *
     * @param personModel1 Model of one person.
     * @param personModel2 Model of one person.
     * @return Strategy to use.
     */
    public MovementStrategy getStrategy(PersonModel personModel1, PersonModel personModel2) {

        MovementStrategy movementStrategy1 = getStrategy(personModel1);
        MovementStrategy movementStrategy2 = getStrategy(personModel2);
        if (movementStrategy1.getPriority() > movementStrategy2.getPriority()) {
            return movementStrategy1;
        }
        return movementStrategy2;
    }
}
