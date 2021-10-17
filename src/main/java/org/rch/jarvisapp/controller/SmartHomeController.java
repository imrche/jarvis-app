package org.rch.jarvisapp.controller;

import org.json.JSONArray;
import org.rch.jarvisapp.bot.MessageBuilder;
import org.rch.jarvisapp.bot.exceptions.HomeApiWrongResponseData;
import org.rch.jarvisapp.bot.ui.Tile;
import org.rch.jarvisapp.bot.ui.TilePool;
import org.rch.jarvisapp.smarthome.init.HomeInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping("/SH")
public class SmartHomeController {
    Logger logger = LoggerFactory.getLogger(SmartHomeController.class);

    @Autowired
    MessageBuilder messageBuilder;

    @Autowired
    TilePool tilePool;

    @Autowired
    HomeInitializer initializer;

    @PostMapping(value = "/alert")
    public ResponseEntity<?> alert(@RequestBody String data){
       if (messageBuilder.sendAsync(data) < 0)
           return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping(value = "/changeStatus")
    public ResponseEntity<?> changeStatus(@RequestBody String data) throws HomeApiWrongResponseData {
        logger.debug("Обратная связь "+ data);

        for (Object obj : new JSONArray(data)){
            try {
                Integer messageId = Integer.parseInt(obj.toString());
                Tile tile = tilePool.getTileWith(messageId);
                if (tile != null)
                    tile.refresh().publish();
                else
                    tilePool.clearFeedBack(messageId);
            } catch (NumberFormatException e){
                logger.error("Ошибка при получении списка id сообщений для обновления : " + obj.toString() + " не число!");
            }
        }

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping(value = "/reinit")
    public ResponseEntity<?> reInitHomeData(@RequestBody String data) {
        logger.debug("Переинициализация данных о доме");

        try {
            initializer.init();
        } catch (Exception e) {
            logger.error("Ошибка при переинициализации данных: " + e.getMessage(),e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
