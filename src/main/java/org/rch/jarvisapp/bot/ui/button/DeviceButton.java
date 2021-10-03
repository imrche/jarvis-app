package org.rch.jarvisapp.bot.ui.button;

import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.FieldDefaults;
import org.rch.jarvisapp.AppContextHolder;
import org.rch.jarvisapp.bot.actions.devices.ReverseDevice;
import org.rch.jarvisapp.bot.dataobject.DeviceCommandData;
import org.rch.jarvisapp.bot.exceptions.DeviceStatusIsUnreachable;
import org.rch.jarvisapp.smarthome.api.Api;
import org.rch.jarvisapp.smarthome.devices.Device;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@EqualsAndHashCode(callSuper = true)
public class DeviceButton extends Button{
    Device device;
    Boolean state;

    DeviceCommandData patternCD;

    public DeviceButton(Device device){
        super();
        this.device = device;
        patternCD = new DeviceCommandData().addDevice(device.getId());
    }

    public DeviceButton setCaption() {
        super.setText(device.getPlacement().getName() + " " + visualize(state));
        return this;
    }

    public static String visualize(Boolean value){
        if (value == null) return "❓";

        String on = "\uD83D\uDD18";
        String off = "⚫";

        return value ? on : off;
    }

    public void getCurrentState(){
        Api api = AppContextHolder.getApi();
        DeviceCommandData cd = api.getStatusDevice(patternCD);
        try {
            state = cd.getDeviceBooleanValue(device.getId());
        } catch (DeviceStatusIsUnreachable e) {
            state = null;//todo tmp
        }
    }

    public Button build(boolean withoutState){
        if (!withoutState)
            getCurrentState();
        setCaption();
        setCallbackData(new ReverseDevice(patternCD).caching());

        return this;
    }
}
