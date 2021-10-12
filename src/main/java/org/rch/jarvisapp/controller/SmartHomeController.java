package org.rch.jarvisapp.controller;

import org.json.JSONArray;
import org.rch.jarvisapp.bot.MessageBuilder;
import org.rch.jarvisapp.bot.ui.Tile;
import org.rch.jarvisapp.bot.ui.TilePool;
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
    @Autowired
    MessageBuilder messageBuilder;

    @Autowired
    TilePool tilePool;

    @PostMapping(value = "/alert")
    public ResponseEntity<?> alert(@RequestBody String data){
       if (messageBuilder.sendAsync(data) < 0)
           return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping(value = "/changeStatus")
    public ResponseEntity<?> changeStatus(@RequestBody String data){
        System.out.println("Обратная связь " + data);
        for (Object obj : new JSONArray(data)){
            try {
                Integer messageId = Integer.parseInt(obj.toString());
                Tile tile = tilePool.getTileWith(messageId);
                if (tile != null)
                    tile.refresh().publish();
                else
                    tilePool.clearFeedBack(messageId);
            } catch (NumberFormatException ignored){}
        }

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
