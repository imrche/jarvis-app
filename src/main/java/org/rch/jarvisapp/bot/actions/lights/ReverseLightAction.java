package org.rch.jarvisapp.bot.actions.lights;

import org.rch.jarvisapp.AppContextHolder;
import org.rch.jarvisapp.bot.actions.Action;
import org.rch.jarvisapp.bot.actions.DataContained;
import org.rch.jarvisapp.bot.dataobject.DeviceCommandData;
import org.rch.jarvisapp.bot.ui.Tile;
import org.rch.jarvisapp.smarthome.api.Api;

public class ReverseLightAction implements Action, DataContained {
    Api api = AppContextHolder.getApi();
    String data;

    public ReverseLightAction() {}

    public ReverseLightAction(DeviceCommandData data) {
        setData(data);
    }

    @Override
    public void run(Tile tile){
        api.setStatusLight(api.getStatusLight(new DeviceCommandData(data)).reverse());
        //todo перенести реверс на сервер (возвращать статус, который получился по итогу)
        tile.refresh();
    }


    @Override
    public Action setData(Object data) {
        this.data = data.toString();
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
