package org.rch.jarvisapp.bot.ui.keyboard.speaker;

import org.rch.jarvisapp.AppContextHolder;
import org.rch.jarvisapp.bot.actions.speaker.ShowSpeakerPlayer;
import org.rch.jarvisapp.bot.actions.speaker.player.settings.ShowSpeakerSettings;
import org.rch.jarvisapp.bot.actions.speaker.ShowSpeakerTTS;
import org.rch.jarvisapp.bot.dataobject.SpeakerData;
import org.rch.jarvisapp.bot.dataobject.SpeakerStatusData;
import org.rch.jarvisapp.bot.enums.CommonCallBack;
import org.rch.jarvisapp.bot.exceptions.HomeApiWrongResponseData;
import org.rch.jarvisapp.bot.ui.DeviceContainer;
import org.rch.jarvisapp.bot.ui.button.Button;
import org.rch.jarvisapp.bot.ui.keyboard.KeyBoard;
import org.rch.jarvisapp.bot.ui.yandexStation.StationState;
import org.rch.jarvisapp.bot.ui.yandexStation.Track;
import org.rch.jarvisapp.bot.ui.yandexStation.TrackBuilder;
import org.rch.jarvisapp.smarthome.api.Api;
import org.rch.jarvisapp.smarthome.devices.Device;
import org.rch.jarvisapp.smarthome.devices.Speaker;

import java.util.ArrayList;
import java.util.List;

public class SpeakerKeyboard extends KeyBoard implements DeviceContainer {
    private final Api api = AppContextHolder.getApi();
    private final TrackBuilder trackBuilder = AppContextHolder.getContext().getBean(TrackBuilder.class);

    private final Speaker speaker;
    private SpeakerData speakerData = new SpeakerData();

    private Button info = new Button(" ", CommonCallBack.empty.name());

    private final String TTS_SYMBOL = "\uD83D\uDCAC";
    private final String PLAYER_SYMBOL = "\uD83C\uDFB6";
    private final String SETTINGS_SYMBOL = "⚙";


    public SpeakerKeyboard(Speaker speaker) {
        this.speaker = speaker;
        speakerData.addSpeaker(speaker);
        addButton(1, info);
        addButton(2, new Button(TTS_SYMBOL      + "[SAY]",      new ShowSpeakerTTS(speaker)));
        addButton(2, new Button(PLAYER_SYMBOL   + "[PLAYER]",   new ShowSpeakerPlayer(speaker)));
        addButton(2, new Button(SETTINGS_SYMBOL + "[SETTINGS]", new ShowSpeakerSettings(speaker)));
    }

    @Override
    public void refresh() throws HomeApiWrongResponseData {
        SpeakerStatusData ssd = api.getSpeakerStatus(speakerData);
        SpeakerStatusData.SpeakerElement se =  ssd.getDevice(speaker);

        String trackInfo = "";
        if (StationState.PLAYING.name().equals(se.state)){
            Track track = trackBuilder.build(se);
            trackInfo = track.getArtist() + " - " + track.getTitle();
        }

        info.setCaption(StationState.getStateSymbol(se.state) + trackInfo);
    }

    @Override
    public List<Device> getDeviceList() {
        List<Device> list = new ArrayList<>();
        list.add(speaker);

        return list;
    }
}