package org.rch.jarvisapp.bot.actions.settings;

import org.rch.jarvisapp.bot.actions.Action;
import org.rch.jarvisapp.bot.enums.SettingsList;
import org.rch.jarvisapp.bot.ui.Tile;
import org.rch.jarvisapp.bot.ui.button.SettingButton;
import org.rch.jarvisapp.bot.ui.keyboard.KeyBoard;

public class ShowSettings implements Action {
    public final static String description = "Настройка";
    public String qwe;



    @Override
    public void run(Tile tile) {
        KeyBoard kb = new KeyBoard();
        int row = 1;
        for (SettingsList settingsList : SettingsList.values())
            kb.addButton(row++, new SettingButton(settingsList, new SetSettings()));

        tile.update()
                .setCaption(description)
                .setKeyboard(kb);
    }
}
