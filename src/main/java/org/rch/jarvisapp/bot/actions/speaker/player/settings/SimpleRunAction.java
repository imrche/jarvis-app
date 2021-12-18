package org.rch.jarvisapp.bot.actions.speaker.player.settings;

import org.rch.jarvisapp.bot.actions.Action;
import org.rch.jarvisapp.bot.exceptions.HomeApiWrongResponseData;
import org.rch.jarvisapp.bot.ui.Tile;
import org.rch.jarvisapp.bot.ui.button.func_interface.SimpleActionRunner;

public class SimpleRunAction implements Action {
    SimpleActionRunner applier;
    Boolean needStepBack;

    public SimpleRunAction(SimpleActionRunner applier, boolean stepBack) {
        this.applier = applier;
        needStepBack = stepBack;
    }

    public SimpleRunAction(SimpleActionRunner applier) {
        this(applier,true);
    }

    @Override
    public void run(Tile tile) throws HomeApiWrongResponseData {
        applier.run();
        if (needStepBack)
            tile.stepBack().publish();
    }

    @Override
    public int hashCode() {
        return (applier.hashCode() + needStepBack.hashCode() + this.getClass().hashCode());
    }//todo переопредилить для applier
}