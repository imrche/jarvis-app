package org.rch.jarvisapp.bot.actions.speaker.player;

import org.rch.jarvisapp.bot.actions.Action;
import org.rch.jarvisapp.bot.actions.speaker.Volumable;
import org.rch.jarvisapp.bot.exceptions.HomeApiWrongResponseData;
import org.rch.jarvisapp.bot.ui.Tile;
import org.rch.jarvisapp.bot.ui.keyboard.KeyBoard;

public class SetVolume implements Action {
    private final Integer volume;

    public SetVolume(Integer volume) {
        this.volume = volume;
    }

    @Override
    public void run(Tile tile) throws HomeApiWrongResponseData {
        for (KeyBoard kb : tile.getContent()){
            if (kb instanceof Volumable)
                ((Volumable) kb).setVolume(volume);
        }
    }

    @Override
    public int hashCode() {
        //todo тут могут быть только final
        return (volume.hashCode() + this.getClass().hashCode());
    }
}
