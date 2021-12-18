package org.rch.jarvisapp.bot.actions.scenario;

import org.rch.jarvisapp.AppContextHolder;
import org.rch.jarvisapp.bot.actions.Action;
import org.rch.jarvisapp.bot.enums.CommonCallBack;
import org.rch.jarvisapp.bot.exceptions.HomeApiWrongResponseData;
import org.rch.jarvisapp.bot.ui.Tile;
import org.rch.jarvisapp.bot.ui.button.Button;
import org.rch.jarvisapp.bot.ui.button.LightButton;
import org.rch.jarvisapp.bot.ui.keyboard.KeyBoard;
import org.rch.jarvisapp.bot.ui.keyboard.LightKeyBoard;
import org.rch.jarvisapp.smarthome.SmartHome;
import org.rch.jarvisapp.smarthome.areas.Place;
import org.rch.jarvisapp.smarthome.devices.Device;
import org.rch.jarvisapp.smarthome.devices.Light;
import org.rch.jarvisapp.smarthome.devices.filters.Predicates;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;

public class ShowLightsOn implements Action {
    SmartHome smartHome = AppContextHolder.getSH();
    List<Predicate<Device>> listPredicate = new ArrayList<>();

    {
        listPredicate.add(Predicates.isTypeOf(Light.class));
    }

    public ShowLightsOn(){}

    @Override
    public void run(Tile tile) throws HomeApiWrongResponseData {
        KeyBoard kbLight = new LightKeyBoard();

        listPredicate.add(Predicates.statusIs(true));

        List<Light> lightList = smartHome.getDevicesWithFilter(listPredicate,Light.class);







        //for (Light device : lightList)
      //      kbLight.addButton(device.getRow(), new LightButton(device));
        Map<Place,List<Light>> map = new HashMap<>();

        for (Light device : lightList){
            if (!map.containsKey(device.getPlacement()))
                map.put(device.getPlacement(),new ArrayList<>());
            map.get(device.getPlacement()).add(device);
        }


        int row = 1;
        for (Place pl :  map.keySet()){
            kbLight.addButton(row++,new Button(pl.getFormattedName(), CommonCallBack.empty.name()));
            for(Light dev: map.get(pl))
                kbLight.addButton(row++, new LightButton(dev));
        }

        kbLight.refresh();

/*
        for (Button btn : kbLight.getButtonsList())
            btn.setVisible(((LightButton)btn).getStatus());
*/


        tile.update()
                .setCaption("Невыключенный свет")
                .setKeyboard(kbLight);
    }
}
