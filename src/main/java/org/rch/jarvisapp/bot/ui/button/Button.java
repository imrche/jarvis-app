package org.rch.jarvisapp.bot.ui.button;

import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.FieldDefaults;
import org.rch.jarvisapp.bot.actions.Action;
import org.rch.jarvisapp.bot.exceptions.HomeApiWrongResponseData;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@EqualsAndHashCode(callSuper = true)
public class Button extends InlineKeyboardButton {
    boolean visible = true;
    Action action;

    public Button(String name, String data){
        setCaption(name);
        setCallbackData(data);
    }

    public Button(String name, Action data){
        setText(name);
        action = data;
        setCallbackData(data.caching());
    }

    public Button() {
    }

    public void refresh() throws HomeApiWrongResponseData {
    }

    public void setCaption(String name){
        setText(name);
    }
}
