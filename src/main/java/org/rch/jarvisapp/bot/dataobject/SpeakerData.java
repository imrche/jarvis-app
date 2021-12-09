package org.rch.jarvisapp.bot.dataobject;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.rch.jarvisapp.smarthome.devices.Device;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SpeakerData extends DataObject{

    private static class SpeakerElement extends DTOElement {
        @JsonInclude(JsonInclude.Include.NON_NULL)
        public String typeCommand;

        public List<Map<String,String>> data = new ArrayList<>();

        public SpeakerElement() {}

        public SpeakerElement(Integer id) {
            super(id);
        }

        public SpeakerElement(int id, String typeCommand) {
            super(id);
            this.typeCommand = typeCommand;
        }
    }

    public enum Command{
        setText,
        setVolume,
        play,
        stop,
        next,
        prev,
        forward,
        backward,
        rewind,
        like,
        dislike
    }

    public SpeakerData(String json, Class<?> clazz) throws JsonProcessingException {
        super(json,clazz);
    }

    public SpeakerData(String json) throws JsonProcessingException {
        super(json, SpeakerElement.class);
    }

    public SpeakerData() {}

    public SpeakerData addSpeaker(Device device, String typeCommand){
        SpeakerElement e = (SpeakerElement) addDevice(device, SpeakerElement.class);
        e.typeCommand = typeCommand;
        return this;
    }

    public SpeakerData addSpeaker(Device device){
        SpeakerElement e = (SpeakerElement) addDevice(device, SpeakerElement.class);
        return this;
    }

    public void addCommand(Device device, Command command, String value){
        SpeakerElement element = getDevice(device);
        for (Map<String,String> cmd : element.data){
            String curCmd = cmd.get("command");
            if (curCmd.equals(command.name())) {
                if (value != null)
                    cmd.put("value", value);
                return;
            }
        }

        Map<String,String> map = new HashMap<>();
        map.put("command", command.name());
        if (value != null)
            map.put("value", value);
        element.data.add(map);
    }

    public void addCommand(Device device, Command command){
        addCommand(device,command,null);
    }

    public void setValueFor(Device device, Command command, String value){
        SpeakerElement element = getDevice(device);
        for (Map<String,String> cmd : element.data){
            String curCmd = cmd.get("command");
            if (curCmd.equals(command.name())) {
                cmd.put("value", value);
                return;
            }
        }
    }

    public void setTypeCommand(SpeakerElement element, String value){
        element.typeCommand = value;
    }

    public SpeakerElement getDevice(Device device){
        DTOElement element = getDeviceDTOElement(device);
        if (element != null)
            return (SpeakerElement) element;

        return null;//todo

    }

    public int getDeviceCount(){
        return data.size();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof SpeakerData){
            SpeakerData sd = (SpeakerData) obj;
            if (sd.getDeviceCount() != getDeviceCount())
                return false;

            return sd.getListElements().equals(getListElements());
        } else
            return false;
    }
}