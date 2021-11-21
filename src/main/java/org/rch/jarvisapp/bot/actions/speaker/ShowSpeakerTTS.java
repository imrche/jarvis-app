package org.rch.jarvisapp.bot.actions.speaker;

import org.rch.jarvisapp.bot.actions.Action;
import org.rch.jarvisapp.bot.enums.ParseMode;
import org.rch.jarvisapp.bot.exceptions.HomeApiWrongResponseData;
import org.rch.jarvisapp.bot.ui.Tile;
import org.rch.jarvisapp.bot.ui.keyboard.KeyBoard;
import org.rch.jarvisapp.bot.ui.keyboard.speaker.SpeakerKeyboard;
import org.rch.jarvisapp.bot.ui.keyboard.speaker.TTSKeyboard;
import org.rch.jarvisapp.smarthome.devices.Speaker;

public class ShowSpeakerTTS implements Action {
    public final static String description = "Сценарии";
   // SmartHome smartHome = AppContextHolder.getSH();

    Speaker speaker;


    public ShowSpeakerTTS(Speaker speaker) {
        this.speaker = speaker;

    }


    @Override
    public void run(Tile tile) throws HomeApiWrongResponseData {
        KeyBoard kb = new TTSKeyboard(speaker);

        tile.update()
                .setCaption("✏"+speaker.getPlacement().getName())
                .setParseMode(ParseMode.Markdown)
                .setKeyboard(kb);
    }
    @Override
    public int hashCode() {
        return (speaker.hashCode() + this.getClass().hashCode());
    }
}
