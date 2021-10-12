package org.rch.jarvisapp.bot.actions.valves;

import org.rch.jarvisapp.smarthome.devices.Valve;

public class OpenValve extends ChangeStateValve {
    public OpenValve(Valve valve) {
        super(valve,OPEN_VALVE);
    }
}
