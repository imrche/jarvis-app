package org.rch.jarvisapp.bot.services;

import org.rch.jarvisapp.bot.dataobject.ActionData;
import org.rch.jarvisapp.bot.enums.ActionType;
import org.rch.jarvisapp.bot.enums.ParseMode;
import org.rch.jarvisapp.bot.ui.Tile;
import org.rch.jarvisapp.bot.ui.button.Button;
import org.rch.jarvisapp.bot.ui.keyboard.KeyBoard;
import org.rch.jarvisapp.bot.utils.MD;
import org.rch.jarvisapp.smarthome.SmartHome;
import org.rch.jarvisapp.smarthome.areas.Place;
import org.rch.jarvisapp.smarthome.devices.Sensor;
import org.rch.jarvisapp.smarthome.enums.SensorTypes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SensorService {

    @Autowired
    SmartHome smartHome;

    public void showTemperature(Tile tile, ActionData actionData){
        List<Sensor> list = smartHome.getSensors(SensorTypes.temperature);
        StringBuilder result = new StringBuilder(MD.italic("Температура") + "\n");

        for (Sensor sensor : list){
            result.append(MD.fixWidth(sensor.getPlacement().getName(), 20)).append(MD.bold("XX")).append("°").append("\n");
        }

        tile.update()
                .setCaption(result.toString())
                .setParseMode(ParseMode.Markdown)
                .clearKeyboard();
    }

    public void showSensorsStatus(Tile tile, ActionData actionData){
        ActionType type = ActionType.showSensorsStatus;
        if (actionData.has(ActionData.PLACE)){
            String placeCode = actionData.getPlace();
            List<Place> places = placeCode.isEmpty() ? smartHome.getArea() : smartHome.getPlaceChildren(placeCode);

            KeyBoard kb = new KeyBoard();
            for (Place place : places)
                kb.addButton(1, new Button(place.getName(),new ActionData(type, place.getCode(), "")));

            //пока полагаем что датчики есть только в конечных помещениях
            if (kb.getButtons().size() == 0){
                List<Sensor> list = smartHome.getSensors(placeCode);
                StringBuilder result = new StringBuilder(MD.italic(smartHome.getPlaceByCode(placeCode).getName()) + "\n");

                for (Sensor sensor : list){
                    result.append(MD.fixWidth(sensor.getSensorType().getDescription(), 20)).append(MD.bold("XX")).append(sensor.getSensorType().getUnit()).append("\n");
                }

                tile.update()
                        .setCaption(result.toString())
                        .setParseMode(ParseMode.Markdown)
                        .clearKeyboard();
            } else {
                tile.update()
                        .setCaption(type.getDescription() + (!placeCode.isEmpty() ? " - " + smartHome.getPlaceByCode(placeCode).getName() : ""))
                        .setKeyboard(kb);
            }

        }
    }
}
