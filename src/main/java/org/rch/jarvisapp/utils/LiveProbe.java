package org.rch.jarvisapp.utils;

import lombok.SneakyThrows;
import org.rch.jarvisapp.bot.Notifier;
import org.rch.jarvisapp.home.Home;
import org.springframework.stereotype.Service;


@Service
public class LiveProbe extends Thread {
    Home home;
    Notifier notifier;
    boolean isDown;

    public LiveProbe(Home home, Notifier notifier){
        this.home = home;
        this.notifier = notifier;
        setDaemon(true);
    }

    @SneakyThrows
    @Override
    public void run() {
        while (true){
            if (!home.isOnline() && !isDown) {
                notifier.send("АЛЯРМ! Соединение с домом потеряно!");
                isDown=true;
            }
            else if(isDown){
                notifier.send("Соединение восстановлено");
                isDown=false;
            }
            Thread.sleep(60000);
        }
    }
}
