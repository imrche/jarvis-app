package org.rch.jarvisapp.bot.actions.speaker;

import org.rch.jarvisapp.bot.actions.Action;
import org.rch.jarvisapp.bot.exceptions.HomeApiWrongResponseData;
import org.rch.jarvisapp.bot.ui.Tile;
import org.rch.jarvisapp.bot.ui.keyboard.KeyBoard;
import org.rch.jarvisapp.bot.ui.keyboard.ScenarioListKeyBoard;
import org.rch.jarvisapp.bot.ui.keyboard.speaker.SpeakerListKeyboard;
import org.rch.jarvisapp.smarthome.devices.Speaker;

public class ShowAllSpeakers implements Action {
    public final static String description = "Умные колонки";

    public ShowAllSpeakers() {}

    @Override
    public void run(Tile tile) throws HomeApiWrongResponseData {
        KeyBoard kb = new SpeakerListKeyboard();

        tile.update()
                .setCaption(description)
                .setKeyboard(kb);
    }
    @Override
    public int hashCode() {
        return ("allSpeakers".hashCode() + this.getClass().hashCode());
    }
}
