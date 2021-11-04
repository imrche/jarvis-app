package org.rch.jarvisapp.bot.actions.scenario;

import org.rch.jarvisapp.AppContextHolder;
import org.rch.jarvisapp.bot.actions.Action;
import org.rch.jarvisapp.bot.exceptions.HomeApiWrongResponseData;
import org.rch.jarvisapp.bot.ui.Tile;
import org.rch.jarvisapp.bot.ui.button.Button;
import org.rch.jarvisapp.bot.ui.keyboard.KeyBoard;
import org.rch.jarvisapp.smarthome.Scenario;
import org.rch.jarvisapp.smarthome.SmartHome;

public class ShowAllScenarios implements Action {
    public final static String description = "Сценарии";

    SmartHome smartHome = AppContextHolder.getSH();

    public ShowAllScenarios() {}

    @Override
    public void run(Tile tile) throws HomeApiWrongResponseData {
        KeyBoard kb = new KeyBoard();

        int i = 0;

        for (Scenario scenario : smartHome.getScenarios())
            kb.addButton(i++,new Button(scenario.getName(), new ShowScenario(scenario)));

        tile.update()
                .setCaption(description)
                .setKeyboard(kb);
    }
    @Override
    public int hashCode() {
        return ("noPlace".hashCode() + this.getClass().hashCode());
    }
}
