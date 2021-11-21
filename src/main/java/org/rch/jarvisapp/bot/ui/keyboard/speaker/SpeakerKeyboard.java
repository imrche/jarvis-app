package org.rch.jarvisapp.bot.ui.keyboard.speaker;

import org.rch.jarvisapp.bot.actions.speaker.ShowSpeakerPlayer;
import org.rch.jarvisapp.bot.actions.speaker.ShowSpeakerTTS;
import org.rch.jarvisapp.bot.enums.CommonCallBack;
import org.rch.jarvisapp.bot.ui.button.Button;
import org.rch.jarvisapp.bot.ui.keyboard.KeyBoard;
import org.rch.jarvisapp.smarthome.devices.Speaker;


public class SpeakerKeyboard extends KeyBoard {

    public SpeakerKeyboard(Speaker speaker) {
        addButton(1, new Button("Колоночка", CommonCallBack.empty.name()));
        addButton(2, new Button("\uD83D\uDCAC", new ShowSpeakerTTS(speaker)));
        addButton(2, new Button("\uD83C\uDFB6", new ShowSpeakerPlayer(speaker)));
        addButton(2, new Button("⚙", CommonCallBack.empty.name()));
    }

}