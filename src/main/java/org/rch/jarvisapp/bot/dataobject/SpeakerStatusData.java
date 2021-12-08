package org.rch.jarvisapp.bot.dataobject;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.rch.jarvisapp.smarthome.devices.Device;

public class SpeakerStatusData extends DataObject{

    public static class SpeakerElement extends DTOElement {
        @JsonInclude(JsonInclude.Include.NON_NULL)
        public String state;

        public Integer volume;
        public String type;//todo возможно бесполезное
        public String artist;
        public String trackID;
        public Integer trackProgress;
        public String title;
        public Integer trackDuration;
        public String coverURL;

        public SpeakerElement() {}

        public SpeakerElement(Integer id) {
            super(id);
        }
    }

    public SpeakerStatusData(String json, Class<?> clazz) throws JsonProcessingException {
        super(json,clazz);
    }

    public SpeakerStatusData(String json) throws JsonProcessingException {
        super(json, SpeakerElement.class);
    }

    public SpeakerStatusData() {}

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
        if (obj instanceof SpeakerStatusData){
            SpeakerStatusData sd = (SpeakerStatusData) obj;
            if (sd.getDeviceCount() != getDeviceCount())
                return false;

            return sd.getListElements().equals(getListElements());
        } else
            return false;
    }
}