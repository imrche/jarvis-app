package org.rch.jarvisapp.bot.actions;

import org.rch.jarvisapp.AppContextHolder;
import org.rch.jarvisapp.bot.exceptions.HomeApiWrongResponseData;
import org.rch.jarvisapp.bot.ui.Tile;

public interface Action {
    void run(Tile tile) throws HomeApiWrongResponseData;

    default String caching(){
        return AppContextHolder.getActionCache().setCallBack(this).toString();
    }
}
