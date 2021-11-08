package org.rch.jarvisapp.bot.actions.scenario;

import org.rch.jarvisapp.bot.actions.Action;
import org.rch.jarvisapp.bot.enums.ParseMode;
import org.rch.jarvisapp.bot.exceptions.HomeApiWrongResponseData;
import org.rch.jarvisapp.bot.ui.Tile;
import org.rch.jarvisapp.bot.ui.keyboard.KeyBoard;
import org.rch.jarvisapp.bot.ui.keyboard.ScenarioListKeyBoard;

public class ShowAllScenarios implements Action {
    public final static String description = "Сценарии";

    public ShowAllScenarios() {}

    @Override
    public void run(Tile tile) throws HomeApiWrongResponseData {
        KeyBoard kb = new ScenarioListKeyBoard();

        tile.update()
                .setCaption(description)
                .setParseMode(ParseMode.Markdown)
                .setKeyboard(kb);
    }
    @Override
    public int hashCode() {
        return ("noPlace".hashCode() + this.getClass().hashCode());
    }
}
