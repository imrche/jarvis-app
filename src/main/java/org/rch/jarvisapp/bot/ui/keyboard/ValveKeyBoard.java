package org.rch.jarvisapp.bot.ui.keyboard;

import org.rch.jarvisapp.bot.actions.valves.CloseValve;
import org.rch.jarvisapp.bot.actions.valves.OpenValve;
import org.rch.jarvisapp.bot.ui.button.Button;
import org.rch.jarvisapp.bot.ui.button.DeviceButton;
import org.rch.jarvisapp.smarthome.devices.Valve;

public class ValveKeyBoard extends KeyBoard{
    public ValveKeyBoard(Valve valve){
        addButton(1, new DeviceButton(valve).build(false));
        addButton(2, new Button("Открыть", new OpenValve(valve)));
        addButton(2, new Button("Закрыть", new CloseValve(valve)));
    }

    public ValveKeyBoard() {
    }

    @Override
    public void refresh() {

    }
}
