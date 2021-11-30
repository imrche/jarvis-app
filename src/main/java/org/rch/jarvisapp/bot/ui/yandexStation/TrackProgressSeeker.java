package org.rch.jarvisapp.bot.ui.yandexStation;

import lombok.SneakyThrows;
import org.rch.jarvisapp.bot.ui.keyboard.speaker.PlayerKeyboard;

import java.util.Map;
import java.util.TreeMap;

public class TrackProgressSeeker extends Thread{
    PlayerKeyboard kb;
    private Integer trackLength = 0;
    private Integer curPosition = 0;
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
            for (int i = 0; i < kb.volumeLineScaleVariants.length; i++)
                switchMap.put(i,trackLength * kb.volumeLineScaleVariants[i] / 100);
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
        curPosition = position;
        for (Map.Entry<Integer, Integer> entry : switchMap.entrySet()) {
            if (entry.getValue() > curPosition){
                curSwitchPosition = entry.getKey();
                secIsLeft = entry.getValue() - curPosition;
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