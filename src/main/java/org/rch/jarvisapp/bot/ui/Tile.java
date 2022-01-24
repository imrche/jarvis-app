package org.rch.jarvisapp.bot.ui;

import org.rch.jarvisapp.AppContextHolder;
import org.rch.jarvisapp.bot.MessageBuilder;
import org.rch.jarvisapp.bot.actions.TextInputSupportable;
import org.rch.jarvisapp.bot.enums.CommonCallBack;
import org.rch.jarvisapp.bot.enums.ParseMode;
import org.rch.jarvisapp.bot.exceptions.HomeApiWrongResponseData;
import org.rch.jarvisapp.bot.ui.button.Button;
import org.rch.jarvisapp.bot.ui.button.func_interface.TileCaptionUpdater;
import org.rch.jarvisapp.bot.ui.keyboard.KeyBoard;
import org.rch.jarvisapp.smarthome.api.Api;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.*;

public class Tile{
    Stack<Tile> historyStack = new Stack<>();

    Integer messageId;
    String caption;

    TileCaptionUpdater tileCaptionUpdater;

    List<KeyBoard> content = new ArrayList<>();
    Button stepBack = new Button("Назад", CommonCallBack.stepBackTile.name());

    ParseMode parseMode;

    public Tile(String caption, KeyBoard keyBoard) {
        this();
        this.caption = caption;
        content.add(keyBoard);
    }

    public Tile() {}

    public List<KeyBoard> getContent() {
        return content;
    }

    public Tile refresh() throws HomeApiWrongResponseData {
        //todo сделать приватной, чтобы избежать вызовов ненужных
        for (KeyBoard kb : content)
            kb.refresh();

        System.out.println("обновление тайла");
        if (tileCaptionUpdater != null)
            setCaption(tileCaptionUpdater.getCaption());

//todo если сломаются сценарии, значит порядок запуска был важен

        return this;
    }

    public Integer getMessageId() {
        return messageId;
    }

    private Boolean isReady2Text(){
        for (KeyBoard keyBoard : content) {
            if (keyBoard instanceof TextInputSupportable)
                return true;//todo ругаться через попап если клав несколько
        }
        return false;
    }

    private InlineKeyboardMarkup getUnionKeyBoard() throws HomeApiWrongResponseData {
        InlineKeyboardMarkup kb = new InlineKeyboardMarkup();

        List<List<InlineKeyboardButton>> mList = new ArrayList<>();

        for (KeyBoard keyBoard : content) {
           // keyBoard.refresh();

           // for (List<Button> lBtn : keyBoard.getInlineButtons()){
            for (List<Button> lBtn : keyBoard.getVisibleButtons()){
                List<InlineKeyboardButton> list = new ArrayList<>();
                for (Button btn : lBtn)
                    list.add(btn.getInlineButton());

                if (list.size() > 0)
                    mList.add(list);
            }
        }

        if (historyStack.size()>0)
            mList.add(new ArrayList<>(Collections.singletonList(stepBack.getInlineButton())));

        kb.setKeyboard(mList);

        return kb;
    }

    public Tile setParseMode(ParseMode mode){
        parseMode = mode;
        return this;
    }

    public void publish() throws HomeApiWrongResponseData {
        refresh();
        if (messageId == null)
            messageId = getMessageBuilder().sendAsync(caption, getUnionKeyBoard());
        else
            getMessageBuilder().editAsync(messageId, caption, getUnionKeyBoard(), parseMode);

        getTilePool().setTileWithTextInputActivated(isReady2Text() ? this : null);
        getTilePool().setFeedBack(messageId);
    }

    public Tile setKeyboard(KeyBoard keyBoard){
        content.clear();
        //content.add(keyBoard);
        addKeyboardWithBuild(keyBoard);
        return this;
    }

    public Tile clearKeyboard(){
        content.clear();
        return this;
    }

    public Tile addKeyboard(KeyBoard keyBoard){
        //content.add(keyBoard);
        addKeyboardWithBuild(keyBoard);
        return this;
    }

    private void addKeyboardWithBuild(KeyBoard keyBoard){
        keyBoard.build();
        content.add(keyBoard);
    }

    private void addKeyboardWithBuild(List<KeyBoard> keyBoard){
        for (KeyBoard kb : keyBoard)
            kb.build();
        content.addAll(keyBoard);
    }

    public Tile setKeyboard(List<KeyBoard> keyBoard){
        content.clear();
       // content.addAll(keyBoard);
        addKeyboardWithBuild(keyBoard);
        return this;
    }

    public Tile setCaption(String caption){
        this.caption = caption;

        return this;
    }

    public Tile setTileCaptionUpdater(TileCaptionUpdater tcu){
        this.tileCaptionUpdater = tcu;
        if (tcu != null)
            setCaption(tcu.getCaption());

        return this;
    }

    public Tile update(){
        //предполагается что после update keyboard будет создан новый, а не изменен старый
        Tile tile = new Tile()
                .setCaption(caption)
                .setTileCaptionUpdater(tileCaptionUpdater)
                .setKeyboard(content)
                .setParseMode(parseMode);

        historyStack.push(tile);

        return this;
    }

    public Tile stepBack() throws HomeApiWrongResponseData {
        Tile t = historyStack.pop();
        setTileCaptionUpdater(null);//wtf
        setTileCaptionUpdater(t.tileCaptionUpdater);
        setCaption(t.caption);

        this.content = t.content;
        this.parseMode = t.parseMode;

        refresh();

        return this;
    }

    public void popup(String message){
        getMessageBuilder().popupAsync(message);
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
