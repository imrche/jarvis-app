package org.rch.jarvisapp.bot.actions.gates;

import org.rch.jarvisapp.bot.dataobject.GateData;
import org.rch.jarvisapp.smarthome.devices.Gate;

public class StopGate extends MoveDoor{
    public StopGate(Gate gate) {
        super(gate, GateData.ActionValue.stop);
    }
}
