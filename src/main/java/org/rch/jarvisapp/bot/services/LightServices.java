package org.rch.jarvisapp.bot.services;

import org.rch.jarvisapp.bot.dataobject.ActionData;
import org.rch.jarvisapp.bot.dataobject.DeviceCommandData;
import org.rch.jarvisapp.bot.enums.ActionType;
import org.rch.jarvisapp.bot.ui.Tile;
import org.rch.jarvisapp.bot.ui.button.Button;
import org.rch.jarvisapp.bot.ui.button.LightButton;
import org.rch.jarvisapp.bot.ui.button.comparators.LightComparator;
import org.rch.jarvisapp.bot.ui.keyboard.KeyBoard;
import org.rch.jarvisapp.bot.ui.keyboard.LightKeyBoard;
import org.rch.jarvisapp.smarthome.SmartHome;
import org.rch.jarvisapp.smarthome.api.Api;
import org.rch.jarvisapp.smarthome.areas.Place;
import org.rch.jarvisapp.smarthome.devices.Light;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LightServices {

    Api api;
    SmartHome smartHome;

    public LightServices(Api api, SmartHome smartHome) {
        this.api = api;
        this.smartHome = smartHome;
    }

    public void setLight(Tile tile, ActionData actionData){
        api.setStatusLight(actionData.getBody());
        tile.refresh();
    }

    public void reverseLight(Tile tile,ActionData actionData){
        api.setStatusLight(api.getStatusLight(new DeviceCommandData(actionData.getBody())).reverse());
        //todo перенести реверс на сервер (возвращать статус, который получился по итогу)
        tile.refresh();
    }

    public void showLights(Tile tile, ActionData actionData){
        ActionType type = ActionType.showLights;
        if (actionData.has(ActionData.PLACE)){
            String placeCode = actionData.getPlace();
            List<Place> places = placeCode.isEmpty() ? smartHome.getArea() : smartHome.getPlaceChildren(placeCode);

            KeyBoard kb = new LightKeyBoard();
            for (Place place : places)
                kb.addButton(1, new Button(place.getName(),new ActionData(type, place.getCode(), "")));

            KeyBoard kbLight = new LightKeyBoard();
            List<Light> lightList = smartHome.getLights(placeCode);
            lightList.sort(new LightComparator());
            for (Light device : lightList)
                kbLight.addButton(device.getRow(), new LightButton(device).build(true));

            if (kbLight.getButtonsList().size() > 0) {
                kbLight.refresh();
                kb.merge(kbLight);
            }

            tile.update()
                    .setCaption(type.getDescription() + (!placeCode.isEmpty() ? " - " + smartHome.getPlaceByCode(placeCode).getName() : ""))
                    .setKeyboard(kb);
        }
    }
}
