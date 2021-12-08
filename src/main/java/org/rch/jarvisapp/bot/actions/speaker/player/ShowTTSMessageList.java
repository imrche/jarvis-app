package org.rch.jarvisapp.bot.actions.speaker.player;

import org.rch.jarvisapp.bot.actions.Action;
import org.rch.jarvisapp.bot.actions.speaker.command.SpeakerTTS;
import org.rch.jarvisapp.bot.actions.speaker.player.settings.SimpleRunAction;
import org.rch.jarvisapp.bot.exceptions.HomeApiWrongResponseData;
import org.rch.jarvisapp.bot.ui.Tile;
import org.rch.jarvisapp.bot.ui.button.Button;
import org.rch.jarvisapp.bot.ui.button.func_interface.MessageListGetter;
import org.rch.jarvisapp.bot.ui.keyboard.KeyBoard;
import org.rch.jarvisapp.smarthome.devices.Speaker;

public class ShowTTSMessageList implements Action {
    private final Speaker speaker;
    private final MessageListGetter messageListGetter;
    SpeakerTTS action;

    public ShowTTSMessageList(Speaker speaker, MessageListGetter getter) {
        messageListGetter = getter;
        this.speaker = speaker;
        action = new SpeakerTTS(speaker);
    }

    @Override
    public void run(Tile tile) throws HomeApiWrongResponseData {
        KeyBoard kb = new KeyBoard();
        int i=1;
        for (String s : messageListGetter.get())
            kb.addButton(i++, new Button(s, new SimpleRunAction(() -> sendMessage(s))));
        tile.update()
                .setCaption("Выбрать сообщение")
                .setKeyboard(kb);
    }

    private void sendMessage(String text){
        action.run(text);
    }

    @Override
    public int hashCode() {
        return (messageListGetter.hashCode() + speaker.hashCode()+ this.getClass().hashCode());
    }
}