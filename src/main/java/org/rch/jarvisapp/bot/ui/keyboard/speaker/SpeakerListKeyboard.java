package org.rch.jarvisapp.bot.ui.keyboard.speaker;

import org.rch.jarvisapp.AppContextHolder;
import org.rch.jarvisapp.bot.exceptions.HomeApiWrongResponseData;
import org.rch.jarvisapp.bot.ui.button.Button;
import org.rch.jarvisapp.bot.ui.button.SpeakerButton;
import org.rch.jarvisapp.bot.ui.keyboard.KeyBoard;
import org.rch.jarvisapp.smarthome.SmartHome;
import org.rch.jarvisapp.smarthome.devices.Speaker;

public class SpeakerListKeyboard extends KeyBoard {
    SmartHome smartHome = AppContextHolder.getSH();

    public SpeakerListKeyboard() {
        int i = 1;
        for (Speaker speaker : smartHome.getDevicesByType(Speaker.class))
            addButton(i++, new SpeakerButton(speaker));
    }

    @Override
    public void refresh() throws HomeApiWrongResponseData {
        for (Button button : getButtonsList()){
            SpeakerButton btn = (SpeakerButton) button;
            btn.setStatus("");
        }
    }
}