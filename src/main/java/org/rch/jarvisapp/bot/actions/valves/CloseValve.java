package org.rch.jarvisapp.bot.actions.valves;

import org.rch.jarvisapp.smarthome.devices.Valve;

public class CloseValve extends ChangeStateValve {
    public CloseValve(Valve valve) {
        super(valve,0);
    }
}
