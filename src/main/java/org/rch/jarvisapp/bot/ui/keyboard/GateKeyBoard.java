package org.rch.jarvisapp.bot.ui.keyboard;

import org.json.JSONObject;
import org.rch.jarvisapp.bot.dataobject.ActionData;
import org.rch.jarvisapp.bot.enums.ActionType;
import org.rch.jarvisapp.bot.ui.button.Button;
import org.rch.jarvisapp.smarthome.devices.Gate;

public class GateKeyBoard extends KeyBoard{
    public GateKeyBoard(Gate gate){
        JSONObject openBody = new JSONObject()
                .put("gate", gate.getCode())
                .put("action", "open");
        JSONObject closeBody = new JSONObject()
                .put("gate", gate.getCode())
                .put("action", "close");

        addButton(1, new Button(gate.getName(), new ActionData(ActionType.gates, "")));
        addButton(2, new Button("Открыть", new ActionData(ActionType.gates, openBody)));
        addButton(2, new Button("Закрыть", new ActionData(ActionType.gates, closeBody)));
    }

    @Override
    public void refresh() {

    }
}
