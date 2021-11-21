package org.rch.jarvisapp.bot.ui.button;

import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.FieldDefaults;
import org.rch.jarvisapp.bot.actions.scenario.ShowScenario;
import org.rch.jarvisapp.bot.actions.speaker.ShowSpeaker;
import org.rch.jarvisapp.bot.dataobject.ScenariosData;
import org.rch.jarvisapp.smarthome.Scenario;
import org.rch.jarvisapp.smarthome.devices.Speaker;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@EqualsAndHashCode(callSuper = true)
public class SpeakerButton extends Button{
    Speaker speaker;
    String status;

    final ScenariosData patternCD = new ScenariosData();

    public SpeakerButton(Speaker speaker){
        this.speaker = speaker;
        setCallBack(new ShowSpeaker(speaker));
    }

    public void setCaption() {
        super.setCaption(speaker.getPlacement().getName() /*+ " " + speaker.visualizeStatus(status,false)*/);
    }

    public void setStatus(String status){
        this.status = status;
        setCaption();
    }
}
