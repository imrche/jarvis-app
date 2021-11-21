package org.rch.jarvisapp.bot.ui.keyboard.speaker;

import org.rch.jarvisapp.AppContextHolder;
import org.rch.jarvisapp.bot.exceptions.HomeApiWrongResponseData;
import org.rch.jarvisapp.bot.ui.button.Button;
import org.rch.jarvisapp.bot.ui.button.SpeakerButton;
import org.rch.jarvisapp.bot.ui.keyboard.KeyBoard;
import org.rch.jarvisapp.smarthome.SmartHome;
import org.rch.jarvisapp.smarthome.devices.Speaker;

import java.util.List;

public class SpeakerListKeyboard extends KeyBoard {
    SmartHome smartHome = AppContextHolder.getSH();
    //ScenariosData patternSD = new ScenariosData();

    public SpeakerListKeyboard() {
        //patternSD.addScenario(smartHome.getScenarios());

        int i = 1;
        for (Speaker speaker : smartHome.getDevicesByType(Speaker.class))
            addButton(i++, new SpeakerButton(speaker));
    }

    @Override
    public void refresh() throws HomeApiWrongResponseData {
/*        ScenariosData sd = smartHome.getApi().getScenariosStatus(patternSD);

        for (Button button : getButtonsList())
            if (button instanceof ScenarioButton) {
                ScenarioButton btn = (ScenarioButton) button;
                btn.setStatus(sd.getStatus(btn.getScenario()));
            }*/
        for (Button button : getButtonsList()){
            SpeakerButton btn = (SpeakerButton) button;
            btn.setStatus("");
        }
    }
}