package org.rch.jarvisapp.bot.ui.keyboard;

import org.rch.jarvisapp.AppContextHolder;
import org.rch.jarvisapp.bot.dataobject.ScenariosData;
import org.rch.jarvisapp.bot.exceptions.HomeApiWrongResponseData;
import org.rch.jarvisapp.bot.ui.ScenarioContainer;
import org.rch.jarvisapp.bot.ui.button.Button;
import org.rch.jarvisapp.bot.ui.button.ScenarioButton;
import org.rch.jarvisapp.smarthome.Scenario;
import org.rch.jarvisapp.smarthome.SmartHome;

import java.util.List;

public class ScenarioListKeyBoard extends KeyBoard implements ScenarioContainer {
    SmartHome smartHome = AppContextHolder.getSH();
    ScenariosData patternSD = new ScenariosData();

    public ScenarioListKeyBoard() {
        patternSD.addScenario(smartHome.getScenarios());

        int i = 1;
        for (Scenario scenario : smartHome.getScenarios())
            addButton(i++, new ScenarioButton(scenario));
    }

    @Override
    public void refresh() throws HomeApiWrongResponseData {
        ScenariosData sd = smartHome.getApi().getScenariosStatus(patternSD);

        for (Button button : getButtonsList())
            if (button instanceof ScenarioButton) {
                ScenarioButton btn = (ScenarioButton) button;
                btn.setStatus(sd.getStatus(btn.getScenario()));
            }
    }

    @Override
    public List<Scenario> getScenarioList() {
        return smartHome.getScenarios();
    }
}