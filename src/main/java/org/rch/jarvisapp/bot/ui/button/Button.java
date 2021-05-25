package org.rch.jarvisapp.bot.ui.button;

import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.FieldDefaults;
import org.rch.jarvisapp.AppContextHolder;
import org.rch.jarvisapp.bot.cache.ActionCache;
import org.rch.jarvisapp.bot.dataobject.ActionData;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@EqualsAndHashCode(callSuper = true)
public class Button extends InlineKeyboardButton {
    boolean visible = true;

    public ActionCache getCache(){
        return AppContextHolder.getActionCache();
    }

    public Button(String name, ActionData data){
        setText(name);
        setCallbackData(getCache().setCallBack(data).toString());
    }

    public Button(String name, String data){
        setText(name);
        setCallbackData(data);
    }

    public Button() {
    }

    public void refresh(){

    }
}
