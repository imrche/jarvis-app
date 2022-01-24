package org.rch.jarvisapp.bot.ui.timerStuff;

import java.util.List;

@FunctionalInterface
public interface OptionListGetter {
    List<String> getOptionList();
}