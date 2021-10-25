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

    private Place place;
    SmartHome smartHome = AppContextHolder.getSH();

    public ShowSensorsStatus() {}

    public ShowSensorsStatus(Place place) {
        this.place = place;
    }

    @Override
    public void run(Tile tile) throws HomeApiWrongResponseData {
        List<Place> places = place == null ? smartHome.getArea() : smartHome.getPlaceChildren(place);

        KeyBoard kb = new KeyBoard();
        for (Place place : places)
            kb.addButton(place.getRow(), new Button(place.getFormattedName(),new ShowSensorsStatus(place)));

        //пока полагаем что датчики есть только в конечных помещениях
        if (kb.getButtons().size() == 0){
            List<Sensor> list = smartHome.getDevicesByType(Sensor.class, place);
            String captionValue;
            if (!list.isEmpty()) {
                SensorData responseSD = SensorUtils.getSensorValues(list);

                StringBuilder result = new StringBuilder(MD.italic(place.getName()) + "\n");

                for (Sensor sensor : list) {
                    result.append(MD.fixWidth(sensor.getSensorType().getDescription(), 20))
                            .append(MD.bold(responseSD.getSensorValue(sensor)))
                            .append(sensor.getSensorType().getUnit())
                            .append("\n");
                }
                captionValue = result.toString();
            } else
                captionValue = "В помещении датчиков нет";


            tile.update()
                    .setCaption(captionValue)
                    .setParseMode(ParseMode.Markdown)
                    .clearKeyboard();
        } else
            tile.update()
                    .setCaption(description + ( place!=null ? " - " + place.getName() : ""))
                    .setKeyboard(kb);
    }

    @Override
    public void setPlace(Place place) {
        this.place = place;
    }

    public Place getPlace() {
        return  place;
    }

    @Override
    public int hashCode() {
        return (place == null ? "emptyPlace".hashCode() : place.hashCode()) + this.getClass().hashCode();
    }
}
