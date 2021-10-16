package org.rch.jarvisapp.bot.ui.keyboard;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.SneakyThrows;
import org.rch.jarvisapp.bot.exceptions.HomeApiWrongResponseData;
import org.rch.jarvisapp.bot.ui.DeviceContainer;
import org.rch.jarvisapp.smarthome.devices.Device;
import org.rch.jarvisapp.smarthome.devices.Gate;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;

public class GroupGateKeyBoard extends KeyBoard implements DeviceContainer {
    private final List<GateKeyBoard> listGateKB = new ArrayList<>();

    public GroupGateKeyBoard(){
        super();
    }

    @Override
    public List<Device> getDeviceList(){
        List<Device> list = new ArrayList<>();

        for (GateKeyBoard gateKB : listGateKB)
            list.add(gateKB.getGate());

        return list;
    }

    public void addGate(Gate gate) throws HomeApiWrongResponseData {
        listGateKB.add(new GateKeyBoard(gate));
    }

    @Override
    public void refresh() throws HomeApiWrongResponseData {
        for (GateKeyBoard gateKB : listGateKB)
            gateKB.refresh();
    }

    @SneakyThrows
    @Override
    public List<List<InlineKeyboardButton>> getKeyboard() {
        refresh();//todo возможно это плохое место
        List<List<InlineKeyboardButton>> kb = super.getKeyboard();
        for (GateKeyBoard gateKB : listGateKB)
            kb.addAll(gateKB.getKeyboard());

        setKeyboard(kb);
        return kb;
    }
}
