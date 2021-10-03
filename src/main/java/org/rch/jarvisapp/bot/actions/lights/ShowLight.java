package org.rch.jarvisapp.bot.actions.lights;

import org.rch.jarvisapp.AppContextHolder;
import org.rch.jarvisapp.bot.actions.Action;
import org.rch.jarvisapp.bot.actions.RunnableByPlace;

import org.rch.jarvisapp.bot.ui.Tile;
import org.rch.jarvisapp.bot.ui.button.Button;
import org.rch.jarvisapp.bot.ui.button.LightButton;
import org.rch.jarvisapp.bot.ui.button.comparators.LightComparator;
import org.rch.jarvisapp.bot.ui.keyboard.KeyBoard;
import org.rch.jarvisapp.bot.ui.keyboard.LightKeyBoard;
import org.rch.jarvisapp.smarthome.SmartHome;
import org.rch.jarvisapp.smarthome.areas.Place;
import org.rch.jarvisapp.smarthome.devices.Light;

import java.util.List;

//когда ставлю аннотацию Data, то спринг начинает создавать бины по новой или что-то типа и зовутся методы getPlaces и getDevices todo разобраться

public class ShowLight implements Action, RunnableByPlace {
    public final static String description = "Освещение";

    private String place;
    SmartHome smartHome = AppContextHolder.getSH();

    public ShowLight() {}

    public ShowLight(String place) {
        this.place = place;
    }

    @Override
    public void setPlace(String place) {
        this.place = place;
    }

    public String getPlace() {
        return  place;
    }

    @Override
    public void run(Tile tile){
        if (place != null){
            List<Place> places = place.isEmpty() ? smartHome.getArea() : smartHome.getPlaceChildren(place);

            KeyBoard kb = new LightKeyBoard(place);
            for (Place place : places)
                kb.addButton(1, new Button(place.getName(), new ShowLight(place.getCode())));

            KeyBoard kbLight = new LightKeyBoard();
            List<Light> lightList = smartHome.getDevicesByType(Light.class, place);
            lightList.sort(new LightComparator());
            for (Light device : lightList)
                kbLight.addButton(device.getRow(), new LightButton(device).build(true));

            if (kbLight.getButtonsList().size() > 0) {
                kbLight.refresh();
                kb.merge(kbLight);
            }

            tile.update()
                    .setCaption(description + (!place.isEmpty() ? " - " + smartHome.getPlaceByCode(place).getName() : ""))
                    .setKeyboard(kb);
        }
    }
    @Override
    public int hashCode() {
        return place.hashCode() + this.getClass().hashCode();
    }
}
