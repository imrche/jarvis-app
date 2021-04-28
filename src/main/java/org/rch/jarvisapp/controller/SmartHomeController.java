package org.rch.jarvisapp.controller;

import org.rch.jarvisapp.bot.JarvisBot;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Controller
@RequestMapping("/SH")
public class SmartHomeController {
    @Autowired
    JarvisBot bot;

    @PostMapping(value = "/alert")
    public ResponseEntity<?> alert(@RequestBody String data){

        try {
            bot.execute(new SendMessage(bot.getChat(), data));
        } catch (TelegramApiException e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
