package org.rch.jarvisapp.bot.actions.speaker.player;

import org.rch.jarvisapp.AppContextHolder;
import org.rch.jarvisapp.bot.actions.Action;
import org.rch.jarvisapp.bot.exceptions.HomeApiWrongResponseData;
import org.rch.jarvisapp.bot.ui.Tile;
import org.rch.jarvisapp.bot.ui.TilePool;
import org.rch.jarvisapp.bot.ui.keyboard.KeyBoard;
import org.rch.jarvisapp.bot.ui.keyboard.speaker.PlayerKeyboard;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.DeleteMessage;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

public class ClosePlayer implements Action {
    private final TilePool tilePool = AppContextHolder.getTilePool();
    DeleteMessage deleteMessage = new DeleteMessage();
    Integer messageId;

    public ClosePlayer() {
        deleteMessage.setChatId(AppContextHolder.getBot().getChat());
    }

    public void setMessageId(Integer messageId){
        this.messageId = messageId;
        deleteMessage.setMessageId(messageId);
    }

    @Override
    public void run(Tile tile) throws HomeApiWrongResponseData {
        tilePool.clearFeedBack(messageId);
        tilePool.removeTile(tile);

        for (KeyBoard kb : tile.getContent()) {
            if (kb instanceof PlayerKeyboard)
                ((PlayerKeyboard) kb).deletePlayer();
        }

        try {
            AppContextHolder.getBot().execute(deleteMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();//todo
        }
    }

    @Override
    public int hashCode() {
        return (deleteMessage.hashCode() +  this.getClass().hashCode());
    }
}
