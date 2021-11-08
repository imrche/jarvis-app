package org.rch.jarvisapp.bot.actions.scenario;

import org.rch.jarvisapp.AppContextHolder;
import org.rch.jarvisapp.bot.actions.Action;
import org.rch.jarvisapp.bot.dataobject.ScenariosData;
import org.rch.jarvisapp.bot.enums.ParseMode;
import org.rch.jarvisapp.bot.exceptions.HomeApiWrongResponseData;
import org.rch.jarvisapp.bot.ui.Tile;
import org.rch.jarvisapp.bot.ui.button.func_interface.TileCaptionUpdater;
import org.rch.jarvisapp.bot.ui.keyboard.KeyBoard;
import org.rch.jarvisapp.bot.ui.keyboard.ScenarioKeyBoard;
import org.rch.jarvisapp.smarthome.Scenario;
import org.rch.jarvisapp.smarthome.SmartHome;

public class ShowScenario implements Action {
    public final static String description = "Сценарии";
    SmartHome smartHome = AppContextHolder.getSH();

    Scenario scenario;

    TileCaptionUpdater tileCaptionUpdater = () -> scenario.getName() + getStatus();

    public ShowScenario(Scenario scenario) {
        this.scenario = scenario;
        sd.addScenario(scenario);
    }

    ScenariosData sd = new ScenariosData();

    private String getStatus(){
        try {
            ScenariosData resp = smartHome.getApi().getScenariosStatus(sd);
            return scenario.visualizeStatus(resp.getStatus(scenario),true);
        } catch (HomeApiWrongResponseData e) {
            return " ❓";
        }
    }

    @Override
    public void run(Tile tile) throws HomeApiWrongResponseData {
        KeyBoard kb = new ScenarioKeyBoard(scenario);

        tile.update()
                .setTileCaptionUpdater(tileCaptionUpdater)
                .setParseMode(ParseMode.Markdown)
                .setKeyboard(kb);
    }
    @Override
    public int hashCode() {
        return (scenario.hashCode() + this.getClass().hashCode());
    }
}
