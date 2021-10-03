package org.rch.jarvisapp.bot.actions.additional;

import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import org.apache.logging.log4j.util.Strings;
import org.rch.jarvisapp.bot.actions.Action;
import org.rch.jarvisapp.bot.actions.RunnableByPlace;
import org.rch.jarvisapp.bot.enums.CommonCallBack;
import org.rch.jarvisapp.bot.ui.Tile;
import org.rch.jarvisapp.bot.ui.button.Button;
import org.rch.jarvisapp.bot.ui.keyboard.KeyBoard;
import org.rch.jarvisapp.bot.ui.keyboard.LightKeyBoard;
import org.rch.jarvisapp.smarthome.areas.Place;

@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)

public class ShowAdditionalPropertiesAction implements Action, RunnableByPlace {
    Class<?> classFrom;
    String place;

    public ShowAdditionalPropertiesAction(String place) {
        this.place = place;
    }

    public ShowAdditionalPropertiesAction() {}

    @Override
    public void run(Tile tile) {
        KeyBoard kb = new KeyBoard();
        kb.addButton(1, new Button("Таймеры", new TimerProperties(place)));
        kb.addButton(1, new Button("2", CommonCallBack.empty.name()));

        tile.update()
                .setCaption("Допничек")
                .setKeyboard(kb);
    }

    @Override
    public void setPlace(String place) {
        this.place = place;
    }


    @Override
    public int hashCode() {
        return Strings.isEmpty(place) ? 0 : place.hashCode() + this.getClass().hashCode();//todo
    }
}
