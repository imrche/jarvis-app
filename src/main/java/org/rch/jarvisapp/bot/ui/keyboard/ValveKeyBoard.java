package org.rch.jarvisapp.bot.ui.keyboard;

import org.rch.jarvisapp.bot.actions.valves.CloseValve;
import org.rch.jarvisapp.bot.actions.valves.OpenValve;
import org.rch.jarvisapp.bot.exceptions.HomeApiWrongResponseData;
import org.rch.jarvisapp.bot.ui.DeviceContainer;
import org.rch.jarvisapp.bot.ui.button.Button;
import org.rch.jarvisapp.bot.ui.button.ValveButton;
import org.rch.jarvisapp.smarthome.devices.Device;
import org.rch.jarvisapp.smarthome.devices.Valve;

import java.util.ArrayList;
import java.util.List;

public class ValveKeyBoard extends KeyBoard implements DeviceContainer {
    private ValveButton valveButton;
    //private Integer status;

    public ValveKeyBoard(Valve valve){
        valveButton = new ValveButton(valve);

        addButton(1, valveButton);
        addButton(2, new Button("Открыть", new OpenValve(valve)));
        addButton(2, new Button("Закрыть", new CloseValve(valve)));
    }

    public ValveKeyBoard() {}


    @Override
    public void refresh() throws HomeApiWrongResponseData {
        valveButton.refresh();

    }

    @Override
    public List<Device> getDeviceList() {
        List<Device> list = new ArrayList<>();
        list.add(valveButton.getValve());

        return list;
    }
}
