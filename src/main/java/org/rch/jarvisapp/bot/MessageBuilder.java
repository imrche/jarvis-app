package org.rch.jarvisapp.bot;

import org.rch.jarvisapp.bot.enums.ParseMode;
import org.rch.jarvisapp.bot.enums.Stickers;
import org.rch.jarvisapp.bot.ui.keyboard.KeyBoard;
import org.rch.jarvisapp.utils.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.AnswerCallbackQuery;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.methods.send.SendSticker;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageMedia;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.media.InputMedia;
import org.telegram.telegrambots.meta.api.objects.media.InputMediaPhoto;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.exceptions.TelegramApiRequestException;

@Service
public class MessageBuilder {
    Logger logger = LoggerFactory.getLogger(MessageBuilder.class);
    @Autowired
    JarvisBot bot;

    public BotApiMethod<?> send(String text){
        sendAsync(text);
        return emptyAnswer();
    }

    public BotApiMethod<?> emptyAnswer(){
        return new AnswerCallbackQuery();
    }

    public BotApiMethod<?> popup(String message){
        return new AnswerCallbackQuery().setShowAlert(true).setText(message).setCallbackQueryId(bot.getLastCallBackId());
    }

    public void popupAsync(String message){
        try {
            bot.execute(new AnswerCallbackQuery()
                    .setShowAlert(true)
                    .setText(message)
                    .setCallbackQueryId(bot.getLastCallBackId()));

        } catch (TelegramApiException e) {
            logger.error(e.getMessage()/*+ " - " + e.getApiResponse()*/,e);
        }
    }

    public Integer sendAsync(String text, InlineKeyboardMarkup keyBoard){
        try {
            Message message = bot.execute(new SendMessage(bot.getChat(), text)
                    .setReplyMarkup(keyBoard));
            return message.getMessageId();
        } catch (TelegramApiException e) {
            logger.error(e.getMessage(),e);
            return -1;
        }
    }

    public Integer sendAsync(String text){
        try {
            Message message = bot.execute(new SendMessage(bot.getChat(), text));
            return message.getMessageId();
        } catch (TelegramApiException e) {
            logger.error(e.getMessage(),e);
            return -1;
        }
    }

    public Integer sendImageAsync(String image, InlineKeyboardMarkup keyBoard){
        try {
            Message message = bot.execute(new SendPhoto().setChatId(bot.getChat()).setReplyMarkup(keyBoard).setPhoto(image));
            return message.getMessageId();
        } catch (TelegramApiException e) {
            logger.error(e.getMessage(),e);
            return -1;
        }
    }

    public void editImageAsync(Integer messageId, String image, InlineKeyboardMarkup keyBoard, ParseMode parseMode){
        InputMediaPhoto media = new InputMediaPhoto();
        media.setMedia(image);
        EditMessageMedia message = new EditMessageMedia()
                .setMessageId(messageId)
                .setChatId(bot.getChat())
                .setReplyMarkup(keyBoard);
        message.setMedia(media);

        logger.trace(Util.toANSI(message.toString()));

        try {
            bot.execute(message);
        } catch (TelegramApiRequestException e) {
            logger.error(e.getApiResponse(), e);
            popupAsync(e.getApiResponse());
        } catch (TelegramApiException e ){
            logger.error(e.getMessage(), e);
            popupAsync(e.getMessage());
        }
    }

    public void editAsync(Integer messageId, String text, InlineKeyboardMarkup keyBoard, ParseMode parseMode){
        EditMessageText message = new EditMessageText()
                                            .setMessageId(messageId)
                                            .setText(text)
                                            .setParseMode(parseMode != null ? parseMode.name() : null)
                                            .setChatId(bot.getChat())
                                            .setReplyMarkup(keyBoard);

        logger.trace(Util.toANSI(message.toString()));

        try {
            bot.execute(message);
        } catch (TelegramApiRequestException e) {
            logger.error(e.getApiResponse(), e);
            popupAsync(e.getApiResponse());
        } catch (TelegramApiException e ){
            logger.error(e.getMessage(), e);
            popupAsync(e.getMessage());
        }
    }

    public BotApiMethod<?> sendStickerAsync(Stickers sticker){
        try {
            Message message = bot.execute(new SendSticker().setChatId(bot.getChat()).setSticker(sticker.getURL()));
            //return message.getMessageId();
        } catch (TelegramApiException e) {
            logger.error(e.getMessage(), e);
        }
        return emptyAnswer();
    }
}