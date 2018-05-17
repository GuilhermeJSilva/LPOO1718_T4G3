package com.lift.game.view.actors.game_actors.person;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.lift.game.model.GameModel;
import com.lift.game.model.entities.ElevatorModel;
import com.lift.game.model.entities.person.PersonModel;
import com.lift.game.model.entities.person.PersonState;

public class PersonClickListener extends ClickListener {
    private PersonModel personModel;

    public PersonClickListener(PersonModel personModel) {
        super();
        this.personModel = personModel;
    }


    @Override
    public void clicked(InputEvent event, float x, float y) {
        super.clicked(event, x, y);
        personModel.setPersonState(PersonState.TryToEnter);
    }
}
