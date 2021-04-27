package org.rch.jarvisapp.utils;

import lombok.SneakyThrows;
import org.rch.jarvisapp.bot.JarvisBot;
import org.rch.jarvisapp.home.Home;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

public class LiveProbe extends Thread {
    Home home;
    JarvisBot bot;
    boolean isDown;

    public LiveProbe(Home home, JarvisBot bot){
        this.home = home;
        this.bot = bot;
    }

    @SneakyThrows
    @Override
    public void run() {
        while (true){
            if (!home.isOnline() && !isDown) {
                bot.execute(new SendMessage(bot.getChat(), "АЛЯРМ! Соединение с домом потеряно!"));
                isDown=true;
            }
            else if(isDown){
                bot.execute(new SendMessage(bot.getChat(), "Соединение восстановлено"));
                isDown=false;
            }
            Thread.sleep(60000);
        }
    }
}
