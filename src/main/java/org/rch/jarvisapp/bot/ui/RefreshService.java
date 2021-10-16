package org.rch.jarvisapp.bot.ui;

import lombok.SneakyThrows;
import org.springframework.stereotype.Service;

@Service
public class RefreshService extends Thread{
    Tile[] tiles;

    @SneakyThrows
    @Override
    public void run() {
        super.run();
        while (true){
            for (Tile tile : tiles)
                tile.refresh();
        }
    }
}
