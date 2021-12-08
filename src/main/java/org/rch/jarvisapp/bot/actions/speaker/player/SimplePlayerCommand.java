package org.rch.jarvisapp.bot.actions.speaker.player;

import org.rch.jarvisapp.AppContextHolder;
import org.rch.jarvisapp.bot.actions.Action;
import org.rch.jarvisapp.bot.dataobject.SpeakerData;
import org.rch.jarvisapp.bot.exceptions.HomeApiWrongResponseData;
import org.rch.jarvisapp.bot.ui.Tile;
import org.rch.jarvisapp.smarthome.api.Api;
import org.rch.jarvisapp.smarthome.devices.Speaker;

public class SimplePlayerCommand implements Action {
    protected final Api api = AppContextHolder.getApi();
    protected final Speaker speaker;
    protected final SpeakerData.Command command;
    protected SpeakerData data = new SpeakerData();

    private final String typeCommand = "playerControl";

    public SimplePlayerCommand(Speaker speaker, SpeakerData.Command command) {
        this.speaker = speaker;
        this.command = command;
        data.addSpeaker(speaker,typeCommand);
        data.addCommand(speaker, command);
    }

    @Override
    public void run(Tile tile) throws HomeApiWrongResponseData {
        api.sendSpeakerCommand(data);
    }

    @Override
    public int hashCode() {
        return (speaker.hashCode() + command.hashCode() + this.getClass().hashCode());
    }
}
