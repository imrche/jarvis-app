package org.rch.jarvisapp.bot.ui.yandexStation;

import org.rch.jarvisapp.bot.ui.keyboard.speaker.PlayerKeyboard;
import org.rch.jarvisapp.bot.ui.keyboard.speaker.ProgressLine;

import java.util.Map;
import java.util.TreeMap;
//пока сохраню на будущее
public class TrackProgressSeekerOld extends Thread{
    //private volatile boolean isStopped = false;
    boolean isPlaying = false;
    PlayerKeyboard kb;
    private Integer trackLength = 0;
    private Integer curPosition = 0;
    private boolean status = false;

    Map<Integer, Integer> switchMap = new TreeMap<>();


    public TrackProgressSeekerOld(PlayerKeyboard kb) {
        this.kb = kb;
    }

    public void setTrackLength(Integer trackLength) {
        if (!this.trackLength.equals(trackLength)) {
            this.trackLength = trackLength;
            switchMap.clear();
            for (int i = 0; i < ProgressLine.progressScale.length; i++) {
                System.out.println(trackLength * ProgressLine.progressScale[i] / 100);
                switchMap.put(trackLength * ProgressLine.progressScale[i] / 100, i);
            }
        }
    }

    public void stopTrack() {
        System.out.println("stop track");
       if (isPlaying) {
           System.out.println("need interrupt");
           interrupt();
       }
    }

    public void startTrack(Integer position) {

        System.out.println("start on " + position);
        boolean equal = position.equals(curPosition);
        curPosition = position;

        if (!status) {
            System.out.println("start");
            super.start();
        }

        if (!isPlaying){
            System.out.println("interrupt");
            interrupt();
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        interrupt();
    }

    @Override
    public void run() {
        System.out.println("------------------ran");
        System.out.println(this.getState().name());
        status = true;
        while (status) {
            try {
                System.out.println("waiting");
                Thread.sleep(10000000000L);
                continue;
            } catch (InterruptedException e) {
                System.out.println("stop waiting");
            }

            try {
                Integer mark = curPosition;
                System.out.println("-----------" + mark);
                System.out.println(this.getState().name());
                isPlaying = true;
                for (Map.Entry<Integer, Integer> entry : switchMap.entrySet()) {
                    System.out.println(entry.getKey() + " - " + mark);
                    if (entry.getKey() < mark)
                        continue;
                    int pause = (entry.getKey() - mark) * 1000;
                    System.out.println("will be waiting for " + pause);
                    Thread.sleep(pause);

                    System.out.println("-> " + entry.getValue());
                    mark = entry.getKey();
                }
            } catch (InterruptedException e) {
                System.out.println(this.getState().name());
                System.out.println("------------------------------------------break1");
            }
            isPlaying = false;
        }

        status = false;
        System.out.println("end");
    }
}