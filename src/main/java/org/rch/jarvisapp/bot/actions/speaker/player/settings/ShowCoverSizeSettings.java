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

public class ShowCoverSizeSettings implements Action {
    String[] sizes = {"300","500","600","1000"};
    Speaker speaker;

    public ShowCoverSizeSettings(Speaker speaker) {
        this.speaker = speaker;
    }

    @Override
    public void run(Tile tile) throws HomeApiWrongResponseData {
        KeyBoard kb = new KeyBoard();
        for (String size : sizes)
            kb.addButton(1, new Button(size + "x" + size, new SimpleRunAction(() -> setSize(size))));

        tile.update()
                .setCaption(speaker.getPlacement().getName())
                .setParseMode(ParseMode.Markdown)
                .setKeyboard(kb);
    }

    private void setSize(String size){
        SpeakerSettings ss = new SpeakerSettings().needCommon();
        ss.setCommonSettings(SpeakerSettings.Settings.coverSize, size);

        AppContextHolder.getApi().setSpeakerSettings(ss);
    }

    @Override
    public int hashCode() {
        return (speaker.hashCode() + this.getClass().hashCode());
    }
}
