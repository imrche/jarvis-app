package org.rch.jarvisapp.bot.ui.button.func_interface;

import java.util.Map;

@FunctionalInterface
public interface GroupCaptionUpdater {
    Map<String, String> getCaption();
}