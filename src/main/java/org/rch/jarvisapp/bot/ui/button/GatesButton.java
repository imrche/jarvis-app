package org.rch.jarvisapp.bot.ui.button;

import org.rch.jarvisapp.bot.dataobject.ActionData;
import org.rch.jarvisapp.smarthome.devices.Gate;

public class GatesButton extends Button{
    Gate gate;

    public GatesButton(String name, ActionData data) {
        super(name, data);
    }
}
