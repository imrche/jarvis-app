package org.rch.jarvisapp.bot.actions.lights;

import org.rch.jarvisapp.bot.actions.Action;
import org.rch.jarvisapp.bot.exceptions.HomeApiWrongResponseData;
import org.rch.jarvisapp.bot.ui.Tile;
import org.rch.jarvisapp.bot.ui.keyboard.KeyBoard;
import org.rch.jarvisapp.bot.ui.keyboard.LightParamsKeyBoard;
import org.rch.jarvisapp.smarthome.devices.Light;

public class ShowLightProperties implements Action {
    private static final String description = "Параметры";
    private Light light;

    public ShowLightProperties(Light light) {
        this.light = light;
    }

    @Override
    public void run(Tile tile) throws HomeApiWrongResponseData {
        KeyBoard kbMain = new LightParamsKeyBoard(light);

        tile.update()
                .setCaption(description)
                .setKeyboard(kbMain);
    }

    @Override
    public int hashCode() {
        return light.hashCode() + this.getClass().hashCode();
    }
}
