package org.rch.jarvisapp.bot.actions.lights;

import org.rch.jarvisapp.AppContextHolder;
import org.rch.jarvisapp.bot.actions.Action;
import org.rch.jarvisapp.bot.actions.DataContained;
import org.rch.jarvisapp.bot.dataobject.DataObject;
import org.rch.jarvisapp.bot.dataobject.SwitcherData;
import org.rch.jarvisapp.bot.exceptions.HomeApiWrongResponseData;
import org.rch.jarvisapp.bot.ui.Tile;
import org.rch.jarvisapp.smarthome.api.Api;

public class SetLight implements Action, DataContained {
    Api api = AppContextHolder.getApi();
    SwitcherData data;

    @Override
    public void run(Tile tile) throws HomeApiWrongResponseData {
        api.setStatusLight(data);
        tile.refresh();
    }


    @Override
    public Action setData(DataObject data) {
        this.data = (SwitcherData) data;
        return this;
    }

    @Override
    public Object getData() {
        return data;
    }


    @Override
    public int hashCode() {
        return data.hashCode() + this.getClass().hashCode();
    }//todo надо позащищеннее
}
