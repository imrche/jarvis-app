package org.rch.jarvisapp.bot;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramWebhookBot;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
public class JarvisBot extends TelegramWebhookBot {

    BotConfig botConfig;

    public JarvisBot(BotConfig botConfig){
        this.botConfig = botConfig;
    }

    @Override
    public BotApiMethod<?> onWebhookUpdateReceived(Update update) {
        System.out.println(update);
        return new SendMessage(botConfig.getMainchat(),"Получено " + update.getMessage().getText());
    }

    @Override
    public String getBotUsername() {
        return botConfig.getName();
    }

    @Override
    public String getBotPath() {
        return botConfig.getPath();
    }

    @Override
    public String getBotToken() {
        return botConfig.getToken();
    }

    public Long getChat(){
        return botConfig.getMainchat();
    }

    public void setChat(Long chat){
        this.botConfig.setMainchat(chat);
    }
}
