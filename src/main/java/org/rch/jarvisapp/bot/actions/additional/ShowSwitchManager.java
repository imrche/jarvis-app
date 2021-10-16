package org.rch.jarvisapp.bot.actions.additional;

import org.rch.jarvisapp.AppContextHolder;
import org.rch.jarvisapp.bot.actions.Action;
import org.rch.jarvisapp.bot.enums.CommonCallBack;
import org.rch.jarvisapp.bot.ui.Tile;
import org.rch.jarvisapp.bot.ui.button.Button;
import org.rch.jarvisapp.bot.ui.button.SwitchManageButton;
import org.rch.jarvisapp.bot.ui.button.comparators.LightComparator;
import org.rch.jarvisapp.bot.ui.keyboard.KeyBoard;
import org.rch.jarvisapp.bot.ui.keyboard.SwitchManageKeyBoard;
import org.rch.jarvisapp.smarthome.SmartHome;
import org.rch.jarvisapp.smarthome.devices.Light;

import java.util.List;

public class ShowSwitchManager implements Action {
    public final static String description = "Активатор";
    SmartHome smartHome = AppContextHolder.getSH();
    String place;
    public String dummy;

    public ShowSwitchManager(String place) {
        this.place = place;
    }

    @Override
    public void run(Tile tile) {
        List<Light> lightList = smartHome.getDevicesByType(Light.class, place);
        lightList.sort(new LightComparator());

/*        KeyBoard kbLight = new KeyBoard();
        for (Light device : lightList)
            //kbLight.addButton(device.getRow(), new Button(device.getName(),CommonCallBack.empty.name()));
            kbLight.addButton(device.getRow(), new SwitchManageButton(device));*/

        SwitchManageKeyBoard kb = new SwitchManageKeyBoard();
        for (Light light : lightList)
            kb.addDevice(light);


        tile.update()
                .setCaption(description + " " + smartHome.getPlaceByCode(place).getName())
                .setKeyboard(kb);
    }
}
