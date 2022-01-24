package org.rch.jarvisapp.bot.actions.speaker.player.settings;

import org.rch.jarvisapp.AppContextHolder;
import org.rch.jarvisapp.bot.actions.Action;
import org.rch.jarvisapp.bot.actions.SimpleRunAction;
import org.rch.jarvisapp.bot.dataobject.SpeakerSettings;
import org.rch.jarvisapp.bot.enums.ParseMode;
import org.rch.jarvisapp.bot.exceptions.HomeApiWrongResponseData;
import org.rch.jarvisapp.bot.ui.Tile;
import org.rch.jarvisapp.bot.ui.button.Button;
import org.rch.jarvisapp.bot.ui.keyboard.KeyBoard;
import org.rch.jarvisapp.bot.ui.keyboard.speaker.VolumeLine;
import org.rch.jarvisapp.smarthome.devices.Speaker;

public class ShowVolumeSettings implements Action {
    Speaker speaker;

    public ShowVolumeSettings(Speaker speaker) {
        this.speaker = speaker;
    }

    @Override
    public void run(Tile tile) throws HomeApiWrongResponseData {
        KeyBoard kb = new KeyBoard();
        for (Integer level : VolumeLine.volumeScale)
            kb.addButton(1, new Button(String.valueOf(level), new SimpleRunAction(() -> setVolume(level), SimpleRunAction.Type.STEP_BACK)));

        tile.update()
                .setCaption(speaker.getPlacement().getName())
                .setParseMode(ParseMode.Markdown)
                .setKeyboard(kb);
    }

    private void setVolume(Integer volume){
        SpeakerSettings ss = new SpeakerSettings();
        ss.setSettings(speaker, SpeakerSettings.Settings.volumeTTS, String.valueOf(volume));

        AppContextHolder.getApi().setSpeakerSettings(ss);
    }

    @Override
    public int hashCode() {
        return (speaker.hashCode() + this.getClass().hashCode());
    }
}