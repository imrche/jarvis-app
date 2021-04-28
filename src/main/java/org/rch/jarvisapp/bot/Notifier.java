package org.rch.jarvisapp.bot;

import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Service
public class Notifier {
    private JarvisBot bot;

    public Notifier(JarvisBot bot){
        this.bot = bot;
    }

    public void send(String text) throws TelegramApiException {
        bot.execute(new SendMessage(bot.getChat(), text));
    }
}
