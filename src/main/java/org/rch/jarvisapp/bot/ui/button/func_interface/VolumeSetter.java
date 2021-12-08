package org.rch.jarvisapp.bot.ui.button.func_interface;

import org.rch.jarvisapp.bot.actions.Action;

@FunctionalInterface
public interface VolumeSetter {
    Action set(Integer i);
}