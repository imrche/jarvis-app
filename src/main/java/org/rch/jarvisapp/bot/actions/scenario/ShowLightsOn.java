package org.rch.jarvisapp.bot.actions.scenario;

import org.rch.jarvisapp.AppContextHolder;
import org.rch.jarvisapp.bot.actions.Action;
import org.rch.jarvisapp.bot.exceptions.HomeApiWrongResponseData;
import org.rch.jarvisapp.bot.ui.Tile;
import org.rch.jarvisapp.bot.ui.button.Button;
import org.rch.jarvisapp.bot.ui.button.LightButton;
import org.rch.jarvisapp.bot.ui.keyboard.KeyBoard;
import org.rch.jarvisapp.bot.ui.keyboard.LightKeyBoard;
import org.rch.jarvisapp.smarthome.SmartHome;
import org.rch.jarvisapp.smarthome.devices.Device;
import org.rch.jarvisapp.smarthome.devices.Light;
import org.rch.jarvisapp.smarthome.devices.filters.Predicates;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

public class ShowLightsOn implements Action {
    SmartHome smartHome = AppContextHolder.getSH();
    List<Predicate<Device>> listPredicate = new ArrayList<>();
    {
        listPredicate.add(Predicates.isTypeOf(Light.class));
    }

    public ShowLightsOn(){

    }

    @Override
    public void run(Tile tile) throws HomeApiWrongResponseData {
        KeyBoard kbLight = new LightKeyBoard();

        List<Light> lightList = smartHome.getDevicesWithFilter(listPredicate,Light.class);

        for (Light device : lightList)
            kbLight.addButton(device.getRow(), new LightButton(device));

        kbLight.refresh();

        for (Button btn : kbLight.getButtonsList())
            btn.setVisible(((LightButton)btn).getStatus());

        tile.update()
                .setCaption("Невыключенный свет")
                .setKeyboard(kbLight);

    }
}
