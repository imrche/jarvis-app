package org.rch.jarvisapp.bot.actions.gates;

import org.rch.jarvisapp.AppContextHolder;
import org.rch.jarvisapp.bot.actions.Action;
import org.rch.jarvisapp.bot.exceptions.HomeApiWrongResponseData;
import org.rch.jarvisapp.bot.ui.Tile;
import org.rch.jarvisapp.bot.ui.keyboard.GroupGateKeyBoard;
import org.rch.jarvisapp.smarthome.SmartHome;
import org.rch.jarvisapp.smarthome.api.Api;
import org.rch.jarvisapp.smarthome.devices.Gate;

import java.util.List;

public class ShowGates implements Action {
    public final static String description = "Гаражные ворота";
    SmartHome smartHome = AppContextHolder.getSH();
    Api api = AppContextHolder.getApi();
    public String tst;//todo придумать как исключить все клавиатуры из сериализации

    @Override
    public void run(Tile tile) throws HomeApiWrongResponseData {
        GroupGateKeyBoard kb = new GroupGateKeyBoard();

        List<Gate> gates = smartHome.getDevicesByType(Gate.class);

        for (Gate gate : gates)
            kb.addGate(gate);

        tile.update()
                .setCaption(description)
                .setKeyboard(kb);
    }
}
