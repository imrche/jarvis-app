package org.rch.jarvisapp.bot.actions.additional;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import org.rch.jarvisapp.AppContextHolder;
import org.rch.jarvisapp.bot.actions.Action;
import org.rch.jarvisapp.bot.actions.RunnableByPlace;
import org.rch.jarvisapp.bot.ui.Tile;
import org.rch.jarvisapp.bot.ui.button.LightButton;
import org.rch.jarvisapp.bot.ui.button.comparators.LightComparator;
import org.rch.jarvisapp.bot.ui.keyboard.KeyBoard;
import org.rch.jarvisapp.bot.ui.keyboard.LightKeyBoard;
import org.rch.jarvisapp.smarthome.SmartHome;
import org.rch.jarvisapp.smarthome.devices.Light;

import java.util.List;

@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TimerProperties implements Action, RunnableByPlace {
    String place;
    final SmartHome smartHome = AppContextHolder.getSH();


    public TimerProperties(String place) {
        this.place = place;
    }

    @Override
    public void run(Tile tile) {
        KeyBoard kbLight = new LightKeyBoard();
        List<Light> lightList = smartHome.getDevicesByType(Light.class, place);
        lightList.sort(new LightComparator());
        for (Light device : lightList)
            kbLight.addButton(device.getRow(), new LightButton(device).build(true));

        tile.update()
                .setCaption("Таймеры")
                .setKeyboard(kbLight);
    }

    @Override
    public void setPlace(String place) {
        this.place = place;
    }

    @Override
    public int hashCode() {
        return place.hashCode() + this.getClass().hashCode();
    }
}
