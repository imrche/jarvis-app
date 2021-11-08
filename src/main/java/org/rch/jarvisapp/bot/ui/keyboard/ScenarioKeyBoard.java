package org.rch.jarvisapp.bot.ui.keyboard;

import org.rch.jarvisapp.bot.actions.scenario.RunScenarioCommand;
import org.rch.jarvisapp.bot.ui.ScenarioContainer;
import org.rch.jarvisapp.bot.ui.button.Button;
import org.rch.jarvisapp.smarthome.Scenario;
import org.rch.jarvisapp.smarthome.ScenarioBtn;

import java.util.ArrayList;
import java.util.List;

public class ScenarioKeyBoard extends KeyBoard implements ScenarioContainer {
    Scenario scenario;

    public ScenarioKeyBoard(Scenario scenario) {
        this.scenario = scenario;
        int i = 0;

        for (ScenarioBtn btn : scenario.getButtons())
            addButton(i++, new Button(btn.getName(), new RunScenarioCommand(scenario,btn)));
    }

    @Override
    public List<Scenario> getScenarioList() {
        List<Scenario> result = new ArrayList<>();
        result.add(scenario);
        return result;
    }
}