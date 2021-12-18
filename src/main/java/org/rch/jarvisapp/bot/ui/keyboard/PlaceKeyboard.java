package org.rch.jarvisapp.bot.ui.keyboard;

import org.rch.jarvisapp.AppContextHolder;
import org.rch.jarvisapp.bot.actions.Action;
import org.rch.jarvisapp.bot.ui.button.Button;
import org.rch.jarvisapp.smarthome.SmartHome;
import org.rch.jarvisapp.smarthome.areas.Place;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

public class PlaceKeyboard extends KeyBoard{
    SmartHome sh = AppContextHolder.getSH();
    Logger logger = LoggerFactory.getLogger(this.getClass());
    Constructor<?> actionConstructor;

    public PlaceKeyboard(Place place, Class<? extends Action> actionClass){
        assignConstructor(actionClass);
        construct(sh.getPlaceChildren(place));
    }

    public PlaceKeyboard(Class<? extends Action> actionClass){
        assignConstructor(actionClass);
        construct(sh.getArea());
    }

    private void assignConstructor(Class<? extends Action> actionClass){
        try {
            actionConstructor = actionClass.getConstructor(Place.class);
        } catch (NoSuchMethodException e) {
            logger.error("Не обнаружен конструктор для действия ");//todo get down
        }
    }

    public void construct(List<Place> places) {
        if (places.size() == 0) return;//todo не понимаю почему ломается на пустом списке
        try {
            for (Place place : places) {//todo make a filter for hiding places without device of this class
                //if (smartHome.hasDevicesOfType(Light.class, place))
                addButton(place.getRow(), new Button(place.getFormattedName(), (Action)actionConstructor.newInstance(place)));
            }
        } catch (InstantiationException | InvocationTargetException | IllegalAccessException e) {
            logger.error("Конструктор недоступен! ");//todo get down
        }
    }
}
