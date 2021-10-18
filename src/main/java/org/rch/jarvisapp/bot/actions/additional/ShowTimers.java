package org.rch.jarvisapp.bot.actions.additional;

import org.rch.jarvisapp.AppContextHolder;
import org.rch.jarvisapp.bot.actions.Action;
import org.rch.jarvisapp.bot.ui.Tile;
import org.rch.jarvisapp.bot.ui.button.comparators.LightComparator;
import org.rch.jarvisapp.bot.ui.keyboard.SwitchManageKeyBoard;
import org.rch.jarvisapp.smarthome.SmartHome;
import org.rch.jarvisapp.smarthome.devices.Light;


import java.util.List;

public class ShowTimers implements Action {
    public final static String description = "Активатор";
    SmartHome smartHome = AppContextHolder.getSH();
    String place;

    public ShowTimers(String place) {
        this.place = place;
    }

    @Override
    public void run(Tile tile) {
     //   List<TimerSupport> lightList = smartHome.getDevicesByType(TimerSupport.class, place);
        //lightList.sort(new LightComparator());

        SwitchManageKeyBoard kb = new SwitchManageKeyBoard();
/*        for (TimerSupport light : lightList)
            kb.addDevice(light);*/


        tile.update()
                .setCaption(description + " " + smartHome.getPlaceByCode(place).getName())
                .setKeyboard(kb);
    }
}
