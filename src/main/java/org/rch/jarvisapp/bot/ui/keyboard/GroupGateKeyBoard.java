package org.rch.jarvisapp.bot.ui.keyboard;

import org.rch.jarvisapp.bot.actions.gates.ClickGate;
import org.rch.jarvisapp.bot.actions.gates.CloseGate;
import org.rch.jarvisapp.bot.actions.gates.OpenGate;
import org.rch.jarvisapp.bot.dataobject.GateData;
import org.rch.jarvisapp.bot.enums.CommonCallBack;
import org.rch.jarvisapp.bot.ui.button.Button;
import org.rch.jarvisapp.smarthome.devices.Gate;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;

public class GroupGateKeyBoard extends KeyBoard{
    private final List<GateKeyBoard> listGateKB = new ArrayList<>();

    public GroupGateKeyBoard(){
        super();
    }


    public void addGate(Gate gate) {
        listGateKB.add(new GateKeyBoard(gate));
    }


    @Override
    public void refresh() {
        for (GateKeyBoard gateKB : listGateKB)
            gateKB.refresh();

    }
    @Override
    public List<List<InlineKeyboardButton>> getKeyboard() {
        refresh();
        List<List<InlineKeyboardButton>> kb = super.getKeyboard();
        for (GateKeyBoard gateKB : listGateKB)
            kb.addAll(gateKB.getKeyboard());

        setKeyboard(kb);
        return kb;
    }

}
