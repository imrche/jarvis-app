package org.rch.jarvisapp.bot.ui.button;

import org.rch.jarvisapp.AppContextHolder;
import org.rch.jarvisapp.bot.dataobject.GateData;
import org.rch.jarvisapp.bot.enums.CommonCallBack;
import org.rch.jarvisapp.bot.exceptions.HomeApiWrongResponseData;
import org.rch.jarvisapp.smarthome.devices.Gate;
import org.rch.jarvisapp.smarthome.devices.status.GateStatus;

public class GateButton extends Button{
    private final Gate gate;
    private final GateData requestStatusData = new GateData();
    private GateStatus status;

    public GateButton(Gate gate){
        super("", CommonCallBack.empty.name());
        this.gate = gate;
        requestStatusData.addGate(gate);
    }

    public void setCaption(){
        super.setCaption(gate.getName() + " " + visualizeStatus(status));
    }

    public GateStatus getStatus() {
        return status;
    }

    @Override
    public void refresh() throws HomeApiWrongResponseData {//todo часто шлются на инициализации
        updateStatus();
        setCaption();
    }

    private void updateStatus() throws HomeApiWrongResponseData {
        GateData response = AppContextHolder.getApi().getStatusGates(requestStatusData);
        status = response.getGateStatus(gate);
    }

    private String visualizeStatus(GateStatus status){
        if (status == null) return "[❓]";
        switch (status){
            case open : return "[\uD83D\uDD13]";
            case close:return "[\uD83D\uDD12]";
            case intermediate: return "[⭕]";
            default: return "[❓]";
        }
    }
}
