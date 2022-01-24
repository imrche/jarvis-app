package org.rch.jarvisapp.bot.actions;

import org.rch.jarvisapp.bot.exceptions.HomeApiWrongResponseData;
import org.rch.jarvisapp.bot.ui.Tile;
import org.rch.jarvisapp.bot.ui.button.func_interface.SimpleActionRunner;
import org.rch.jarvisapp.bot.ui.button.func_interface.SimpleActionRunnerPar;

public class SimpleRunAction implements Action {
    SimpleActionRunner applier;
    SimpleActionRunnerPar<String> applierPar;
    Type actionType;

    public enum Type{
        STEP_BACK,
        UPDATE,
        NONE
    }

    public SimpleRunAction(SimpleActionRunnerPar<String> applier) {
        this(applier,Type.NONE);
    }

    public SimpleRunAction(SimpleActionRunnerPar<String> applier, Type actionType) {
        this.applierPar = applier;
        this.actionType = actionType;
    }




    public SimpleRunAction(SimpleActionRunner applier) {
        this(applier,Type.NONE);
    }

    public SimpleRunAction(SimpleActionRunner applier, Type actionType) {
        this.applier = applier;
        this.actionType = actionType;
    }

    @Override
    public void run(Tile tile) throws HomeApiWrongResponseData {
        if (applier != null)
            applier.run();

        if (applierPar != null)
            applierPar.run("");

        switch (actionType){
            case STEP_BACK :
                tile.stepBack().publish();
                //tile.stepBack().refresh();
                break;
            case UPDATE:
                tile.refresh();
                break;
            case NONE:
        }
    }

    @Override
    public int hashCode() {
        return ((applier != null ? applier.hashCode() : 0) + (applierPar != null ? applierPar.hashCode() : 0) + actionType.hashCode() + this.getClass().hashCode());
    }//todo переопредилить для applier
}