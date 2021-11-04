package org.rch.jarvisapp.bot.actions.scenario;

import org.rch.jarvisapp.AppContextHolder;
import org.rch.jarvisapp.bot.actions.Action;
import org.rch.jarvisapp.bot.actions.lights.ShowLight;
import org.rch.jarvisapp.bot.enums.CommonCallBack;
import org.rch.jarvisapp.bot.exceptions.HomeApiWrongResponseData;
import org.rch.jarvisapp.bot.ui.Tile;
import org.rch.jarvisapp.bot.ui.button.Button;
import org.rch.jarvisapp.bot.ui.button.LightButton;
import org.rch.jarvisapp.bot.ui.keyboard.KeyBoard;
import org.rch.jarvisapp.bot.ui.keyboard.LightKeyBoard;
import org.rch.jarvisapp.bot.ui.keyboard.PlaceKeyboard;
import org.rch.jarvisapp.smarthome.SmartHome;
import org.rch.jarvisapp.smarthome.areas.Place;
import org.rch.jarvisapp.smarthome.devices.Device;
import org.rch.jarvisapp.smarthome.devices.Light;
import org.rch.jarvisapp.smarthome.devices.filters.Predicates;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;

public class ShowLightsOnFloor implements Action {
    SmartHome smartHome = AppContextHolder.getSH();
    private Place place;
    List<Predicate<Device>> listPredicate = new ArrayList<>();
    {
        listPredicate.add(Predicates.isTypeOf(Light.class));
    }

    public ShowLightsOnFloor(){
        place = smartHome.getPlaceByCode("floor2");
        //this.place = place;
        //kbList.add(new PlaceKeyboard(place, ShowLight.class));
        listPredicate.add(Predicates.isPlaceLayingAll(place));
    }

    public ShowLightsOnFloor(Place place){
        place = smartHome.getPlaceByCode("floor2");
        this.place = place;
        //kbList.add(new PlaceKeyboard(place, ShowLight.class));
        listPredicate.add(Predicates.isPlaceLayingAll(place));
        //description += " - " + place.getName();
       // fillLightKeyboard();




    }

    @Override
    public void run(Tile tile) throws HomeApiWrongResponseData {
        LightKeyBoard kbLight = new LightKeyBoard();
        kbLight.hideAllAdditionalBtn();

        List<Light> lightList = smartHome.getDevicesWithFilter(listPredicate,Light.class);

        Map<Place,List<Light>> map = new HashMap<>();

        for (Light device : lightList){
            if (!map.containsKey(device.getPlacement()))
                map.put(device.getPlacement(),new ArrayList<>());
            map.get(device.getPlacement()).add(device);
        }


        int row = 1;
        for (Place pl :  map.keySet()){
            kbLight.addButton(row++,new Button(pl.getFormattedName(), CommonCallBack.empty.name()));
            for(Light dev: map.get(pl))
                kbLight.addButton(row++, new LightButton(dev));
        }

  //      for (Light device : lightList)
//            kbLight.addButton(device.getRow(), new LightButton(device));





     //   kbLight.refresh();

    //    for (Button btn : kbLight.getButtonsList())
     //       btn.setVisible(((LightButton)btn).getStatus());

        tile.update()
                .setCaption("Cвет на этаже")
                .setKeyboard(kbLight);

    }
}
