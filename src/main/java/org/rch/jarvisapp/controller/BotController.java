package org.rch.jarvisapp.controller;

import org.rch.jarvisapp.bot.JarvisBot;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Update;

@RestController
public class BotController {

    @Autowired
    JarvisBot bot;

    @RequestMapping(value = "/", method = RequestMethod.POST)
    public BotApiMethod<?> receiving(@RequestBody Update update){
        return bot.onWebhookUpdateReceived(update);
    }
}
