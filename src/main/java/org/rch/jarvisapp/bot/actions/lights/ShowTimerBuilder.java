package org.rch.jarvisapp.bot.actions.lights;

import org.rch.jarvisapp.bot.actions.Action;
import org.rch.jarvisapp.bot.dataobject.SwitcherData;
import org.rch.jarvisapp.bot.enums.CommonCallBack;
import org.rch.jarvisapp.bot.enums.ParseMode;
import org.rch.jarvisapp.bot.exceptions.HomeApiWrongResponseData;
import org.rch.jarvisapp.bot.ui.Tile;
import org.rch.jarvisapp.bot.ui.button.Button;
import org.rch.jarvisapp.bot.ui.button.func_interface.TileCaptionUpdater;
import org.rch.jarvisapp.bot.ui.keyboard.KeyBoard;
import org.rch.jarvisapp.bot.ui.keyboard.LightParamsKeyBoard;
import org.rch.jarvisapp.bot.ui.keyboard.TimerSetterKeyBoard;
import org.rch.jarvisapp.smarthome.devices.Device;

public class ShowTimerBuilder implements Action {
    private static final String description = "Таймеры";
    TileCaptionUpdater tileCaptionUpdater = this::getCaption;

    SwitcherData patternCD;
    Device device;

    String caption = description;
    public void setCaption(String caption){
        this.caption = caption;
    }

    private String getCaption(){
        return caption;
    }

    public SwitcherData getPatternCD() {
        return patternCD;
    }

    public Device getDevice() {
        return device;
    }

    public ShowTimerBuilder(Device device) {
        patternCD = new SwitcherData();//todo надо сюда сразу забрасывать шаблон (клон)
        patternCD.addSwitcher(device);
        this.device = device;
    }

    @Override
    public void run(Tile tile) throws HomeApiWrongResponseData {
        KeyBoard kbMain = new TimerSetterKeyBoard(this);

        tile.update()
                .setTileCaptionUpdater(tileCaptionUpdater)
                .setParseMode(ParseMode.Markdown)
               // .setCaption(description)
                .setKeyboard(kbMain);
    }

    @Override
    public int hashCode() {
        return device.hashCode() + this.getClass().hashCode();
    }
}
