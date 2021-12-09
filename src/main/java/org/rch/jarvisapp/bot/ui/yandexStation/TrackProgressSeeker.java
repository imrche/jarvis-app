package org.rch.jarvisapp.bot.ui.yandexStation;

import lombok.SneakyThrows;
import org.rch.jarvisapp.bot.ui.keyboard.speaker.PlayerKeyboard;
import org.rch.jarvisapp.bot.ui.keyboard.speaker.ProgressLine;

import java.util.Map;
import java.util.TreeMap;

public class TrackProgressSeeker extends Thread{
    PlayerKeyboard kb;
    private Integer trackLength = 0;
    int curSwitchPosition = 0;
    int secIsLeft = 0;

    Map<Integer, Integer> switchMap = new TreeMap<>();

    public TrackProgressSeeker(PlayerKeyboard kb) {
        this.kb = kb;
    }

    public void setTrackLength(Integer trackLength) {
        if (!this.trackLength.equals(trackLength)) {
            this.trackLength = trackLength;
            switchMap.clear();
            for (int i = 0; i < ProgressLine.progressScale.length; i++)
                switchMap.put(i,trackLength * ProgressLine.progressScale[i] / 100);
        }
    }

    public void stopTrack() {
        //System.out.println("stop track");
        secIsLeft = -1;
    }

    public void startTrack(Integer position) {
        if (State.NEW == getState())
            start();
        //System.out.println(this.getState().name());
        for (Map.Entry<Integer, Integer> entry : switchMap.entrySet()) {
            if (entry.getValue() > position){
                curSwitchPosition = entry.getKey();
                secIsLeft = entry.getValue() - position;
                //System.out.println("start with params: curSwitchPosition - " + curSwitchPosition + " secIsLeft - " + secIsLeft);
                break;
            }
        }
    }

    @SneakyThrows
    @Override
    public void run() {
        while (!isInterrupted()) {
            if (secIsLeft > 0)
                secIsLeft--;

            if (secIsLeft == 0) {
                kb.setProgress(curSwitchPosition);
                kb.getTile().publish();

                int prevPosition = switchMap.get(curSwitchPosition);
                if (++curSwitchPosition > switchMap.size() - 1)
                    stopTrack();
                else
                    secIsLeft = switchMap.get(curSwitchPosition) - prevPosition;
            }
            Thread.sleep(1000);
        }
    }
}