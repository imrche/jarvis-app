package org.rch.jarvisapp.bot.ui.keyboard;

import org.rch.jarvisapp.AppContextHolder;
import org.rch.jarvisapp.bot.dataobject.SwitcherData;
import org.rch.jarvisapp.bot.exceptions.HomeApiWrongResponseData;
import org.rch.jarvisapp.bot.ui.button.Button;
import org.rch.jarvisapp.bot.ui.button.SwitchManageButton;
import org.rch.jarvisapp.smarthome.devices.Device;
import org.rch.jarvisapp.smarthome.devices.Light;

public class SwitchManageKeyBoard extends KeyBoard {

    public SwitchManageKeyBoard() {
    }

    public void addDevice(Device device) {
        if (!(device instanceof Light))
            return;//пока другие классы не рассматриваем

        super.addButton(((Light)device).getRow(), new SwitchManageButton((Light)device));
    }

    @Override
    public void refresh() throws HomeApiWrongResponseData {
        SwitcherData patternSD = new SwitcherData();
        for (Button button : getButtonsList())
            if (button instanceof SwitchManageButton) {
                patternSD.mergeDTO(((SwitchManageButton) button).getPatternCD());
            }

        SwitcherData sd = AppContextHolder.getApi().getStatusSwitchManager(patternSD);

        for (Button button : getButtonsList())
            if (button instanceof SwitchManageButton) {
                SwitchManageButton btn = (SwitchManageButton) button;
                btn.setStatus(sd.getDeviceValue(btn.getLight()));
            }

    }
}