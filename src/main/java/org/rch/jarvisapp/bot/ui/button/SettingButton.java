package org.rch.jarvisapp.bot.ui.button;

import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.FieldDefaults;
import org.rch.jarvisapp.AppContextHolder;
import org.rch.jarvisapp.bot.actions.Action;
import org.rch.jarvisapp.bot.enums.SettingsList;


@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@EqualsAndHashCode(callSuper = true)
public class SettingButton extends Button{
    SettingsList settingsList;

    public SettingButton(SettingsList settingsList, Action action) {
        super("",action);
        this.settingsList = settingsList;
        setCaption();
    }

    public void setCaption(){
        setText(settingsList.getDescription() + "   ->   " + AppContextHolder.getSettings().getSetting(settingsList).toString());
    }

    @Override
    public void refresh() {
        setCaption();
    }
}
