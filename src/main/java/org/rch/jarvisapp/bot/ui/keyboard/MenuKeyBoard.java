package org.rch.jarvisapp.bot.ui.keyboard;

import org.rch.jarvisapp.bot.enums.BotCommand;
import org.rch.jarvisapp.bot.enums.Menu;
import org.rch.jarvisapp.bot.ui.button.Button;

public class MenuKeyBoard extends KeyBoard {
    public MenuKeyBoard(BotCommand botCommand) {
        for (Menu menuItem : Menu.values()) {
            if (menuItem.isPartOf(botCommand)) {
                if (menuItem.getProduceCommand() != null)
                    addButton(menuItem.getRow(), new Button(menuItem.getDescription(), menuItem.getProduceCommand().name()));
                if (menuItem.isProduceActionInstance())
                    addButton(menuItem.getRow(), new Button(menuItem.getDescription(), menuItem.getActionInstance()));
            }
        }
    }
}
