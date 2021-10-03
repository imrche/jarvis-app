package org.rch.jarvisapp.bot.actions.gates;

import org.rch.jarvisapp.AppContextHolder;
import org.rch.jarvisapp.bot.actions.Action;
import org.rch.jarvisapp.bot.ui.Tile;
import org.rch.jarvisapp.bot.ui.keyboard.GateKeyBoard;
import org.rch.jarvisapp.bot.ui.keyboard.KeyBoard;
import org.rch.jarvisapp.smarthome.SmartHome;
import org.rch.jarvisapp.smarthome.devices.Gate;

public class ShowGates implements Action {
    public final static String description = "Гаражные ворота";
    SmartHome smartHome = AppContextHolder.getSH();
    public String tst;//todo я прям хз что это

    @Override
    public void run(Tile tile) {
        KeyBoard kb = new KeyBoard();

        for (Gate gate : smartHome.getDevicesByType(Gate.class))
            kb.merge(new GateKeyBoard(gate));

        tile.update()
                .setCaption(description)
                .setKeyboard(kb);
    }
}
