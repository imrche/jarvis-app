package org.rch.jarvisapp.bot.actions.speaker.player;

import org.rch.jarvisapp.bot.actions.speaker.Volumable;
import org.rch.jarvisapp.bot.dataobject.SpeakerData;
import org.rch.jarvisapp.bot.exceptions.HomeApiWrongResponseData;
import org.rch.jarvisapp.bot.ui.Tile;
import org.rch.jarvisapp.bot.ui.keyboard.KeyBoard;
import org.rch.jarvisapp.bot.ui.keyboard.speaker.PlayerKeyboard;
import org.rch.jarvisapp.smarthome.devices.Speaker;

public class SetVolume extends SimplePlayerCommand {
    private final Integer volume;

    public SetVolume(Speaker speaker, Integer volume) {
        super(speaker, SpeakerData.Command.setVolume);
        this.volume = volume;
    }

    @Override
    public void run(Tile tile) throws HomeApiWrongResponseData {
        for (KeyBoard kb : tile.getContent()){
            if (kb instanceof PlayerKeyboard) {
                PlayerKeyboard pkb =((PlayerKeyboard) kb);

                pkb.setVolume(volume);

                data.addCommand(speaker, command, pkb.getPercentVolumeLevel(volume).toString());

                super.run(tile);
                return;
            }
        }
    }

    @Override
    public int hashCode() {
        return (volume.hashCode() + this.getClass().hashCode());
    }
}