package org.rch.jarvisapp.bot.ui.keyboard.speaker;

import org.rch.jarvisapp.AppContextHolder;
import org.rch.jarvisapp.bot.actions.TextInputSupportable;
import org.rch.jarvisapp.bot.actions.speaker.ShowMessage;
import org.rch.jarvisapp.bot.actions.speaker.Volumable;
import org.rch.jarvisapp.bot.actions.speaker.player.SetProgress;
import org.rch.jarvisapp.bot.actions.speaker.player.SetVolume;
import org.rch.jarvisapp.bot.actions.speaker.player.SimplePlayerCommand;
import org.rch.jarvisapp.bot.dataobject.SpeakerData;
import org.rch.jarvisapp.bot.dataobject.SpeakerSettings;
import org.rch.jarvisapp.bot.dataobject.SpeakerStatusData;
import org.rch.jarvisapp.bot.exceptions.HomeApiWrongResponseData;
import org.rch.jarvisapp.bot.ui.DeviceContainer;
import org.rch.jarvisapp.bot.ui.Tile;
import org.rch.jarvisapp.bot.ui.button.Button;
import org.rch.jarvisapp.bot.ui.keyboard.KeyBoard;
import org.rch.jarvisapp.bot.ui.yandexStation.*;
import org.rch.jarvisapp.smarthome.devices.Device;
import org.rch.jarvisapp.smarthome.devices.Speaker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.rch.jarvisapp.bot.dataobject.SpeakerSettings.Settings.*;

import java.util.*;

public class PlayerKeyboard extends KeyBoard implements DeviceContainer, TextInputSupportable, Volumable {
    private final Logger logger = LoggerFactory.getLogger(PlayerKeyboard.class);

    private Tile tile;
    private final SpeakerData speakerData = new SpeakerData();
    private final Speaker speaker;
    private final Button info = new Button(" ", new ShowMessage(s -> getInfo()));

    private final VolumeLine volumeLineNew;
    private final ProgressLine progressLineNew;

    private final TrackProgressSeeker seeker = new TrackProgressSeeker(this);
    private Track curTrack;
    private final String defaultCover = "https://avatars.mds.yandex.net/i?id=7b449cdc58fa7f0586a744fc9ddf9173-5190189-images-thumbs&n=13";
    private final Integer curCoverSize;

    public PlayerKeyboard(Speaker speaker) {
        this.speaker = speaker;
        speakerData.addSpeaker(speaker);

        volumeLineNew = new VolumeLine(i -> new SetVolume(speaker, i));
        progressLineNew = new ProgressLine(i -> new SetProgress(speaker, i));

        int i = 1;
        addButton(i, info);

        i++;
        for (Button btn : progressLineNew.getProgressButton())
            addButton(i, btn);

        i++;
        addButton(i, new Button(PlayerSymbols.PREV_TRACK.toString(),  new SimplePlayerCommand(speaker, SpeakerData.Command.prev)));
        addButton(i, new Button(PlayerSymbols.BACKWARD.toString(),    new SimplePlayerCommand(speaker, SpeakerData.Command.backward)));
        addButton(i, new Button(PlayerSymbols.STOP.toString(),        new SimplePlayerCommand(speaker, SpeakerData.Command.stop)));
        addButton(i, new Button(PlayerSymbols.PLAY.toString(),        new SimplePlayerCommand(speaker, SpeakerData.Command.play)));
        addButton(i, new Button(PlayerSymbols.FORWARD.toString(),     new SimplePlayerCommand(speaker, SpeakerData.Command.forward)));
        addButton(i, new Button(PlayerSymbols.NEXT_TRACK.toString(),  new SimplePlayerCommand(speaker, SpeakerData.Command.next)));

        i++;
        for (Button btn : volumeLineNew.getVolumeButton())
            addButton(i, btn);

        i++;
        addButton(i, new Button(PlayerSymbols.LIKE.toString(),    new SimplePlayerCommand(speaker, SpeakerData.Command.like)));
        addButton(i, new Button(PlayerSymbols.DISLIKE.toString(), new SimplePlayerCommand(speaker, SpeakerData.Command.dislike)));

        curCoverSize = getCoverSize();
    }

    public Tile getTile() {
        return tile;
    }

    public void setTile(Tile tile) {
        this.tile = tile;
    }

    public void deletePlayer(){
        seeker.interrupt();
    }

    public Track getCurTrack() {
        return curTrack;
    }

    public String getCoverURL(){
        return curTrack != null ? curTrack.getCoverURL(curCoverSize) : defaultCover;
    }

    private Integer getCoverSize(){
        SpeakerSettings patternSettings = new SpeakerSettings();
        patternSettings.addSpeaker(speaker);

        try {
            SpeakerSettings ss;
            ss = AppContextHolder.getApi().getSpeakerSettings(patternSettings);
            Object coverSizeResponse = ss.getSettings(speaker).get(coverSize);
            if (!(coverSizeResponse instanceof Number))
                throw new HomeApiWrongResponseData("Не получено/некорректное значение параметра " + coverSize.name());

            return (Integer)coverSizeResponse;
        } catch (HomeApiWrongResponseData e) {
            logger.error("Не удалось получить дефолтное значение размера обложки",e);
            return 300;
        }
    }

    private String getInfo(){
        return  "[INFO]" + "\n" +
                "\uD83D\uDC64   " + curTrack.getArtist() + "\n\n" +
                "\uD83C\uDFB9   " + curTrack.getTitle()  + " (" + curTrack.getDurationFormatted() + ")" + "\n\n" +
                "\uD83D\uDCBF   " + curTrack.getAlbum() + " [" + curTrack.getYear() + "]" + "\n\n" +
                "\uD83C\uDFB6   " + curTrack.getGenre() + "\n\n" +
                "❤  "            + curTrack.getAlbumLikesCount();
    }

    @Override
    public void refresh() throws HomeApiWrongResponseData {
        SpeakerStatusData ssd = AppContextHolder.getApi().getSpeakerStatus(speakerData);
        SpeakerStatusData.SpeakerElement se =  ssd.getDevice(speaker);

        if (se.trackID != null && !se.trackID.isEmpty()) {
            curTrack = AppContextHolder.getTrackBuilder().build(se);

            info.setCaption(curTrack.getDescription());

            progressLineNew.setProgressWithLevel(se.trackProgress, curTrack != null ? curTrack.getDuration() : 0);
            volumeLineNew.setVolumeWithLevel(se.volume);

            seeker.setTrackLength(se.trackDuration);
            if (StationState.PLAYING.name().equals(se.state))
                seeker.startTrack(se.trackProgress);
            else
                seeker.stopTrack();
        }
    }

    @Override
    public void setVolume(Integer volume){
        volumeLineNew.setVolume(volume);
    }

    public void setProgress(Integer progress){
        progressLineNew.setProgress(progress);
    }

    @Override
    public void ProceedTextInput(String text) {
        System.out.println(text);
    }

    @Override
    public List<Device> getDeviceList() {
        List<Device> list = new ArrayList<>();
        list.add(speaker);

        return list;
    }
}