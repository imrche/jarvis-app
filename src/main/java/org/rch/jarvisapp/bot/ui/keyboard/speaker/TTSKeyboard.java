package org.rch.jarvisapp.bot.ui.keyboard.speaker;

import org.rch.jarvisapp.AppContextHolder;
import org.rch.jarvisapp.bot.actions.TextInputSupportable;
import org.rch.jarvisapp.bot.actions.speaker.Volumable;
import org.rch.jarvisapp.bot.actions.speaker.command.SetVolumeTTS;
import org.rch.jarvisapp.bot.actions.speaker.command.SpeakerTTS;
import org.rch.jarvisapp.bot.actions.speaker.player.ShowTTSMessageList;
import org.rch.jarvisapp.bot.dataobject.SpeakerSettings;
import org.rch.jarvisapp.bot.exceptions.HomeApiWrongResponseData;
import org.rch.jarvisapp.bot.ui.button.Button;
import org.rch.jarvisapp.bot.ui.keyboard.KeyBoard;
import org.rch.jarvisapp.smarthome.devices.Speaker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.rch.jarvisapp.bot.dataobject.SpeakerSettings.Settings.*;

public class TTSKeyboard extends KeyBoard implements TextInputSupportable, Volumable {
    private static final Logger logger = LoggerFactory.getLogger(TTSKeyboard.class);
    SpeakerTTS action;

    private final VolumeLine volumeLineNew;

    public TTSKeyboard(Speaker speaker) {
        action = new SpeakerTTS(speaker);
        int i = 1;
        addButton(i++, new Button("Шаблоны", new ShowTTSMessageList(speaker, () -> getMessageLists(patternMessage))));
        addButton(i++, new Button("Недавнее", new ShowTTSMessageList(speaker, () -> getMessageLists(recentMessage))));

        volumeLineNew = new VolumeLine(SetVolumeTTS::new);

        for (Button btn : volumeLineNew.getVolumeButton())
            addButton(i, btn);

        setVolume(getDefaultTTSVolume(speaker));
    }

    @Override
    public void setVolume(Integer volume){
        action.setVolume(VolumeLine.getVolumeLevel(volume).toString());
        volumeLineNew.setVolume(volume);
    }

    @Override
    public void ProceedTextInput(String text) {
        action.run(text);
    }

    public Integer getDefaultTTSVolume(Speaker speaker){
        try {
            SpeakerSettings patternSettings = new SpeakerSettings();
            patternSettings.addSpeaker(speaker);

            SpeakerSettings ss = AppContextHolder.getApi().getSpeakerSettings(patternSettings);
            Object volumeResponse = ss.getSettings(speaker).get(volumeTTS);

            if (!(volumeResponse instanceof Number))
                throw new HomeApiWrongResponseData("Не получено/некорректное значение параметра " + volumeTTS.name());

            return VolumeLine.getVolumePosition((Integer)volumeResponse);
        } catch (HomeApiWrongResponseData e) {
            logger.error("Ошибка получения значения настройки громкости",e);
            return 2;
        }
    }

    private String[] getMessageLists(SpeakerSettings.Settings setting){
        try {
            SpeakerSettings patternSettings = new SpeakerSettings().needCommon();
            SpeakerSettings ss = AppContextHolder.getApi().getSpeakerSettings(patternSettings);

            return (String[]) ss.getCommonSettings().get(setting);
        } catch (HomeApiWrongResponseData e) {
            logger.error("Ошибка получения значения настройки громкости",e);
            return new String[]{};
        }
    }
}