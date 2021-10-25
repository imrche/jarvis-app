package org.rch.jarvisapp.bot.actions.lights;

import org.rch.jarvisapp.AppContextHolder;
import org.rch.jarvisapp.bot.actions.Action;
import org.rch.jarvisapp.bot.actions.RunnableByPlace;
import org.rch.jarvisapp.bot.exceptions.HomeApiWrongResponseData;
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

    private Place place;
    SmartHome smartHome = AppContextHolder.getSH();

    public ShowLight() {}

    public ShowLight(Place place) {
        this.place = place;
    }

    @Override
    public void setPlace(Place place) {
        this.place = place;
    }

    public Place getPlace() {
        return  place;
    }

    @Override
    public void run(Tile tile) throws HomeApiWrongResponseData {
        List<Place> places = place == null ? smartHome.getArea() : smartHome.getPlaceChildren(place);

        KeyBoard kb = new LightKeyBoard(place);
        for (Place place : places) {//todo make a filter for hiding places without device of this class
            kb.addButton(place.getRow(), new Button(place.getFormattedName(), new ShowLight(place)));
        }

        KeyBoard kbLight = new LightKeyBoard();

        List<Light> lightList = smartHome.getDevicesByType(Light.class, place);
        lightList.sort(new LightComparator());
        for (Light device : lightList)
            kbLight.addButton(device.getRow(), new LightButton(device));

        if (kbLight.getButtonsList().size() > 0) {
            kbLight.refresh();
            kb.merge(kbLight);
        }

        tile.update()
                .setCaption(description + (place != null ? " - " + place.getName() : ""))
                .setKeyboard(kb);
    }
    @Override
    public int hashCode() {
        return (place == null ? "emptyPlace".hashCode() : place.hashCode()) + this.getClass().hashCode();
    }
}
