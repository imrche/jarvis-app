package org.rch.jarvisapp.bot.actions.lights;

import org.rch.jarvisapp.AppContextHolder;
import org.rch.jarvisapp.bot.actions.Action;
import org.rch.jarvisapp.bot.actions.RunnableByPlace;
import org.rch.jarvisapp.bot.exceptions.HomeApiWrongResponseData;
import org.rch.jarvisapp.bot.ui.Tile;
import org.rch.jarvisapp.bot.ui.button.LightButton;
import org.rch.jarvisapp.bot.ui.button.comparators.LightComparator;
import org.rch.jarvisapp.bot.ui.keyboard.KeyBoard;
import org.rch.jarvisapp.bot.ui.keyboard.LightKeyBoard;
import org.rch.jarvisapp.bot.ui.keyboard.PlaceKeyboard;
import org.rch.jarvisapp.smarthome.SmartHome;
import org.rch.jarvisapp.smarthome.areas.Place;
import org.rch.jarvisapp.smarthome.devices.Device;
import org.rch.jarvisapp.smarthome.devices.Light;
import org.rch.jarvisapp.smarthome.devices.filters.Predicates;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Predicate;

//когда ставлю аннотацию Data, то спринг начинает создавать бины по новой или что-то типа и зовутся методы getPlaces и getDevices todo разобраться

public class ShowLight implements Action, RunnableByPlace {
    private String description = "Освещение";
    private Place place;

    LinkedList<KeyBoard> kbList = new LinkedList<>();

    SmartHome smartHome = AppContextHolder.getSH();

    List<Predicate<Device>> listPredicate = new ArrayList<>();

    {
        listPredicate.add(Predicates.isTypeOf(Light.class));
    }

    public ShowLight() {
        fillLightKeyboard();
    }

    public ShowLight(Place place) {
        this.place = place;
        kbList.add(new PlaceKeyboard(place, ShowLight.class));
        listPredicate.add(Predicates.isPlaceLaying(place));
        description += " - " + place.getName();
        fillLightKeyboard();
    }

    @Override
    public void setPlace(Place place) {
    }

    public Place getPlace() {
        return  place;
    }

    private void fillLightKeyboard(){
        KeyBoard kbLight = new LightKeyBoard();

        List<Light> lightList = smartHome.getDevicesWithFilter(listPredicate,Light.class);
        lightList.sort(new LightComparator());

        for (Light device : lightList)
            kbLight.addButton(device.getRow(), new LightButton(device));

        if (kbLight.getButtonsList().size() > 0)
            kbList.add(kbLight);
    }

    @Override
    public void run(Tile tile) throws HomeApiWrongResponseData {
        KeyBoard kbMain = new LightKeyBoard();

        for (KeyBoard kb : kbList){
            if (kb instanceof LightKeyBoard)
                kb.refresh();
        }

        for (KeyBoard kb : kbList)
            kbMain.merge(kb);

        tile.update()
                .setCaption(description)
                .setKeyboard(kbMain);
    }

    @Override
    public int hashCode() {
        return (place == null ? "emptyPlace".hashCode() : place.hashCode()) + this.getClass().hashCode();
    }
}
