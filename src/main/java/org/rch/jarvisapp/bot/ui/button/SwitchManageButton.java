package org.rch.jarvisapp.bot.ui.button;

import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.FieldDefaults;
import org.rch.jarvisapp.AppContextHolder;
import org.rch.jarvisapp.bot.actions.additional.ReverseSWManage;
import org.rch.jarvisapp.bot.dataobject.SwitcherData;
import org.rch.jarvisapp.bot.exceptions.HomeApiWrongResponseData;
import org.rch.jarvisapp.smarthome.devices.Light;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@EqualsAndHashCode(callSuper = true)
public class SwitchManageButton extends Button{
    Light light;
    Boolean status;

    final SwitcherData patternCD = new SwitcherData();

    public SwitchManageButton(Light light){
        this.light = light;
        patternCD.addSwitcher(light);
        setCallBack(new ReverseSWManage(patternCD));
    }

    public void setCaption() {
        super.setCaption(light.getName() + " " + visualizeStatus(status));
    }

    private String visualizeStatus(Boolean status){
        if (status == null) return "[❓]";
        if (status) {
            return "[\uD83D\uDFE2]";
        } else {
            return "[\uD83D\uDD34]";
        }
    }

    public void setStatus(Boolean status){
        this.status = status;
        setCaption();
    }

    @Override
    public void refresh() throws HomeApiWrongResponseData {
        SwitcherData cd = AppContextHolder.getApi().getStatusSwitchManager(patternCD);
        setStatus(cd.getDeviceValue(light));
    }
}
