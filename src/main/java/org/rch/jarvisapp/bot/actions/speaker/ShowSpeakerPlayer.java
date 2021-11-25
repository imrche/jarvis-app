package org.rch.jarvisapp.bot.actions.speaker;

import org.rch.jarvisapp.bot.actions.Action;
import org.rch.jarvisapp.bot.exceptions.HomeApiWrongResponseData;
import org.rch.jarvisapp.bot.ui.Tile;
import org.rch.jarvisapp.bot.ui.TilePlayer;
import org.rch.jarvisapp.bot.ui.keyboard.KeyBoard;
import org.rch.jarvisapp.bot.ui.keyboard.speaker.PlayerKeyboard;
import org.rch.jarvisapp.smarthome.devices.Speaker;

public class ShowSpeakerPlayer implements Action {
    public final static String description = "Проигрыватель";

    Speaker speaker;

    public ShowSpeakerPlayer(Speaker speaker) {
        this.speaker = speaker;
    }

    @Override
    public void run(Tile tile) throws HomeApiWrongResponseData {
        KeyBoard kb = new PlayerKeyboard(speaker);
        Tile tilePlayer = new TilePlayer(kb);

        tilePlayer.refresh();
        tilePlayer.publish();
    }
    @Override
    public int hashCode() {
        return (speaker.hashCode() + this.getClass().hashCode());
    }
}