package org.rch.jarvisapp.bot.ui.button;

import org.rch.jarvisapp.AppContextHolder;
import org.rch.jarvisapp.bot.dataobject.DeviceCommandData;
import org.rch.jarvisapp.bot.dataobject.SwitcherData;
import org.rch.jarvisapp.bot.enums.CommonCallBack;
import org.rch.jarvisapp.bot.exceptions.DeviceStatusIsUnreachable;
import org.rch.jarvisapp.bot.exceptions.HomeApiWrongResponseData;
import org.rch.jarvisapp.smarthome.devices.Valve;

public class ValveButton extends Button{
    private final Valve valve;
    private Boolean status;
    private final SwitcherData requestStatusData = new SwitcherData();

    public ValveButton(Valve valve){
        super("", CommonCallBack.empty.name());
        this.valve = valve;
        requestStatusData.addSwitcher(valve);
        //refresh();
    }

    public void setCaption(){
        super.setCaption(valve.getName() + " " + visualizeStatus(status));
    }

    public void updateStatus() throws HomeApiWrongResponseData {
        SwitcherData response = AppContextHolder.getApi().getStatusValve(requestStatusData);
        status = response.getDeviceValue(valve);
    }

    @Override
    public void refresh() throws HomeApiWrongResponseData {
        updateStatus();
        setCaption();
    }

    private String visualizeStatus(Boolean status){
        if (status == null) return "[❓]";
        if (status) {
            return "[\uD83D\uDCA7]";
        } else {
            return "[\uD83D\uDEAB]";
        }
    }

    public Valve getValve() {
        return valve;
    }
}
