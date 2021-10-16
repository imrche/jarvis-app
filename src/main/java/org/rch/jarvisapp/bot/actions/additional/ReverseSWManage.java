package org.rch.jarvisapp.bot.actions.additional;

import org.rch.jarvisapp.AppContextHolder;
import org.rch.jarvisapp.bot.actions.Action;
import org.rch.jarvisapp.bot.actions.DataContained;
import org.rch.jarvisapp.bot.dataobject.SwitcherData;
import org.rch.jarvisapp.bot.exceptions.HomeApiWrongResponseData;
import org.rch.jarvisapp.bot.ui.Tile;
import org.rch.jarvisapp.smarthome.api.Api;

public class ReverseSWManage implements Action, DataContained {
    Api api = AppContextHolder.getApi();
    SwitcherData data;
    public String dummy;

    public ReverseSWManage() {}

    public ReverseSWManage(SwitcherData data) {
        setData(data);
    }

    @Override
    public void run(Tile tile) throws HomeApiWrongResponseData {
        api.setStatusSwitchManager(api.getStatusSwitchManager(data).reverse());
        //todo перенести реверс на сервер (возвращать статус, который получился по итогу)
        //tile.refresh();
    }


    @Override
    public Action setData(Object data) {
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
