package org.rch.jarvisapp.bot.actions.valves;

import org.rch.jarvisapp.AppContextHolder;
import org.rch.jarvisapp.bot.actions.Action;
import org.rch.jarvisapp.bot.ui.Tile;
import org.rch.jarvisapp.bot.ui.keyboard.KeyBoard;
import org.rch.jarvisapp.bot.ui.keyboard.ValveKeyBoard;
import org.rch.jarvisapp.smarthome.SmartHome;
import org.rch.jarvisapp.smarthome.devices.Valve;

public class ShowValve implements Action {
    public final static String description = "Вводные краны";
    SmartHome smartHome = AppContextHolder.getSH();
    public String qwe;

    @Override
    public void run(Tile tile) {
        KeyBoard kb = new ValveKeyBoard();

        for (Valve valve : smartHome.getDevicesByType(Valve.class))
            kb.merge(new ValveKeyBoard(valve));

        tile.update()
                .setCaption(description)
                .setKeyboard(kb);
    }
}
