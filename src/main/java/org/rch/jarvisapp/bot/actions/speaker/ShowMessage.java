package org.rch.jarvisapp.bot.actions.speaker;

import org.rch.jarvisapp.AppContextHolder;
import org.rch.jarvisapp.bot.actions.Action;
import org.rch.jarvisapp.bot.exceptions.HomeApiWrongResponseData;
import org.rch.jarvisapp.bot.ui.Tile;
import org.rch.jarvisapp.bot.ui.button.func_interface.SimpleTextActionRunner;

public class ShowMessage implements Action {
    SimpleTextActionRunner runner;
    public ShowMessage(SimpleTextActionRunner runner) {
        this.runner = runner;
    }

    @Override
    public void run(Tile tile) throws HomeApiWrongResponseData {
        AppContextHolder.getMessageBuilder().popupAsync(runner.run(null));
    }

    @Override
    public int hashCode() {
        return (this.getClass().hashCode());
    }
}