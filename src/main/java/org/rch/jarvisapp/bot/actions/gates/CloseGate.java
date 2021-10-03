package org.rch.jarvisapp.bot.actions.gates;

import org.rch.jarvisapp.smarthome.devices.Gate;

public class CloseGate extends MoveDoor{
    public CloseGate(Gate gate) {
        super(gate, Direction.close);
    }
}
