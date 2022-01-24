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
import org.rch.jarvisapp.smarthome.devices.Speaker;

public class ShowVoiceSettings implements Action {
    Speaker speaker;
    String[] listVoice;

    public ShowVoiceSettings(Speaker speaker, String[] listVoice) {
        this.speaker = speaker;
        this.listVoice = listVoice;
    }

    @Override
    public void run(Tile tile) throws HomeApiWrongResponseData {
        KeyBoard kb = new KeyBoard();
        int i = 1;
        for (String voice : listVoice)
            kb.addButton(i++, new Button(voice, new SimpleRunAction(() -> setVoice(voice), SimpleRunAction.Type.STEP_BACK)));

        tile.update()
                .setCaption(speaker.getPlacement().getName())
                .setParseMode(ParseMode.Markdown)
                .setKeyboard(kb);
    }

    private void setVoice(String voice){
        SpeakerSettings ss = new SpeakerSettings();
        ss.setSettings(speaker, SpeakerSettings.Settings.voiceTTS, voice);

        AppContextHolder.getApi().setSpeakerSettings(ss);
    }

    @Override
    public int hashCode() {
        return (speaker.hashCode() + this.getClass().hashCode());
    }
}