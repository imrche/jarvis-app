package org.rch.jarvisapp.bot.ui.keyboard.speaker;

import org.rch.jarvisapp.bot.actions.TextInputSupportable;
import org.rch.jarvisapp.bot.actions.speaker.player.SimplePlayerCommand;
import org.rch.jarvisapp.bot.dataobject.SpeakerData;
import org.rch.jarvisapp.bot.enums.CommonCallBack;
import org.rch.jarvisapp.bot.ui.button.Button;
import org.rch.jarvisapp.bot.ui.keyboard.KeyBoard;
import org.rch.jarvisapp.smarthome.devices.Speaker;

import java.util.ArrayList;
import java.util.List;


public class PlayerKeyboard extends KeyBoard implements TextInputSupportable {

    private Speaker speaker;

    private Button info = new Button(" ",CommonCallBack.empty.name());

    private List<Button> progressBar = new ArrayList<>();

    private List<Button> volume = new ArrayList<>();




    public PlayerKeyboard(Speaker speaker) {
        this.speaker = speaker;

        char[] progressBarLine = "PROGRESS".toCharArray();
        for (int j = 0; j < progressBarLine.length; j++){
            progressBar.add(new Button(String.valueOf(progressBarLine[j]), CommonCallBack.empty.name()));
        }

        char[] volumeLine = "-VOLUME+".toCharArray();
        for (int j = 0; j < volumeLine.length; j++){
            volume.add(new Button(String.valueOf(volumeLine[j]), CommonCallBack.empty.name()));
        }


        Integer i = 1;
        addButton(i, info);
        i++;

        for (Button btn : progressBar)
            addButton(i, btn);


/*        addButton(i, new Button("P", CommonCallBack.empty.name()));
        addButton(i, new Button("R", CommonCallBack.empty.name()));
        addButton(i, new Button("O", CommonCallBack.empty.name()));
        addButton(i, new Button("G", CommonCallBack.empty.name()));
        addButton(i, new Button("\uD83D\uDD38", CommonCallBack.empty.name()));
        addButton(i, new Button("E", CommonCallBack.empty.name()));
        addButton(i, new Button("S", CommonCallBack.empty.name()));
        addButton(i, new Button("S", CommonCallBack.empty.name()));*/

        i++;
        addButton(i, new Button("⏮", new SimplePlayerCommand(speaker, SpeakerData.Command.prev)));
        addButton(i, new Button("⏪", new SimplePlayerCommand(speaker, SpeakerData.Command.backward)));
        addButton(i, new Button("⏹", new SimplePlayerCommand(speaker, SpeakerData.Command.stop)));
        addButton(i, new Button("⏯", new SimplePlayerCommand(speaker, SpeakerData.Command.play)));
        addButton(i, new Button("⏩", new SimplePlayerCommand(speaker, SpeakerData.Command.forward)));
        addButton(i, new Button("⏭", new SimplePlayerCommand(speaker, SpeakerData.Command.next)));
        i++;
        for (Button btn : volume)
            addButton(i, btn);
/*        addButton(i, new Button("\uD83D\uDD07", CommonCallBack.empty.name()));
        addButton(i, new Button("\uD83D\uDD08", CommonCallBack.empty.name()));
        addButton(i, new Button("\uD83D\uDD09", CommonCallBack.empty.name()));
        addButton(i, new Button("\uD83D\uDD0A", CommonCallBack.empty.name()));*/
        i++;
        addButton(i, new Button("\uD83D\uDC4D", CommonCallBack.empty.name()));
        addButton(i, new Button("\uD83D\uDC4E", CommonCallBack.empty.name()));
    }

    @Override
    public void ProceedTextInput(String text) {
        System.out.println(text);
    }
}