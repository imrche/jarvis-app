package org.rch.jarvisapp.bot.actions.speaker;

import org.rch.jarvisapp.AppContextHolder;
import org.rch.jarvisapp.bot.actions.Action;
import org.rch.jarvisapp.bot.enums.ParseMode;
import org.rch.jarvisapp.bot.exceptions.HomeApiWrongResponseData;
import org.rch.jarvisapp.bot.ui.Tile;
import org.rch.jarvisapp.bot.ui.TilePlayer;
import org.rch.jarvisapp.bot.ui.keyboard.KeyBoard;
import org.rch.jarvisapp.bot.ui.keyboard.speaker.PlayerKeyboard;
import org.rch.jarvisapp.bot.ui.keyboard.speaker.TTSKeyboard;
import org.rch.jarvisapp.smarthome.devices.Speaker;

public class ShowSpeakerPlayer implements Action {
    public final static String description = "Проигрыватель";
   // SmartHome smartHome = AppContextHolder.getSH();

    Speaker speaker;

    String defaultCover = "https://avatars.mds.yandex.net/i?id=7b449cdc58fa7f0586a744fc9ddf9173-5190189-images-thumbs&n=13";

    public ShowSpeakerPlayer(Speaker speaker) {
        this.speaker = speaker;
    }

    @Override
    public void run(Tile tile) throws HomeApiWrongResponseData {
        KeyBoard kb = new PlayerKeyboard(speaker);

        Tile tilePlayer = new TilePlayer(defaultCover,kb);
        //AppContextHolder.getTilePool().addTile(tilePlayer);
        tilePlayer.publish();
    }
    @Override
    public int hashCode() {
        return (speaker.hashCode() + this.getClass().hashCode());
    }
}
