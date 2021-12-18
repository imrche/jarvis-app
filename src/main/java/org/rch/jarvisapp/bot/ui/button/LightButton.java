package org.rch.jarvisapp.bot.ui.button;

import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.FieldDefaults;
import org.rch.jarvisapp.AppContextHolder;
import org.rch.jarvisapp.bot.actions.Action;
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

    Mode currentMode;

    Action switchAction;
    Action menuAction;

    static class ModeData{
        Action action;

        public Action getAction() {
            return action;
        }
        public void setAction(Action action) {
            this.action = action;
        }
    }

    public enum Mode{
        switcher(new ModeData()),
        menu(new ModeData());

        ModeData data;
        Mode(ModeData data){
            this.data = data;
        }

        public Action getAction(){
            return data.getAction();
        }

        public void setAction(Action action){
            data.setAction(action);
        }
    }

    final SwitcherData patternCD = new SwitcherData();

    public LightButton(Light light){
        this.light = light;
        patternCD.addSwitcher(light);
        //setCallBack(new ReverseLight(patternCD));
        Mode.switcher.setAction(new ReverseLight(patternCD));

        setMode(Mode.switcher);
    }

    public void setMode(Mode mode){
       currentMode = mode;
       setCallBack(mode.getAction());
    }




    public void setCaption() {
        String caption;
        switch (currentMode){
            case switcher: caption = light.getName() + " " + visualizeStatus(status);
                break;
            case menu: caption = light.getName();
            default: caption = "?";
        }
        super.setCaption(caption);
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
