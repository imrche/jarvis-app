package org.rch.jarvisapp.bot.actions.speaker.player;

import org.rch.jarvisapp.bot.actions.Action;
import org.rch.jarvisapp.bot.actions.speaker.command.SpeakerTTS;
import org.rch.jarvisapp.bot.actions.SimpleRunAction;
import org.rch.jarvisapp.bot.exceptions.HomeApiWrongResponseData;
import org.rch.jarvisapp.bot.ui.Tile;
import org.rch.jarvisapp.bot.ui.button.Button;
import org.rch.jarvisapp.bot.ui.button.func_interface.MessageListGetter;
import org.rch.jarvisapp.bot.ui.keyboard.KeyBoard;

public class ShowTTSMessageList implements Action {
    private final MessageListGetter messageListGetter;
    SpeakerTTS action;

    public ShowTTSMessageList(SpeakerTTS action, MessageListGetter getter) {
        messageListGetter = getter;
        this.action = action;
    }

    @Override
    public void run(Tile tile) throws HomeApiWrongResponseData {
        KeyBoard kb = new KeyBoard();
        int i=1;
        for (String s : messageListGetter.get())
            kb.addButton(i++, new Button(s, new SimpleRunAction(() -> sendMessage(s), SimpleRunAction.Type.STEP_BACK)));
        tile.update()
                .setCaption("Выбрать сообщение")
                .setKeyboard(kb);
    }

    private void sendMessage(String text){
        action.run(text);
    }

    @Override
    public int hashCode() {
        return (messageListGetter.hashCode() + action.hashCode()+ this.getClass().hashCode());
    }
}