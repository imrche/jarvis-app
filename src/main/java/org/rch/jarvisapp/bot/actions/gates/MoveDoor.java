package org.rch.jarvisapp.bot.actions.gates;

import org.json.JSONObject;
import org.rch.jarvisapp.AppContextHolder;
import org.rch.jarvisapp.bot.JarvisBot;
import org.rch.jarvisapp.bot.actions.Action;
import org.rch.jarvisapp.bot.actions.DataContained;
import org.rch.jarvisapp.bot.ui.Tile;
import org.rch.jarvisapp.smarthome.SmartHome;
import org.rch.jarvisapp.smarthome.devices.Gate;

public class MoveDoor implements Action, DataContained {
    SmartHome smartHome = AppContextHolder.getSH();
    JarvisBot bot = AppContextHolder.getBot();
    String data;

    public enum Direction{
        open, close
    }

    public MoveDoor(Gate gate, Direction direction) {
        data = new JSONObject()
                .put("gate", gate.getCode())
                .put("action", direction.name()).toString();
    }

    @Override
    public void run(Tile tile) {
        JSONObject response = new JSONObject(smartHome.getApi().setGatesAction(data));
        bot.getMessageBuilder().popupAsync(bot.getLastCallBackId(), response.getString("message"));
    }

    @Override
    public Action setData(Object data) {
        this.data = data.toString();
        return this;
    }

    @Override
    public Object getData() {
        return null;
    }
}
