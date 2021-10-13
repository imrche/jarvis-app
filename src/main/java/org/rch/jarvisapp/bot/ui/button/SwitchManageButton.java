package org.rch.jarvisapp.bot.ui.button;

import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.FieldDefaults;
import org.rch.jarvisapp.AppContextHolder;
import org.rch.jarvisapp.bot.actions.additional.ReverseSWManage;
import org.rch.jarvisapp.bot.actions.lights.ReverseLight;
import org.rch.jarvisapp.bot.dataobject.DeviceCommandData;
import org.rch.jarvisapp.bot.exceptions.DeviceStatusIsUnreachable;
import org.rch.jarvisapp.smarthome.devices.Light;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@EqualsAndHashCode(callSuper = true)
public class SwitchManageButton extends Button{
    Light light;
    Boolean status;

    final DeviceCommandData patternCD = new DeviceCommandData();

    public SwitchManageButton(Light light){
        this.light = light;
        patternCD.addDevice(light.getId());
        setCallbackData(new ReverseSWManage(patternCD).caching());
    }

    public void setCaption() {
        super.setCaption(light.getName() + " " + visualizeStatus(status));
    }

    public static String visualizeStatus(Boolean status){
        if (status == null) return "[❓]";

        String on = "\uD83D\uDFE2";
        String off = "\uD83D\uDD34";

        return status ? on : off;
    }

    @Override
    public void refresh() {
        DeviceCommandData cd = AppContextHolder.getApi().getStatusSwitchManager(patternCD);
        try {
            status = cd.getDeviceBooleanValue(light.getId());
        } catch (DeviceStatusIsUnreachable e) {
            status = null;
        }
        setCaption();
    }
}
