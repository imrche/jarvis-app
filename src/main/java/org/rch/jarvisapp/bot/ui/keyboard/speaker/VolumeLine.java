package org.rch.jarvisapp.bot.ui.keyboard.speaker;

import org.rch.jarvisapp.bot.ui.button.Button;
import org.rch.jarvisapp.bot.ui.button.func_interface.VolumeSetter;
import org.rch.jarvisapp.bot.ui.yandexStation.PlayerSymbols;

import java.util.ArrayList;
import java.util.List;

public class VolumeLine {
    private final List<Button> volume = new ArrayList<>();
    private final char[] volumeLine = "-VOLUME+".toCharArray();
    public static final int[] volumeScale = {0,10,30,40,60,70,90,100};

    public VolumeLine(VolumeSetter setter){
        for (int j = 0; j < volumeLine.length; j++)
            volume.add(new Button(String.valueOf(volumeLine[j]), setter.set(j)));
    }

    public void setVolume(Integer position){
        for (int i = 0; i < this.volume.size(); i++)
            this.volume.get(i).setCaption(String.valueOf(volumeLine[i]));

        this.volume.get(position).setCaption(PlayerSymbols.HANDLE.toString());
    }

    public void setVolumeWithLevel(Integer level){
        setVolume(getVolumePosition(level));
    }

    public static Integer getVolumePosition(Integer volume){
        for(int i = 0; i < volumeScale.length; i++){
            if (volumeScale[i] == volume)
                return i;
            else if (volumeScale[i] > volume)
                if (i == 0)
                    return 1;
                else
                    return (volumeScale[i] - volume) < (volume - volumeScale[i - 1]) ? i : i - 1;
        }
        return 2;
    }

    public static Integer getVolumeLevel(Integer volume){
        try {
            return volumeScale[volume];
        } catch (IndexOutOfBoundsException e){
            return 20;
        }
    }

    public List<Button> getVolumeButton() {
        return volume;
    }
}