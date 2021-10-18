package org.rch.jarvisapp.bot.actions.valves;

import org.rch.jarvisapp.AppContextHolder;
import org.rch.jarvisapp.bot.actions.Action;
import org.rch.jarvisapp.bot.ui.Tile;
import org.rch.jarvisapp.bot.ui.keyboard.KeyBoard;
import org.rch.jarvisapp.bot.ui.keyboard.ValveKeyBoard;
import org.rch.jarvisapp.smarthome.SmartHome;
import org.rch.jarvisapp.smarthome.devices.Valve;

import java.util.ArrayList;
import java.util.List;

public class ShowValve implements Action {
    public final static String description = "Вводные краны";
    SmartHome smartHome = AppContextHolder.getSH();

    @Override
    public void run(Tile tile) {
        List<KeyBoard> kbList = new ArrayList<>();

        for (Valve valve : smartHome.getDevicesByType(Valve.class))
            kbList.add(new ValveKeyBoard(valve));

        tile.update()
                .setCaption(description)
                .setKeyboard(kbList);
    }
}
