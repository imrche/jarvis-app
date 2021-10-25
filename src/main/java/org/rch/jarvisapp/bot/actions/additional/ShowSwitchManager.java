package org.rch.jarvisapp.bot.actions.additional;

import org.rch.jarvisapp.AppContextHolder;
import org.rch.jarvisapp.bot.actions.Action;
import org.rch.jarvisapp.bot.ui.Tile;
import org.rch.jarvisapp.bot.ui.button.comparators.LightComparator;
import org.rch.jarvisapp.bot.ui.keyboard.SwitchManageKeyBoard;
import org.rch.jarvisapp.smarthome.SmartHome;
import org.rch.jarvisapp.smarthome.areas.Place;
import org.rch.jarvisapp.smarthome.devices.Light;

import java.util.List;

public class ShowSwitchManager implements Action {
    public final static String description = "Активатор";
    SmartHome smartHome = AppContextHolder.getSH();
    Place place;

    public ShowSwitchManager(Place place) {
        this.place = place;
    }

    @Override
    public void run(Tile tile) {
        List<Light> lightList = smartHome.getDevicesByType(Light.class, place);
        lightList.sort(new LightComparator());

        SwitchManageKeyBoard kb = new SwitchManageKeyBoard();
        for (Light light : lightList)
            kb.addDevice(light);

        tile.update()
                .setCaption(description + " " + place.getName())
                .setKeyboard(kb);
    }
}
