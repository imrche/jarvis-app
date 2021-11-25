package org.rch.jarvisapp.bot.ui.keyboard.speaker;

import org.rch.jarvisapp.bot.actions.TextInputSupportable;
import org.rch.jarvisapp.bot.actions.speaker.Volumable;
import org.rch.jarvisapp.bot.actions.speaker.command.SetVolumeTTS;
import org.rch.jarvisapp.bot.actions.speaker.command.SpeakerTTS;
import org.rch.jarvisapp.bot.enums.CommonCallBack;
import org.rch.jarvisapp.bot.ui.button.Button;
import org.rch.jarvisapp.bot.ui.keyboard.KeyBoard;
import org.rch.jarvisapp.smarthome.devices.Speaker;

import java.util.ArrayList;
import java.util.List;


public class TTSKeyboard extends KeyBoard implements TextInputSupportable, Volumable {

    SpeakerTTS action;
    List<Button> volume = new ArrayList<>();
    Integer curVolumeLevel;

    private Integer getPercentVolumeLevel(Integer volume){
        switch (volume){
            case 1 : return 10;
            case 2 : return 30;
            case 3 : return 50;
            case 4 : return 75;
            case 5 : return 100;
            default: return 20;
        }
    }

    public TTSKeyboard(Speaker speaker) {
        action = new SpeakerTTS(speaker);
        int i = 1;
        addButton(i++, new Button("Шаблоны", CommonCallBack.empty.name()));
        addButton(i++, new Button("Недавнее", CommonCallBack.empty.name()));
        addButton(i, new Button("vol.", CommonCallBack.empty.name()));

        for (int x = 0; x < 5; x++)
            volume.add(new Button(" ", new SetVolumeTTS(x + 1)));

        for (Button btn : volume)
            addButton(i, btn);

        setVolume(2);
    }

    @Override
    public void setVolume(Integer volume){
        curVolumeLevel = volume;
        //curVolumeLevel = Integer.parseInt(volume);
        action.setVolume(getPercentVolumeLevel(curVolumeLevel).toString());

        for (Button btn : this.volume)
            btn.setCaption("➖");

        this.volume.get(curVolumeLevel-1).setCaption("\uD83D\uDD38");
    }

    @Override
    public void ProceedTextInput(String text) {
        action.run(text);
        System.out.println(text);
    }
}