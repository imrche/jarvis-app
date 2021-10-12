package org.rch.jarvisapp.bot.ui.button;

import org.rch.jarvisapp.AppContextHolder;
import org.rch.jarvisapp.bot.dataobject.DeviceCommandData;
import org.rch.jarvisapp.bot.enums.CommonCallBack;
import org.rch.jarvisapp.bot.exceptions.DeviceStatusIsUnreachable;
import org.rch.jarvisapp.smarthome.devices.Valve;

public class ValveButton extends Button{
    private final Valve valve;
    private Integer status;
    private final DeviceCommandData requestStatusData = new DeviceCommandData();

    public ValveButton(Valve valve){
        super("", CommonCallBack.empty.name());
        this.valve = valve;
        requestStatusData.addDevice(valve.getId());
        refresh();
    }

    public void setCaption(){
        super.setCaption(valve.getName() + " " + visualizeStatus(status));
    }

    public void updateStatus(){
        try {
            DeviceCommandData response = AppContextHolder.getApi().getStatusValve(requestStatusData);
            status = response.getDeviceValue(valve.getId());
        } catch (DeviceStatusIsUnreachable deviceStatusIsUnreachable) {
            status = null;
        }
    }

    @Override
    public void refresh() {
        updateStatus();
        setCaption();
    }

    private String visualizeStatus(Integer status){
        if (status == null) return "[❓]";
        switch (status){
            case 1 : return "[\uD83D\uDCA7]";
            case 0 : return "[\uD83D\uDEAB]";
            default: return "[❓]";
        }
    }

    public Valve getValve() {
        return valve;
    }
}
