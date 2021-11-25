package org.rch.jarvisapp.bot.ui;

import org.rch.jarvisapp.AppContextHolder;
import org.rch.jarvisapp.bot.actions.speaker.player.ClosePlayer;
import org.rch.jarvisapp.bot.enums.CommonCallBack;
import org.rch.jarvisapp.bot.exceptions.HomeApiWrongResponseData;
import org.rch.jarvisapp.bot.ui.button.Button;
import org.rch.jarvisapp.bot.ui.keyboard.KeyBoard;
import org.rch.jarvisapp.bot.ui.keyboard.speaker.PlayerKeyboard;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TilePlayer extends Tile {

    String image;
    ClosePlayer closeAction = new ClosePlayer();
    Button close = new Button("закрыть", closeAction);

    public String getImage() {
        return image;
    }

    public TilePlayer setImage(String image) {
        this.image = image;
        return this;
    }

    public TilePlayer(KeyBoard keyBoard) {
        this();
        content.add(keyBoard);
        AppContextHolder.getTilePool().addTile(this);
    }

    public TilePlayer() {}

    @Override
    public TilePlayer refresh() throws HomeApiWrongResponseData {
        for (KeyBoard kb : content) {
            kb.refresh();
            if (kb instanceof PlayerKeyboard)
                setImage(((PlayerKeyboard)kb).getCoverURL());
        }
        return this;
    }


    private InlineKeyboardMarkup getUnionKeyBoard() throws HomeApiWrongResponseData {
        InlineKeyboardMarkup kb = new InlineKeyboardMarkup();

        List<List<InlineKeyboardButton>> mList = new ArrayList<>();

        for (KeyBoard keyBoard : content) {
            keyBoard.refresh();

            for (List<Button> lBtn : keyBoard.getInlineButtons()){
                List<InlineKeyboardButton> list = new ArrayList<>();
                for (Button btn : lBtn)
                    list.add(btn.getInlineButton());

                if (list.size() > 0)
                    mList.add(list);
            }
        }

        mList.add(new ArrayList<>(Collections.singletonList(close.getInlineButton())));

        kb.setKeyboard(mList);

        return kb;
    }

    @Override
    public void publish() throws HomeApiWrongResponseData {
        if (messageId == null)
            messageId = getMessageBuilder().sendImageAsync(image, getUnionKeyBoard());
        else {
            getMessageBuilder().editImageAsync(messageId, image, getUnionKeyBoard(), parseMode);
        }

        getTilePool().setTileWithTextInputActivated(this);
        getTilePool().setFeedBack(messageId);
        closeAction.setMessageId(messageId);
    }

    @Override
    public TilePlayer update(){
        return this;
    }

    @Override
    public TilePlayer stepBack() {
        return this;
    }
}
