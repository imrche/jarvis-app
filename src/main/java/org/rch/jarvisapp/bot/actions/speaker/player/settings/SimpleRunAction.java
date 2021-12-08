package org.rch.jarvisapp.bot.actions.speaker.player.settings;

import org.rch.jarvisapp.bot.actions.Action;
import org.rch.jarvisapp.bot.exceptions.HomeApiWrongResponseData;
import org.rch.jarvisapp.bot.ui.Tile;
import org.rch.jarvisapp.bot.ui.button.func_interface.SimpleActionRunner;

public class SimpleRunAction implements Action {
    SimpleActionRunner applier;
    //todo сделать универсальным прокинув выбор по post-action

    public SimpleRunAction(SimpleActionRunner applier) {
        this.applier = applier;
    }

    @Override
    public void run(Tile tile) throws HomeApiWrongResponseData {
        applier.run();
        tile.stepBack().publish();
    }

    @Override
    public int hashCode() {
        return (applier.hashCode() + this.getClass().hashCode());
    }//todo переопредилить для applier
}