package org.rch.jarvisapp.bot.ui.button.func_interface;

@FunctionalInterface
public interface SimpleActionRunnerPar<T> {
    void run(T t);
}