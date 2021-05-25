package org.rch.jarvisapp.bot.ui.button;

import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.FieldDefaults;
import org.rch.jarvisapp.AppContextHolder;
import org.rch.jarvisapp.bot.dataobject.ActionData;
import org.rch.jarvisapp.bot.enums.Settings;


@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@EqualsAndHashCode(callSuper = true)
public class SettingButton extends Button{
    Settings settings;

    public SettingButton(Settings settings, ActionData actionData) {
        super("",actionData);
        this.settings = settings;
        setCaption();
    }

    public void setCaption(){
        setText(settings.getDescription() + "   ->   " + AppContextHolder.getSettingService().tmpSettings.get(settings.name()).toString());
    }

    @Override
    public void refresh() {
        setCaption();
    }
}
