package org.rch.jarvisapp.bot.ui.keyboard.speaker;

import org.rch.jarvisapp.AppContextHolder;
import org.rch.jarvisapp.bot.actions.TextInputSupportable;
import org.rch.jarvisapp.bot.actions.speaker.Volumable;
import org.rch.jarvisapp.bot.actions.speaker.command.SetVolumeTTS;
import org.rch.jarvisapp.bot.actions.speaker.player.SetProgress;
import org.rch.jarvisapp.bot.actions.speaker.player.SimplePlayerCommand;
import org.rch.jarvisapp.bot.dataobject.SpeakerData;
import org.rch.jarvisapp.bot.dataobject.SpeakerStatusData;
import org.rch.jarvisapp.bot.enums.CommonCallBack;
import org.rch.jarvisapp.bot.exceptions.HomeApiWrongResponseData;
import org.rch.jarvisapp.bot.ui.DeviceContainer;
import org.rch.jarvisapp.bot.ui.button.Button;
import org.rch.jarvisapp.bot.ui.button.LightButton;
import org.rch.jarvisapp.bot.ui.keyboard.KeyBoard;
import org.rch.jarvisapp.bot.ui.yandexStation.CachedTracks;
import org.rch.jarvisapp.bot.ui.yandexStation.Track;
import org.rch.jarvisapp.bot.ui.yandexStation.TrackBuilder;
import org.rch.jarvisapp.smarthome.api.Api;
import org.rch.jarvisapp.smarthome.devices.Device;
import org.rch.jarvisapp.smarthome.devices.Speaker;

import java.util.*;


public class PlayerKeyboard extends KeyBoard implements DeviceContainer, TextInputSupportable, Volumable {
    Api api = AppContextHolder.getApi();
    TrackBuilder trackBuilder = AppContextHolder.getContext().getBean(TrackBuilder.class);
    SpeakerData speakerData = new SpeakerData();
    private Speaker speaker;

    private Button info = new Button(" ", CommonCallBack.empty.name());

    private List<Button> progressBar = new ArrayList<>();
    private List<Button> volume = new ArrayList<>();

    final char[] progressBarLine = "PROGRESS".toCharArray();
    final char[] volumeLine = "-VOLUME+".toCharArray();

    Map<Integer,Integer> volumeLineScale = new TreeMap<>();
    Map<Integer,Integer> progressBarScale = new TreeMap<>();

    Integer curVolumeLevel;
    Integer curSeekLevel;
    Track curTrack;

    private final String LIKE_SYMBOL        = "\uD83D\uDC4D";
    private final String DISLIKE_SYMBOL     = "\uD83D\uDC4E";

    private final String PREV_TRACK_SYMBOL  = "⏮";
    private final String BACKWARD_SYMBOL    = "⏪";
    private final String STOP_SYMBOL        = "⏹";
    private final String PLAY_SYMBOL        = "⏯";
    private final String FORWARD_SYMBOL     = "⏩";
    private final String NEXT_TRACK_SYMBOL  = "⏭";

    private final String HANDLE_SYMBOL      = "\uD83D\uDD38";

    private final String defaultCover = "https://avatars.mds.yandex.net/i?id=7b449cdc58fa7f0586a744fc9ddf9173-5190189-images-thumbs&n=13";
    private String coverSize = "300";

    {
        progressBarScale.put(0,0);
        progressBarScale.put(1,13);
        progressBarScale.put(2,26);
        progressBarScale.put(3,39);
        progressBarScale.put(4,52);
        progressBarScale.put(5,65);
        progressBarScale.put(6,78);
        progressBarScale.put(7,91);

        volumeLineScale.put(0,5);
        volumeLineScale.put(1,10);
        volumeLineScale.put(2,25);
        volumeLineScale.put(3,40);
        volumeLineScale.put(4,55);
        volumeLineScale.put(5,70);
        volumeLineScale.put(6,85);
        volumeLineScale.put(7,100);
    }

    public PlayerKeyboard(Speaker speaker) {
        this.speaker = speaker;
        speakerData.addSpeaker(speaker);

        for (int j = 0; j < progressBarLine.length; j++)
            progressBar.add(new Button(String.valueOf(progressBarLine[j]), new SetProgress(speaker, j)));

        for (int j = 0; j < volumeLine.length; j++)
            volume.add(new Button(String.valueOf(volumeLine[j]), new SetVolumeTTS(j)));

        int i = 1;
        addButton(i, info);

        i++;
        for (Button btn : progressBar)
            addButton(i, btn);

        i++;
        addButton(i, new Button(PREV_TRACK_SYMBOL,  new SimplePlayerCommand(speaker, SpeakerData.Command.prev)));
        addButton(i, new Button(BACKWARD_SYMBOL,    new SimplePlayerCommand(speaker, SpeakerData.Command.backward)));
        addButton(i, new Button(STOP_SYMBOL,        new SimplePlayerCommand(speaker, SpeakerData.Command.stop)));
        addButton(i, new Button(PLAY_SYMBOL,        new SimplePlayerCommand(speaker, SpeakerData.Command.play)));
        addButton(i, new Button(FORWARD_SYMBOL,     new SimplePlayerCommand(speaker, SpeakerData.Command.forward)));
        addButton(i, new Button(NEXT_TRACK_SYMBOL,  new SimplePlayerCommand(speaker, SpeakerData.Command.next)));

        i++;
        for (Button btn : volume)
            addButton(i, btn);

        i++;
        addButton(i, new Button(LIKE_SYMBOL,    CommonCallBack.empty.name()));
        addButton(i, new Button(DISLIKE_SYMBOL, CommonCallBack.empty.name()));
    }



    public Integer getPercentVolumeLevel(){
        //todo def value 20
        return volumeLineScale.get(curVolumeLevel);
    }

    public Integer getPercentProgressLevel(Integer level){
        return progressBarScale.get(level);
    }

    public Track getCurTrack() {
        return curTrack;
    }

    private void setProgressBarPosition(Integer progress){
        Integer result = -1;
        if (curTrack != null) {
            Integer percent = Math.round(progress.floatValue() / curTrack.getDuration() * 100);
            for (Map.Entry<Integer,Integer> entry : progressBarScale.entrySet()){
                result = entry.getKey();
                if (entry.getValue() >= percent)
                    break;
            }
        }
        setProgress(result);
    }

    private void setVolumeLinePosition(Integer volume){
        Integer result = -1;

        for (Map.Entry<Integer,Integer> entry : volumeLineScale.entrySet()){
            result = entry.getKey();
            if (entry.getValue() >= volume)
                break;
        }

        setVolume(result);
    }

    public String getCoverURL(){
        return curTrack != null ? curTrack.getCoverURL() + getCoverSize() : defaultCover;
    }

    private String getCoverSize(){
        return coverSize + "x" + coverSize;
    }

    @Override
    public void refresh() throws HomeApiWrongResponseData {
        SpeakerStatusData ssd = api.getSpeakerStatus(speakerData);
        SpeakerStatusData.SpeakerElement se =  ssd.getDevice(speaker);

        curTrack = trackBuilder.build(se);

        info.setCaption(curTrack.getArtist() + " - " +  curTrack.getTitle() + "(" + curTrack.getDuration() + ")");

        setProgressBarPosition(se.trackProgress);
        setVolumeLinePosition(se.volume);
    }

    @Override
    public void setVolume(Integer volume){
        curVolumeLevel = volume;
        //action.setVolume(getPercentVolumeLevel(curVolumeLevel).toString());

        for (int i = 0; i < this.volume.size(); i++)
            this.volume.get(i).setCaption(String.valueOf(volumeLine[i]));

        this.volume.get(curVolumeLevel).setCaption(HANDLE_SYMBOL);
    }

    public void setProgress(Integer progress){
        curSeekLevel = progress;
        //action.setVolume(getPercentVolumeLevel(curVolumeLevel).toString());

        for (int i = 0; i < progressBar.size(); i++)
            this.progressBar.get(i).setCaption(String.valueOf(progressBarLine[i]));

        this.progressBar.get(curSeekLevel).setCaption(HANDLE_SYMBOL);
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