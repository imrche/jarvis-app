package org.rch.jarvisapp.bot.actions.scenario;

import org.rch.jarvisapp.bot.actions.Action;
import org.rch.jarvisapp.bot.exceptions.HomeApiWrongResponseData;
import org.rch.jarvisapp.bot.ui.Tile;
import org.rch.jarvisapp.bot.ui.button.Button;
import org.rch.jarvisapp.bot.ui.keyboard.KeyBoard;
import org.rch.jarvisapp.smarthome.Scenario;
import org.rch.jarvisapp.smarthome.ScenarioBtn;

public class ShowScenario implements Action {
    public final static String description = "Сценарии";

    Scenario scenario;

    public ShowScenario(Scenario scenario) {
        this.scenario = scenario;
    }

    @Override
    public void run(Tile tile) throws HomeApiWrongResponseData {
        KeyBoard kb = new KeyBoard();

        int i = 0;

        for (ScenarioBtn btn : scenario.getButtons())
            kb.addButton(i++, new Button(btn.getName(), new RunScenarioCommand(scenario,btn)));

        tile.update()
                .setCaption(scenario.getName())
                .setKeyboard(kb);
    }
    @Override
    public int hashCode() {
        return (scenario.hashCode() + this.getClass().hashCode());
    }
}
