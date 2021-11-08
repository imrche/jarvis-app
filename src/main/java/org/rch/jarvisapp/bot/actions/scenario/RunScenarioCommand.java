package org.rch.jarvisapp.bot.actions.scenario;

import org.rch.jarvisapp.AppContextHolder;
import org.rch.jarvisapp.bot.actions.Action;
import org.rch.jarvisapp.bot.dataobject.ScenarioData;
import org.rch.jarvisapp.bot.exceptions.HomeApiWrongResponseData;
import org.rch.jarvisapp.bot.ui.Tile;
import org.rch.jarvisapp.smarthome.Scenario;
import org.rch.jarvisapp.smarthome.ScenarioBtn;
import org.rch.jarvisapp.smarthome.api.Api;

public class RunScenarioCommand implements Action {
    Api api = AppContextHolder.getApi();

    Scenario scenario;
    ScenarioBtn btn;

    public RunScenarioCommand(Scenario scenario, ScenarioBtn btn) {
        this.scenario = scenario;
        this.btn = btn;
    }

    @Override
    public void run(Tile tile) throws HomeApiWrongResponseData {
        ScenarioData data = new ScenarioData(scenario.getCode(), btn.getCode());
        api.runScenario(data);
        tile.refresh();//потому что кнопки не меняются
    }

    @Override
    public int hashCode() {
        return (scenario.hashCode() + btn.hashCode() + this.getClass().hashCode());
    }
}
