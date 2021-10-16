package org.rch.jarvisapp.bot.actions.gates;

import org.rch.jarvisapp.AppContextHolder;
import org.rch.jarvisapp.bot.JarvisBot;
import org.rch.jarvisapp.bot.actions.Action;
import org.rch.jarvisapp.bot.actions.DataContained;
import org.rch.jarvisapp.bot.dataobject.GateData;
import org.rch.jarvisapp.bot.exceptions.HomeApiWrongResponseData;
import org.rch.jarvisapp.bot.ui.Tile;
import org.rch.jarvisapp.smarthome.SmartHome;
import org.rch.jarvisapp.smarthome.devices.Gate;

public class MoveDoor implements Action, DataContained {
    SmartHome smartHome = AppContextHolder.getSH();
    JarvisBot bot = AppContextHolder.getBot();
    GateData data;

    public MoveDoor(Gate gate, GateData.ActionValue action) {
        data = new GateData();
        data.addGate(gate,action);
    }

    @Override
    public void run(Tile tile) throws HomeApiWrongResponseData {
        GateData response = smartHome.getApi().setGatesAction(data);
        //todo
        //bot.getMessageBuilder().popupAsync(bot.getLastCallBackId(), response.getGateMessage());
    }

    @Override
    public Action setData(Object data) {
        this.data = (GateData)data;
        return this;
    }

    @Override
    public Object getData() {
        return null;
    }
}
