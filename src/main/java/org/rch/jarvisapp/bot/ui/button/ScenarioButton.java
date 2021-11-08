package org.rch.jarvisapp.bot.ui.button;

import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.FieldDefaults;
import org.rch.jarvisapp.bot.actions.scenario.ShowScenario;
import org.rch.jarvisapp.bot.dataobject.ScenariosData;
import org.rch.jarvisapp.smarthome.Scenario;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@EqualsAndHashCode(callSuper = true)
public class ScenarioButton extends Button{
    Scenario scenario;
    String status;

    final ScenariosData patternCD = new ScenariosData();

    public ScenarioButton(Scenario scenario){
        this.scenario = scenario;
        setCallBack(new ShowScenario(scenario));
    }

    public void setCaption() {
        super.setCaption(scenario.getName() + " " + scenario.visualizeStatus(status,false));
    }

    public void setStatus(String status){
        this.status = status;
        setCaption();
    }
}
