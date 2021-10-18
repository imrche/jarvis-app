package org.rch.jarvisapp.bot.ui.button;

import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.FieldDefaults;
import org.rch.jarvisapp.AppContextHolder;
import org.rch.jarvisapp.bot.actions.devices.ReverseDevice;
import org.rch.jarvisapp.bot.dataobject.SwitcherData;
import org.rch.jarvisapp.bot.exceptions.HomeApiWrongResponseData;
import org.rch.jarvisapp.smarthome.api.Api;
import org.rch.jarvisapp.smarthome.devices.Device;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@EqualsAndHashCode(callSuper = true)
public class DeviceButton extends Button{
    Device device;
    Boolean state;

    SwitcherData patternCD = new SwitcherData();

    public DeviceButton(Device device){
        super();
        this.device = device;
        patternCD.addSwitcher(device);
    }

    public DeviceButton setCaption() {
        super.setCaption(device.getPlacement().getName() + " " + visualize(state));
        return this;
    }

    public static String visualize(Boolean value){
        if (value == null) return "❓";

        String on = "\uD83D\uDD18";
        String off = "⚫";

        return value ? on : off;
    }

    public void getCurrentState() throws HomeApiWrongResponseData {
        Api api = AppContextHolder.getApi();
        SwitcherData cd = api.getStatusDevice(patternCD);
        state = cd.getDeviceValue(device);

    }
//todo навести порядок как в light
    public Button build(boolean withoutState){
        if (!withoutState) {
            try {
                getCurrentState();
            } catch (HomeApiWrongResponseData homeApiWrongResponseData) {
                homeApiWrongResponseData.printStackTrace();
            }
        }
        setCaption();
        setCallBack(new ReverseDevice(patternCD));

        return this;
    }
}
