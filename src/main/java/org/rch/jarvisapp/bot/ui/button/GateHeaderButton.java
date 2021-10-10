package org.rch.jarvisapp.bot.ui.button;

import org.rch.jarvisapp.bot.dataobject.GateData;
import org.rch.jarvisapp.bot.enums.CommonCallBack;
import org.rch.jarvisapp.smarthome.devices.Gate;

public class GateHeaderButton extends Button{
    Gate gate;
    public GateHeaderButton(Gate gate){
        super(gate.getName(), CommonCallBack.empty.name());
        this.gate = gate;
    }

    public void setCaption(GateData.StatusValue status){
        setText(gate.getName() + " " + visualizeStatus(status));
    }

    private String visualizeStatus(GateData.StatusValue status){
        if (status == null) return "[❓]";
        switch (status){
            case open : return "[\uD83D\uDD13]";
            case close:return "[\uD83D\uDD12]";
            case intermediate: return "[⭕]";
            default: return "[❓]";
        }
    }
}
