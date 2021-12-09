package org.rch.jarvisapp.bot.ui.keyboard.speaker;

import org.rch.jarvisapp.bot.ui.button.Button;
import org.rch.jarvisapp.bot.ui.button.func_interface.VolumeSetter;
import org.rch.jarvisapp.bot.ui.yandexStation.PlayerSymbols;

import java.util.ArrayList;
import java.util.List;

public class ProgressLine {
    private final List<Button> progress = new ArrayList<>();
    private final char[] progressLine = "PROGRESS".toCharArray();
    public static final int[] progressScale = {0,13,26,39,52,65,78,91};

    public ProgressLine(VolumeSetter setter){
        for (int j = 0; j < progressLine.length; j++)
            progress.add(new Button(String.valueOf(progressLine[j]), setter.set(j)));
    }

    public void setProgress(Integer position){
        for (int i = 0; i < progress.size(); i++)
            this.progress.get(i).setCaption(String.valueOf(progressLine[i]));

        this.progress.get(position).setCaption(PlayerSymbols.HANDLE.toString());
    }

    public void setProgressWithLevel(Integer progress, Integer fullDuration){
        setProgress(getProgressPosition(progress,fullDuration));
    }

    public static Integer getProgressPosition(Integer progress, Integer fullDuration){
        int result = -1;

        if (fullDuration > 0) {
            int percent = Math.round(progress.floatValue() / fullDuration * 100);
            for (int i = 0; i < progressScale.length; i++){
                if (progressScale[i] > percent)
                    break;
                result = i;
            }
        }
        return result;
    }

    public static Integer getProgressLevel(Integer progress){
        try {
            return progressScale[progress];
        } catch (IndexOutOfBoundsException e){
            return 0;
        }
    }

    public List<Button> getProgressButton() {
        return progress;
    }
}