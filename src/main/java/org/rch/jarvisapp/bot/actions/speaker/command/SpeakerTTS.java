package org.rch.jarvisapp.bot.actions.speaker.command;

import org.rch.jarvisapp.AppContextHolder;
import org.rch.jarvisapp.bot.actions.Action;
import org.rch.jarvisapp.bot.dataobject.SpeakerData;
import org.rch.jarvisapp.bot.exceptions.HomeApiWrongResponseData;
import org.rch.jarvisapp.bot.ui.Tile;
import org.rch.jarvisapp.smarthome.api.Api;
import org.rch.jarvisapp.smarthome.devices.Speaker;

public class SpeakerTTS implements Action {
    Api api = AppContextHolder.getApi();

    private final String typeCommand = "tts";

    Speaker speaker;
    SpeakerData data = new SpeakerData();

    public SpeakerTTS(Speaker speaker) {
        this.speaker = speaker;
        data.addSpeaker(speaker,typeCommand);
    }

    public void run(String text){
        data.addCommand(speaker, SpeakerData.Command.setText, text);
        api.sendSpeakerCommand(data);
    }

    public void setVolume(String volume){
        data.addCommand(speaker, SpeakerData.Command.setVolume, volume);
    }

    @Override
    public void run(Tile tile) throws HomeApiWrongResponseData {}

    @Override
    public int hashCode() {
        return (speaker.hashCode() + this.getClass().hashCode());
    }
}
