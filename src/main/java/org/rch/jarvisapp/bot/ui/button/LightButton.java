package org.rch.jarvisapp.bot.ui.button;

import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.FieldDefaults;
import org.rch.jarvisapp.AppContextHolder;
import org.rch.jarvisapp.bot.actions.lights.ReverseLight;
import org.rch.jarvisapp.bot.dataobject.SwitcherData;
import org.rch.jarvisapp.bot.exceptions.HomeApiWrongResponseData;
import org.rch.jarvisapp.smarthome.devices.Light;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@EqualsAndHashCode(callSuper = true)
public class LightButton extends Button{
    Light light;
    Boolean status;

    final SwitcherData patternCD = new SwitcherData();

    public LightButton(Light light){
        this.light = light;
        patternCD.addSwitcher(light);
        setCallBack(new ReverseLight(patternCD));
    }

    public void setCaption() {
        super.setCaption(light.getName() + " " + visualizeStatus(status));
    }

    public static String visualizeStatus(Boolean status){
        if (status == null) return "[❓]";

        String on = "\uD83C\uDF15";
        String off = "\uD83C\uDF11";

        return status ? on : off;
    }

    @Override
    public void refresh() throws HomeApiWrongResponseData {
        SwitcherData sd = AppContextHolder.getApi().getStatusLight(patternCD);
        status = sd.getDeviceValue(light);

        setCaption();
    }
}
