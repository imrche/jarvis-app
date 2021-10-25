package org.rch.jarvisapp.bot.actions.windows;

import org.rch.jarvisapp.AppContextHolder;
import org.rch.jarvisapp.bot.actions.Action;
import org.rch.jarvisapp.bot.actions.RunnableByPlace;
import org.rch.jarvisapp.bot.actions.sensors.SensorUtils;
import org.rch.jarvisapp.bot.dataobject.WindowData;
import org.rch.jarvisapp.bot.enums.ParseMode;
import org.rch.jarvisapp.bot.exceptions.HomeApiWrongResponseData;
import org.rch.jarvisapp.bot.ui.Tile;
import org.rch.jarvisapp.bot.ui.button.Button;
import org.rch.jarvisapp.bot.ui.keyboard.KeyBoard;
import org.rch.jarvisapp.bot.utils.MD;
import org.rch.jarvisapp.smarthome.SmartHome;
import org.rch.jarvisapp.smarthome.areas.Place;
import org.rch.jarvisapp.smarthome.devices.Window;

import java.util.List;

public class ShowWindowsStatus implements Action, RunnableByPlace {
    public final static String description = "Статусы окон по помещению";

    private Place place;
    SmartHome smartHome = AppContextHolder.getSH();

    public ShowWindowsStatus() {}

    public ShowWindowsStatus(Place place) {
        this.place = place;
    }

    @Override
    public void run(Tile tile) throws HomeApiWrongResponseData {
        List<Place> places = place == null ? smartHome.getArea() : smartHome.getPlaceChildren(place);

        KeyBoard kb = new KeyBoard();
        for (Place place : places)
            kb.addButton(place.getRow(), new Button(place.getFormattedName(),new ShowWindowsStatus(place)));

        if (kb.getButtons().size() == 0){
            String captionValue;
            List<Window> list = smartHome.getDevicesByType(Window.class, place);
            if (!list.isEmpty()) {
                WindowData responseSD = SensorUtils.getWindowValues(list);

                StringBuilder result = new StringBuilder(MD.italic(place.getName()) + "\n");

                for (Window window : list){
                    result.append(MD.fixWidth(window.getName(), 20))
                            .append(MD.bold(responseSD.getWindowValue(window)))
                            .append("\n");
                }
                captionValue = result.toString();
            } else
                captionValue = "В помещении нет окон с датчиками";

            tile.update()
                    .setCaption(captionValue)
                    .setParseMode(ParseMode.Markdown)
                    .clearKeyboard();
        } else
            tile.update()
                    .setCaption(description + (place != null ? " - " + place.getName() : ""))
                    .setKeyboard(kb);
    }

    @Override
    public void setPlace(Place place) {
        this.place = place;
    }

    public Place getPlace() {
        return place;
    }

    @Override
    public int hashCode() {
        return (place == null ? "emptyPlace".hashCode() : place.hashCode()) + this.getClass().hashCode();
    }
}
