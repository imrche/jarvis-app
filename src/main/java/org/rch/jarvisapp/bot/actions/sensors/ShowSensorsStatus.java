package org.rch.jarvisapp.bot.actions.sensors;

import org.rch.jarvisapp.AppContextHolder;
import org.rch.jarvisapp.bot.actions.Action;
import org.rch.jarvisapp.bot.actions.RunnableByPlace;
import org.rch.jarvisapp.bot.dataobject.SensorData;
import org.rch.jarvisapp.bot.enums.ParseMode;
import org.rch.jarvisapp.bot.exceptions.HomeApiWrongResponseData;
import org.rch.jarvisapp.bot.ui.Tile;
import org.rch.jarvisapp.bot.ui.button.Button;
import org.rch.jarvisapp.bot.ui.keyboard.KeyBoard;
import org.rch.jarvisapp.bot.utils.MD;
import org.rch.jarvisapp.smarthome.SmartHome;
import org.rch.jarvisapp.smarthome.areas.Place;
import org.rch.jarvisapp.smarthome.devices.Sensor;

import java.util.List;

public class ShowSensorsStatus implements Action, RunnableByPlace {
    public final static String description = "Статусы по помещению";

    private String place;
    SmartHome smartHome = AppContextHolder.getSH();

    public ShowSensorsStatus() {}

    public ShowSensorsStatus(String place) {
        this.place = place;
    }

    @Override
    public void run(Tile tile) throws HomeApiWrongResponseData {
        if (place != null){
            List<Place> places = place.isEmpty() ? smartHome.getArea() : smartHome.getPlaceChildren(place);

            KeyBoard kb = new KeyBoard();
            for (Place place : places)
                kb.addButton(1, new Button(place.getName(),new ShowSensorsStatus(place.getCode())));

            //пока полагаем что датчики есть только в конечных помещениях
            if (kb.getButtons().size() == 0){
                List<Sensor> list = smartHome.getDevicesByType(Sensor.class, place);
                SensorData responseSD = SensorUtils.getSensorValues(list);

                StringBuilder result = new StringBuilder(MD.italic(smartHome.getPlaceByCode(place).getName()) + "\n");

                for (Sensor sensor : list){
                    result.append(MD.fixWidth(sensor.getSensorType().getDescription(), 20))
                            .append(MD.bold(responseSD.getSensorValue(sensor)))
                            .append(sensor.getSensorType().getUnit())
                            .append("\n");
                }

                tile.update()
                        .setCaption(result.toString())
                        .setParseMode(ParseMode.Markdown)
                        .clearKeyboard();
            } else
                tile.update()
                        .setCaption(description + (!place.isEmpty() ? " - " + smartHome.getPlaceByCode(place).getName() : ""))
                        .setKeyboard(kb);
        }
    }

    @Override
    public void setPlace(String place) {
        this.place = place;
    }

    public String getPlace() {
        return  place;
    }
}
