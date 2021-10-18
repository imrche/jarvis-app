package org.rch.jarvisapp.bot.ui.button;

import com.fasterxml.jackson.annotation.JsonIgnoreType;
import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.FieldDefaults;
import org.rch.jarvisapp.bot.actions.Action;
import org.rch.jarvisapp.bot.exceptions.HomeApiWrongResponseData;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@EqualsAndHashCode
public class Button{
    InlineKeyboardButton inlineButton = new InlineKeyboardButton();
    boolean visible = true;
    Action action;

    public Button(String name, String data){
        setCaption(name);
        setCallBack(data);
    }

    public Button(String name, Action data){
        inlineButton.setText(name);
        action = data;
        setCallBack(data);
    }

    public Button() {}

    public void refresh() throws HomeApiWrongResponseData {}

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
