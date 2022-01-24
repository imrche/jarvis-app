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

public class ShowEffectSettings implements Action {
    Speaker speaker;
    String[] listEffects;

    public ShowEffectSettings(Speaker speaker, String[] listEffects) {
        this.speaker = speaker;
        this.listEffects = listEffects;
    }

    @Override
    public void run(Tile tile) throws HomeApiWrongResponseData {
        KeyBoard kb = new KeyBoard();
        int i = 1;
        for (String effect : listEffects)
            kb.addButton(i++, new Button(effect, new SimpleRunAction(() -> setEffect(effect), SimpleRunAction.Type.STEP_BACK)));

        tile.update()
                .setCaption(speaker.getPlacement().getName())
                .setParseMode(ParseMode.Markdown)
                .setKeyboard(kb);
    }

    private void setEffect(String effect){
        SpeakerSettings ss = new SpeakerSettings();
        ss.setSettings(speaker, SpeakerSettings.Settings.effectTTS, effect);

        AppContextHolder.getApi().setSpeakerSettings(ss);
    }

    @Override
    public int hashCode() {
        return (speaker.hashCode() + this.getClass().hashCode());
    }
}