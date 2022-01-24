package org.rch.jarvisapp.bot.ui.button;

import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.FieldDefaults;
import org.rch.jarvisapp.AppContextHolder;
import org.rch.jarvisapp.bot.actions.Action;
import org.rch.jarvisapp.bot.actions.additional.ReverseSWManage;
import org.rch.jarvisapp.bot.actions.lights.ReverseLight;
import org.rch.jarvisapp.bot.actions.lights.ShowLightProperties;
import org.rch.jarvisapp.bot.actions.lights.ShowTimerBuilder;
import org.rch.jarvisapp.bot.dataobject.SwitcherData;
import org.rch.jarvisapp.bot.enums.StatusVisual;
import org.rch.jarvisapp.bot.exceptions.HomeApiWrongResponseData;
import org.rch.jarvisapp.bot.ui.button.func_interface.CaptionUpdater;
import org.rch.jarvisapp.smarthome.devices.Light;

import java.util.HashMap;
import java.util.Map;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@EqualsAndHashCode(callSuper = true)
public class LightButton extends Button{
    Light light;
    Boolean status;
    Mode currentMode;
    Map<Mode,ModeData> modeActionsMap = new HashMap<>();
    final SwitcherData patternCD = new SwitcherData();

    static class ModeData{
        Action action;
        CaptionUpdater captionUpdater;

        public ModeData(Action action, CaptionUpdater captionUpdater) {
            this.action = action;
            this.captionUpdater = captionUpdater;
        }

        public Action getAction() {
            return action;
        }
        public String getCaption() {
            return captionUpdater.getCaption();
        }
    }

    public enum Mode{
        SWITCHER("Выключатели"),
        CONNECT_MANAGE("Доступность"),
        LIGHT_PARAMS("Свойства"),
        TIMER("Таймеры");

        String name;

        Mode(String name){
            this.name = name;
        }

        public String getName(){return name;}

        public Mode getNext(){
            if (ordinal() == values().length - 1)
                return values()[0];
            return values()[ordinal()+1];
        }
    }
    
    public LightButton(Light light){
        this.light = light;
        patternCD.addSwitcher(light);

        modeActionsMap.put(Mode.SWITCHER, new ModeData(new ReverseLight(patternCD), () -> light.getName() + " " + StatusVisual.LIGHT_SWITCHER.get(status)));
        modeActionsMap.put(Mode.CONNECT_MANAGE, new ModeData(new ReverseSWManage(patternCD), () -> light.getName() + " " + StatusVisual.CONNECT_MANAGER.get(status)));
        modeActionsMap.put(Mode.TIMER, new ModeData(new ShowTimerBuilder(light), () -> light.getName() + "⏲"));
        modeActionsMap.put(Mode.LIGHT_PARAMS, new ModeData(new ShowLightProperties(light), () -> light.getName() + " " + "\uD83C\uDF9A"));

        setMode(Mode.SWITCHER);
    }

    public void setMode(Mode mode){
       currentMode = mode;
       setCallBack(modeActionsMap.get(mode).getAction());
       setCaption();
    }

    public void setCaption() {
        super.setCaption(modeActionsMap.get(currentMode).getCaption());
    }

    public void setStatus(Boolean status) {
        this.status = status;
        setCaption();
    }

    @Override
    public void refresh() throws HomeApiWrongResponseData {
        if (currentMode == Mode.SWITCHER) {
            SwitcherData sd = AppContextHolder.getApi().getStatusLight(patternCD);
            setStatus(sd.getDeviceValue(light));
        }
        if (currentMode == Mode.CONNECT_MANAGE) {
            SwitcherData cd = AppContextHolder.getApi().getStatusSwitchManager(patternCD);
            setStatus(cd.getDeviceValue(light));
        }
        if (currentMode == Mode.LIGHT_PARAMS)
            setVisible(false);
    }
}