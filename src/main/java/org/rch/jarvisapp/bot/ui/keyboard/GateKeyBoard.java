package org.rch.jarvisapp.bot.ui.keyboard;

import org.rch.jarvisapp.bot.actions.gates.CloseGate;
import org.rch.jarvisapp.bot.actions.gates.OpenGate;
import org.rch.jarvisapp.bot.enums.CommonCallBack;
import org.rch.jarvisapp.bot.ui.button.Button;
import org.rch.jarvisapp.smarthome.devices.Gate;

public class GateKeyBoard extends KeyBoard{
    public GateKeyBoard(Gate gate){
        addButton(1, new Button(gate.getName(), CommonCallBack.empty.name()));
        addButton(2, new Button("Открыть", new OpenGate(gate)));
        addButton(2, new Button("Закрыть", new CloseGate(gate)));
    }

    @Override
    public void refresh() {

    }
}
