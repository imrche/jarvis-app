package org.rch.jarvisapp.bot.ui;

import org.rch.jarvisapp.AppContextHolder;
import org.rch.jarvisapp.bot.MessageBuilder;
import org.rch.jarvisapp.bot.enums.CommonCallBack;
import org.rch.jarvisapp.bot.enums.ParseMode;
import org.rch.jarvisapp.bot.ui.button.Button;
import org.rch.jarvisapp.bot.ui.keyboard.KeyBoard;
import org.rch.jarvisapp.smarthome.api.Api;

import java.util.*;

public class Tile{
    Stack<Tile> historyStack = new Stack<>();
    Integer messageId;
    String caption;
    List<KeyBoard> content = new ArrayList<>();
    KeyBoard stepBackButton = new KeyBoard();

    ParseMode parseMode;

    public Tile(String caption, KeyBoard keyBoard) {
        this();
        this.caption = caption;
        content.add(keyBoard);
    }

    public Tile() {
        stepBackButton.addButton(1, new Button("Назад", CommonCallBack.stepBackTile.name()));
    }

    public Tile refresh(){
        for (KeyBoard kb : content)
            kb.refresh();

        return this;
    }

    private KeyBoard getUnionKeyBoard() {
        KeyBoard kb = new KeyBoard();
        for (KeyBoard keyBoard : content)
            kb.merge(keyBoard.getKeyboard());

        if (historyStack.size()>0)
            kb.merge(stepBackButton);

        return kb;
    }

    public Tile setParseMode(ParseMode mode){
        parseMode = mode;
        return this;
    }

    public void publish() {
        if (messageId == null)
            messageId = getMessageBuilder().sendAsync(caption, getUnionKeyBoard());
        else
            getMessageBuilder().editAsync(messageId, caption, getUnionKeyBoard(),parseMode);

        getTilePool().setFeedBack(messageId);
    }

    public Tile setKeyboard(KeyBoard keyBoard){
        content.clear();
        content.add(keyBoard);
        return this;
    }

    public Tile clearKeyboard(){
        content.clear();
        return this;
    }

    public Tile addKeyboard(KeyBoard keyBoard){
        content.add(keyBoard);
        return this;
    }


    public Tile setKeyboard(List<KeyBoard> keyBoard){
        content.clear();
        content.addAll(keyBoard);
        return this;
    }

    public Tile setCaption(String caption){
        this.caption = caption;
        return this;
    }


    public Tile update(){
        //предполагается что после update keyboard будет создан новый, а не изменен старый
        Tile tile = new Tile()
                .setCaption(caption)
                .setKeyboard(content)
                .setParseMode(parseMode);

        historyStack.push(tile);

        return this;
    }

    public Tile stepBack(){
        Tile t = historyStack.pop();
        this.caption = t.caption;
        this.content = t.content;
        this.parseMode = t.parseMode;

        refresh();

        return this;
    }

    public void popup(String message){
        getMessageBuilder().popupAsync(messageId.toString(), message);
    }

    public MessageBuilder getMessageBuilder(){
        return AppContextHolder.getMessageBuilder();
    }
    public Api getApi(){
        return AppContextHolder.getApi();
    }
    public TilePool getTilePool(){
        return AppContextHolder.getTilePool();
    }
}
