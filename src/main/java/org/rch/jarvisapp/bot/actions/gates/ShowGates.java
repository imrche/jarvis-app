package org.rch.jarvisapp.bot.actions.gates;

import org.rch.jarvisapp.AppContextHolder;
import org.rch.jarvisapp.bot.actions.Action;
import org.rch.jarvisapp.bot.dataobject.GateData;
import org.rch.jarvisapp.bot.ui.Tile;
import org.rch.jarvisapp.bot.ui.keyboard.GateKeyBoard;
import org.rch.jarvisapp.bot.ui.keyboard.GroupGateKeyBoard;
import org.rch.jarvisapp.bot.ui.keyboard.KeyBoard;
import org.rch.jarvisapp.smarthome.SmartHome;
import org.rch.jarvisapp.smarthome.api.Api;
import org.rch.jarvisapp.smarthome.devices.Gate;

import java.util.List;

public class ShowGates implements Action {
    public final static String description = "Гаражные ворота";
    SmartHome smartHome = AppContextHolder.getSH();
    Api api = AppContextHolder.getApi();
    public String tst;//todo я прям хз что это все значит

    @Override
    public void run(Tile tile) {
        GroupGateKeyBoard kb = new GroupGateKeyBoard();

        List<Gate> gates = smartHome.getDevicesByType(Gate.class);

        for (Gate gate : gates)
            kb.addGate(gate);

        tile.update()
                .setCaption(description)
                .setKeyboard(kb);
    }
}
