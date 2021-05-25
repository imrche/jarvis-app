package org.rch.jarvisapp.bot;

import org.rch.jarvisapp.bot.enums.ParseMode;
import org.rch.jarvisapp.bot.enums.Stickers;
import org.rch.jarvisapp.bot.ui.keyboard.KeyBoard;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.AnswerCallbackQuery;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendSticker;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Service
public class MessageBuilder {

    @Autowired
    JarvisBot bot;

    public BotApiMethod<?> send(String text){
        sendAsync(text);
        return emptyAnswer();
    }

    public BotApiMethod<?> emptyAnswer(){
        return new AnswerCallbackQuery();
    }

    public BotApiMethod<?> popup(String id, String message){
        return new AnswerCallbackQuery().setShowAlert(true).setText(message).setCallbackQueryId(id);
    }

    public void popupAsync(String id, String message){
        try {
            bot.execute(new AnswerCallbackQuery()
                    .setShowAlert(true)
                    .setText(message)
                    .setCallbackQueryId(id));

        } catch (TelegramApiException e) {
            //todo showWarning
        }
    }


    public Integer sendAsync(String text, KeyBoard keyBoard){
        try {
            Message message = bot.execute(new SendMessage(bot.getChat(), text)
                    .setReplyMarkup(keyBoard));
            return message.getMessageId();
        } catch (TelegramApiException e) {
            //todo showWarning
            return -1;
        }
    }

    public Integer sendAsync(String text){
        try {
            Message message = bot.execute(new SendMessage(bot.getChat(), text));
            return message.getMessageId();
        } catch (TelegramApiException e) {
            //todo showWarning
            return -1;
        }
    }

    public void editAsync(Integer messageId, String text, KeyBoard keyBoard, ParseMode parseMode){
        try {
            bot.execute(
                    new EditMessageText()
                            .setMessageId(messageId)
                            .setText(text)
                            .setParseMode(parseMode != null ? parseMode.name() : null)
                            .setChatId(bot.getChat())
                            .setReplyMarkup(keyBoard));
        } catch (TelegramApiException e) {
            //todo showWarning
        }
    }

    public BotApiMethod<?> sendStickerAsync(Stickers sticker){
        try {
            Message message = bot.execute(new SendSticker().setChatId(bot.getChat()).setSticker(sticker.getURL()));
            //return message.getMessageId();
        } catch (TelegramApiException ignored) {}
        return emptyAnswer();
    }
}