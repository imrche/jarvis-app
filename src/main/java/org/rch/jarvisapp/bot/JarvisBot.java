package org.rch.jarvisapp.bot;

import org.rch.jarvisapp.bot.actions.TextInputSupportable;
import org.rch.jarvisapp.bot.cache.ActionCache;
import org.rch.jarvisapp.bot.actions.Action;
import org.rch.jarvisapp.bot.enums.BotCommand;
import org.rch.jarvisapp.bot.enums.CommonCallBack;
import org.rch.jarvisapp.bot.enums.Stickers;
import org.rch.jarvisapp.bot.exceptions.BotException;
import org.rch.jarvisapp.bot.exceptions.HomeApiWrongResponseData;
import org.rch.jarvisapp.bot.ui.*;
import org.rch.jarvisapp.bot.ui.keyboard.KeyBoard;
import org.rch.jarvisapp.bot.ui.keyboard.MenuKeyBoard;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramWebhookBot;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
public class JarvisBot extends TelegramWebhookBot {
    static Logger logger = LoggerFactory.getLogger(JarvisBot.class);
    @Autowired
    MessageBuilder messageBuilder;
    BotConfig botConfig;
    ActionCache actionCache;
    TilePool tilePool;
    String lastCallBackId = "";

    public JarvisBot(BotConfig botConfig, ActionCache actionCache, TilePool tilePool){
        this.botConfig = botConfig;
        this.tilePool = tilePool;
        this.actionCache = actionCache;
    }

    @Override
    public BotApiMethod<?> onWebhookUpdateReceived(Update update) {
        try {
            //System.out.println("-------------------------------------------------------------------");
            //System.out.println(update);

            if (update.hasMessage()) {
                Message message = update.getMessage();
                if (message.isCommand()) {
                    try {
                        BotCommand command = BotCommand.valueOf(message.getText().replace("/", ""));
                        tilePool.build(command).publish();
                        return messageBuilder.emptyAnswer();
                    } catch (IllegalArgumentException e) {
                        throw new BotException("Неопознанная команда " + message.getText());
                    }
                } else if (tilePool.getTileWithTextInputActivated() != null) {
                    Tile tileWith = tilePool.getTileWithTextInputActivated();
                    for (KeyBoard keyBoard : tileWith.getContent()){
                        if (keyBoard instanceof TextInputSupportable)
                            ((TextInputSupportable)keyBoard).ProceedTextInput(message.getText());
                    }
                    messageBuilder.popupAsync("Успешно отправлено");
                    return messageBuilder.emptyAnswer();
                } else {
                    return messageBuilder.sendStickerAsync(Stickers.what);
                }
            }

            if (update.hasCallbackQuery()) {
                Integer messageId = update.getCallbackQuery().getMessage().getMessageId();
                String callBackData = update.getCallbackQuery().getData();
                lastCallBackId = update.getCallbackQuery().getId();

                Tile tile = tilePool.getTileWith(messageId);

                if (tile == null)
                    throw new BotException("CallBack не актуален");  

                //кнопка EMPTY
                if (CommonCallBack.empty.is(callBackData))
                    return messageBuilder.popup("Пусто");

                //кнопка НАЗАД
                if (CommonCallBack.stepBackTile.is(callBackData)) {
                    tile.stepBack().publish();
                    return messageBuilder.emptyAnswer();
                }

                //команды отправленные из МЕНЮ
                try {
                    tile.setKeyboard(new MenuKeyBoard(BotCommand.valueOf(callBackData)))
                            .publish();

                    return messageBuilder.emptyAnswer();
                } catch (IllegalArgumentException ignored) {}

                //основные действия через callback (кэшированный)
                Action actionData = actionCache.getCallBack(callBackData);

                if (actionData == null)
                    throw new BotException("CallBack " + update.getCallbackQuery().getData() + " не существует");

                //запустить действие
                actionData.run(tile);
                tile.publish();

                return messageBuilder.emptyAnswer();
            }
        } catch (BotException | HomeApiWrongResponseData botException){
            logger.error("Ошибка",botException);
            if (update.hasMessage())
                return messageBuilder.send(botException.getMessage());
            else
                return messageBuilder.popup(botException.getMessage());
        } catch (Exception e) {
            String msg = "Что-то пошло не так ";
            logger.error(msg, e);
            if (update.hasMessage())
                return messageBuilder.send(msg + e.toString());
            else
                return messageBuilder.popup(msg + e.toString());
        }

        return messageBuilder.emptyAnswer();
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

    public String getLastCallBackId() {
        return lastCallBackId;
    }
}