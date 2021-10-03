package org.rch.jarvisapp.bot.ui.button;

import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.FieldDefaults;
import org.rch.jarvisapp.AppContextHolder;
import org.rch.jarvisapp.bot.dataobject.DeviceCommandData;
import org.rch.jarvisapp.bot.actions.lights.ReverseLightAction;
import org.rch.jarvisapp.smarthome.api.Api;
import org.rch.jarvisapp.smarthome.devices.Light;


@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@EqualsAndHashCode(callSuper = true)
public class LightButton extends Button{
    Light light;
    //String state;
    Boolean state2;

    DeviceCommandData patternCD;

    public LightButton(Light light){
        super();
        this.light = light;
        patternCD = new DeviceCommandData().addDevice(light.getId());
    }

    public LightButton setCaption() {
        super.setText(light.getName() + (state2 != null ? " " + visualize(state2) : ""));
        return this;
    }

    public static String visualize(Boolean value){
        if (value == null) return "?";

        String on = "\uD83C\uDF15";
        String off = "\uD83C\uDF11";

        return value ? on : off;
    }

    public void getCurrentState(){
        Api api = AppContextHolder.getApi();
        DeviceCommandData cd = api.getStatusLight(patternCD);
        state2 = cd.getDeviceBooleanValue(light.getId());
    }

/*    public Button refresh(){
        getCurrentState();
        setText(getCaption());
        return this;
    }*/

    public Button build(){
        return build(false);
    }
    public Button build(boolean withoutState){
        if (!withoutState)
            getCurrentState();
        setCaption();
        setCallbackData(new ReverseLightAction(patternCD).caching());

        return this;
    }
}
