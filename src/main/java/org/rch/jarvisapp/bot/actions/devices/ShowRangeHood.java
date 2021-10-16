package org.rch.jarvisapp.bot.actions.devices;

import org.rch.jarvisapp.AppContextHolder;
import org.rch.jarvisapp.bot.actions.Action;
import org.rch.jarvisapp.bot.exceptions.HomeApiWrongResponseData;
import org.rch.jarvisapp.bot.ui.Tile;
import org.rch.jarvisapp.bot.ui.button.DeviceButton;
import org.rch.jarvisapp.bot.ui.keyboard.DeviceKeyBoard;
import org.rch.jarvisapp.bot.ui.keyboard.KeyBoard;
import org.rch.jarvisapp.smarthome.SmartHome;
import org.rch.jarvisapp.smarthome.devices.RangeHood;

import java.util.List;

public class ShowRangeHood implements Action {
    public final static String description = "Вытяжки";
    SmartHome smartHome = AppContextHolder.getSH();
    public String qwe;

    @Override
    public void run(Tile tile) throws HomeApiWrongResponseData {
        KeyBoard kb = new DeviceKeyBoard();

        List<RangeHood> rangeHoods = smartHome.getDevicesByType(RangeHood.class);

        for (RangeHood device : rangeHoods)
            kb.addButton(device.getRow(), new DeviceButton(device).build(true));

        if (kb.getButtonsList().size() > 0)
            kb.refresh();

        tile.update()
                .setCaption(description)
                .setKeyboard(kb);
    }
}
