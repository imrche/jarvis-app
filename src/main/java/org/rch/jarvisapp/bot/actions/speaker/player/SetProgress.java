package org.rch.jarvisapp.bot.actions.speaker.player;

import org.rch.jarvisapp.bot.dataobject.SpeakerData;
import org.rch.jarvisapp.bot.exceptions.HomeApiWrongResponseData;
import org.rch.jarvisapp.bot.ui.Tile;
import org.rch.jarvisapp.bot.ui.keyboard.KeyBoard;
import org.rch.jarvisapp.bot.ui.keyboard.speaker.PlayerKeyboard;
import org.rch.jarvisapp.bot.ui.keyboard.speaker.ProgressLine;
import org.rch.jarvisapp.bot.ui.yandexStation.Track;
import org.rch.jarvisapp.smarthome.devices.Speaker;

public class SetProgress extends SimplePlayerCommand {
    private final Integer progress;

    public SetProgress(Speaker speaker, Integer progress) {
        super(speaker, SpeakerData.Command.rewind);
        this.progress = progress;
    }

    @Override
    public void run(Tile tile) throws HomeApiWrongResponseData {
        for (KeyBoard kb : tile.getContent()){
            if (kb instanceof PlayerKeyboard) {
                PlayerKeyboard pkb =((PlayerKeyboard) kb);
                Track track = pkb.getCurTrack();
                pkb.setProgress(progress);
                if (track != null) {
                    int newPosition = Math.round(track.getDuration() * ProgressLine.getProgressLevel(progress).floatValue()/100);
                    data.addCommand(speaker, command, Integer.toString(newPosition));
                }
                super.run(tile);
                return;
            }
        }
    }

    @Override
    public int hashCode() {
        return (progress.hashCode() + this.getClass().hashCode());
    }
}