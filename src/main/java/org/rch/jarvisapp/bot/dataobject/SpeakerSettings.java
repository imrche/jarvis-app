package org.rch.jarvisapp.bot.dataobject;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.rch.jarvisapp.smarthome.devices.Speaker;

import java.util.HashMap;
import java.util.Map;

public class SpeakerSettings extends DataObject{

    public static class SpeakerElement extends DTOElement {
        @JsonInclude(JsonInclude.Include.NON_NULL)
        public String[] voices;
        @JsonInclude(JsonInclude.Include.NON_NULL)
        public String[] effects;
        @JsonInclude(JsonInclude.Include.NON_NULL)
        public Integer coverSize;
        @JsonInclude(JsonInclude.Include.NON_NULL)
        public Integer volumeTTS;
        @JsonInclude(JsonInclude.Include.NON_NULL)
        public String voiceTTS;
        @JsonInclude(JsonInclude.Include.NON_NULL)
        public String effectTTS;
        @JsonInclude(JsonInclude.Include.NON_NULL)
        public String[] recentMessage;
        @JsonInclude(JsonInclude.Include.NON_NULL)
        public String[] patternMessage;

        public SpeakerElement() {}
        public SpeakerElement(Integer id) {
            super(id);
        }
    }

    public enum Settings{
        voices,
        effects,
        coverSize,
        volumeTTS,
        voiceTTS,
        effectTTS,
        patternMessage,
        recentMessage;
    }

    public SpeakerSettings(String json, Class<?> clazz) throws JsonProcessingException {
        super(json,clazz);
    }

    public SpeakerSettings(String json) throws JsonProcessingException {
        super(json, SpeakerElement.class);
    }

    public SpeakerSettings() {}

    public SpeakerSettings needCommon(){
        data.add(new SpeakerElement(0));
        return this;
    }

    public Map<Settings, Object> getCommonSettings(){
        Map<Settings, Object> result = new HashMap<>();
        SpeakerElement element = (SpeakerElement)getDeviceDTOElement(0);
        if (element != null){
            result.put(Settings.voices, element.voices);
            result.put(Settings.effects, element.effects);
            result.put(Settings.recentMessage, element.recentMessage);
            result.put(Settings.patternMessage, element.patternMessage);
            result.put(Settings.coverSize, element.coverSize);
        }

        return result;
    }

    public Map<Settings, Object> getSettings(Speaker speaker){
        Map<Settings, Object> result = new HashMap<>();
        SpeakerElement element = (SpeakerElement)getDeviceDTOElement(speaker.getId());
        if (element != null){
            result.put(Settings.volumeTTS, element.volumeTTS);
            result.put(Settings.voiceTTS, element.voiceTTS);
            result.put(Settings.effectTTS, element.effectTTS);
        }

        return result;
    }

    public void setSettings(Speaker speaker, Settings setting, String value){
        SpeakerElement element = (SpeakerElement) getDeviceDTOElement(speaker.getId());
        if (element == null)
            element = addSpeaker(speaker);

        switch(setting){
            case volumeTTS : element.volumeTTS = Integer.parseInt(value); break;
            case effectTTS : element.effectTTS = value; break;
            case voiceTTS : element.voiceTTS = value; break;
        }
    }

    public void setCommonSettings(Settings setting, String value){
        SpeakerElement element = (SpeakerElement)getDeviceDTOElement(0);
        if (element == null) {
            needCommon();
            element = (SpeakerElement)getDeviceDTOElement(0);
        }

        if (setting == Settings.coverSize)
            element.coverSize = Integer.parseInt(value);
    }

    public SpeakerElement addSpeaker(Speaker speaker){
        return (SpeakerElement)addDevice(speaker, SpeakerElement.class);
    }

    public int getDeviceCount(){
        return data.size();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof SpeakerSettings){
            SpeakerSettings sd = (SpeakerSettings) obj;
            if (sd.getDeviceCount() != getDeviceCount())
                return false;

            return sd.getListElements().equals(getListElements());
        } else
            return false;
    }
}