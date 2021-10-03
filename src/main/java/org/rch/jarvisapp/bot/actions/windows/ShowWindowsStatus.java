package org.rch.jarvisapp.bot.actions.windows;

import org.rch.jarvisapp.AppContextHolder;
import org.rch.jarvisapp.bot.actions.Action;
import org.rch.jarvisapp.bot.actions.RunnableByPlace;
import org.rch.jarvisapp.bot.actions.sensors.SensorUtils;
import org.rch.jarvisapp.bot.dataobject.SensorData;
import org.rch.jarvisapp.bot.dataobject.WindowData;
import org.rch.jarvisapp.bot.enums.ParseMode;
import org.rch.jarvisapp.bot.ui.Tile;
import org.rch.jarvisapp.bot.ui.button.Button;
import org.rch.jarvisapp.bot.ui.keyboard.KeyBoard;
import org.rch.jarvisapp.bot.utils.MD;
import org.rch.jarvisapp.smarthome.SmartHome;
import org.rch.jarvisapp.smarthome.areas.Place;
import org.rch.jarvisapp.smarthome.devices.Sensor;
import org.rch.jarvisapp.smarthome.devices.Window;

import java.util.List;

public class ShowWindowsStatus implements Action, RunnableByPlace {
    public final static String description = "Статусы окон по помещению";

    private String place;
    SmartHome smartHome = AppContextHolder.getSH();

    public ShowWindowsStatus() {}

    public ShowWindowsStatus(String place) {
        this.place = place;
    }

    @Override
    public void run(Tile tile) {
        if (place != null){
            List<Place> places = place.isEmpty() ? smartHome.getArea() : smartHome.getPlaceChildren(place);

            KeyBoard kb = new KeyBoard();
            for (Place place : places)
                kb.addButton(1, new Button(place.getName(),new ShowWindowsStatus(place.getCode())));

            if (kb.getButtons().size() == 0){
                List<Window> list = smartHome.getDevicesByType(Window.class, place);
                WindowData responseSD = SensorUtils.getWindowValues(list);

                StringBuilder result = new StringBuilder(MD.italic(smartHome.getPlaceByCode(place).getName()) + "\n");

                for (Window window : list){
                    result.append(MD.fixWidth(window.getName(), 20))
                            .append(MD.bold(responseSD.getWindowValue(window)))
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
