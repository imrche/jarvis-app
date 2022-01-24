package org.rch.jarvisapp.bot.ui.button;

import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.FieldDefaults;
import org.rch.jarvisapp.bot.actions.Action;
import org.rch.jarvisapp.bot.exceptions.HomeApiWrongResponseData;
import org.rch.jarvisapp.bot.ui.button.func_interface.CaptionUpdater;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@EqualsAndHashCode
public class Button{
    InlineKeyboardButton inlineButton = new InlineKeyboardButton();
    boolean visible = true;
    Action action;
    CaptionUpdater updater;

    public Button(String name, String data){
        setCaption(name);
        setCallBack(data);
    }

    public Button(String name, String data, CaptionUpdater updater){
        setCaption(name);
        this.updater = updater;
        setCallBack(data);
    }

    public Button(String name, Action data){
        inlineButton.setText(name);
        action = data;
        setCallBack(data);
    }

    public Button(String name, Action data, CaptionUpdater updater){
        inlineButton.setText(name);
        action = data;
        this.updater = updater;
        setCallBack(data);
    }

    public Button setCaptionUpdater(CaptionUpdater updater){
        this.updater = updater;
        return this;
    }

    public Button() {}

    public void refresh() throws HomeApiWrongResponseData {
        if (updater != null)
            setCaption(updater.getCaption());
    }

    public void setCaption(String name){
        inlineButton.setText(name);
    }

    public String getCaption(){
        return inlineButton.getText();
    }

    public void setCallBack(Action data){
        inlineButton.setCallbackData(data.caching());
    }

    public void setCallBack(String data){
        inlineButton.setCallbackData(data);
    }
}
