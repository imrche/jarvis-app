package org.rch.jarvisapp.bot.actions.gates;

import org.rch.jarvisapp.smarthome.devices.Gate;

public class OpenGate extends MoveDoor{
    public OpenGate(Gate gate) {
        super(gate, MoveDoor.Direction.open);
    }
}
