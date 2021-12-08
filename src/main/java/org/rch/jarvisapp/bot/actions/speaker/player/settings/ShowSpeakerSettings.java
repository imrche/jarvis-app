package org.rch.jarvisapp.bot.actions.speaker.player.settings;

import org.rch.jarvisapp.AppContextHolder;
import org.rch.jarvisapp.bot.actions.Action;
import org.rch.jarvisapp.bot.dataobject.SpeakerSettings;
import org.rch.jarvisapp.bot.enums.ParseMode;
import org.rch.jarvisapp.bot.exceptions.HomeApiWrongResponseData;
import org.rch.jarvisapp.bot.ui.Tile;
import org.rch.jarvisapp.bot.ui.button.Button;
import org.rch.jarvisapp.bot.ui.keyboard.KeyBoard;
import org.rch.jarvisapp.smarthome.devices.Speaker;

import java.util.HashMap;
import java.util.Map;

import static org.rch.jarvisapp.bot.dataobject.SpeakerSettings.Settings.*;

public class ShowSpeakerSettings implements Action {
    Speaker speaker;
    String[] voicesList;
    String[] effectsList;

    private enum Codes{
        cover,
        voice,
        effect,
        volume
    }

    public ShowSpeakerSettings(Speaker speaker) {
        this.speaker = speaker;
    }

    @Override
    public void run(Tile tile) throws HomeApiWrongResponseData {
        KeyBoard kb = new KeyBoard();
        kb.setUpdater(this::getUpdatedCaptions);
        getListsOfProperties();

        kb.addButton(1, Codes.voice.name(),     new Button("",  new ShowVoiceSettings(speaker,voicesList)));
        kb.addButton(2, Codes.effect.name(),    new Button("",  new ShowEffectSettings(speaker,effectsList)));
        kb.addButton(3, Codes.volume.name(),    new Button("",  new ShowVolumeSettings(speaker)));
        kb.addButton(4, Codes.cover.name(),     new Button("",  new ShowCoverSizeSettings(speaker)));

        tile.update()
                .setCaption(speaker.getPlacement().getName())
                .setParseMode(ParseMode.Markdown)
                .setKeyboard(kb);
    }

/*    private String updateCaption(String setting){
        System.out.println("update caption 4 " + setting);
        return setting;
    }*/

    private Map<String, String> getUpdatedCaptions(){
        Map<String, String> data = new HashMap<>();

        try {
            SpeakerSettings patternSettings = new SpeakerSettings().needCommon();
            patternSettings.addSpeaker(speaker);

            SpeakerSettings ss = AppContextHolder.getApi().getSpeakerSettings(patternSettings);
            data.put(Codes.cover.name(),   "Размер обложки (для всех)"     + " : [" + ss.getCommonSettings().get(coverSize)  + "]");
            data.put(Codes.voice.name(),   "Голос"                         + " : [" + ss.getSettings(speaker).get(voiceTTS)  + "]");
            data.put(Codes.effect.name(),  "Эффект"                        + " : [" + ss.getSettings(speaker).get(effectTTS) + "]");
            data.put(Codes.volume.name(),  "Громкость по-умолчанию"        + " : [" + ss.getSettings(speaker).get(volumeTTS) + "]");
        } catch (HomeApiWrongResponseData homeApiWrongResponseData) {}

        return data;
    }

    private void getListsOfProperties(){
        try {
            SpeakerSettings patternSettings = new SpeakerSettings().needCommon();

            SpeakerSettings ss = AppContextHolder.getApi().getSpeakerSettings(patternSettings);
            voicesList = (String[]) ss.getCommonSettings().get(voices);
            effectsList = (String[]) ss.getCommonSettings().get(effects);
        } catch (HomeApiWrongResponseData ignored) {}
    }

    @Override
    public int hashCode() {
        return (speaker.hashCode() + this.getClass().hashCode());
    }
}