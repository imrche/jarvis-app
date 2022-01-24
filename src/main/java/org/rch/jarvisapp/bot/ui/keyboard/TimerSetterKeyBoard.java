package org.rch.jarvisapp.bot.ui.keyboard;

import org.rch.jarvisapp.AppContextHolder;
import org.rch.jarvisapp.bot.MessageBuilder;
import org.rch.jarvisapp.bot.actions.ChoiceAction;
import org.rch.jarvisapp.bot.actions.lights.ShowTimerBuilder;
import org.rch.jarvisapp.bot.dataobject.SwitcherData;
import org.rch.jarvisapp.bot.enums.CommonCallBack;
import org.rch.jarvisapp.bot.exceptions.HomeApiWrongResponseData;
import org.rch.jarvisapp.bot.ui.button.Button;
import org.rch.jarvisapp.bot.ui.timerStuff.Actions;
import org.rch.jarvisapp.bot.ui.timerStuff.TimerData;
import org.rch.jarvisapp.bot.ui.timerStuff.Units;
import org.rch.jarvisapp.bot.ui.timerStuff.WrongDataForTimerException;
import org.rch.jarvisapp.bot.utils.MD;

import java.util.Arrays;
import java.util.List;

public class TimerSetterKeyBoard extends KeyBoard {
    MessageBuilder builder = AppContextHolder.getMessageBuilder();
    ShowTimerBuilder showTimerBuilder;
    TimerData data = new TimerData();

    Button addBtn           = new Button("[ДОБАВИТЬ]",          s -> addTimer());
    Button saveBtn          = new Button("[СОХРАНИТЬ]",         s -> saveTimer());
    Button editBtn          = new Button("[ИЗМЕНИТЬ]",          s -> showEditor());
    Button deleteBtn        = new Button("[УДАЛИТЬ]",           s -> deleteTimer());
    Button actionBtn        = new Button("..выбрать действие..",new ChoiceAction(Actions::getDescriptionList, this::changeAction));
    Button prepDurationBtn  = new Button("на",                  CommonCallBack.empty.name());
    Button durationValue    = new Button("..время..",           new ChoiceAction(() -> data.getDuration().getUnit().getOptions(), this::changeDurationValue));
    Button durationUnits    = new Button("МИНУТ",               new ChoiceAction(Units::getDescriptionList, this::changeDurationUnit));
    Button prepDelayBtn     = new Button("через",               CommonCallBack.empty.name());
    Button delayValue       = new Button("..время..",           new ChoiceAction(() -> data.getDelay().getUnit().getOptions(), this::changeDelayValue));
    Button delayUnits       = new Button("МИНУТ",               new ChoiceAction(Units::getDescriptionList, this::changeDelayUnit));

    List<Button> timerSettingsButtons = Arrays.asList(actionBtn, prepDurationBtn, durationValue, durationUnits, prepDelayBtn, delayValue, delayUnits);

    Status status;
    enum Status{
        SHOWN, HIDDEN, SPECIFIED, UNSPECIFIED
    }

    @Override
    public void refresh() throws HomeApiWrongResponseData {
        if (status == Status.SHOWN)
            return;

        System.out.println("обновление таймера");
        SwitcherData patternSD = new SwitcherData();
        patternSD.addSwitcher(showTimerBuilder.getDevice());

        SwitcherData responseSD = AppContextHolder.getApi().getStatusLight(patternSD);
        data.setTimer(responseSD.getTimer(showTimerBuilder.getDevice()));

        //todo вынести в функцию
        if (data.isComplete()) {
            status = Status.SPECIFIED;
            hideEditor();
            showTimerBuilder.setCaption(buildTimerCaption());
            editBtn.setVisible(true);
            deleteBtn.setVisible(true);
        }
    }

    private void changeAction(String newAction){
        try {
            data.setAction(newAction);
            actionBtn.setCaption(data.getAction().getDescription());
        } catch (WrongDataForTimerException e) {
            builder.popupAsync(e.getMessage());
        }
        updateKB();
    }

    private void updateKB(){
        if (data.isComplete() && !saveBtn.isVisible())
            saveBtn.setVisible(true);
    }

    private void changeDurationValue(String newForTime){
        data.getDuration().setValue(Integer.valueOf(newForTime));
        durationValue.setCaption(data.getDuration().getValue());

        updateKB();
    }

    private void changeDurationUnit(String newForTimeUnits){
        try {
            data.getDuration().setUnit(newForTimeUnits);
            durationUnits.setCaption(data.getDuration().getUnit().getDescription());
        } catch (WrongDataForTimerException e) {
            builder.popupAsync(e.getMessage());
        }
    }

    private void changeDelayValue(String newInTime){
        data.getDelay().setValue(Integer.valueOf(newInTime));
        delayValue.setCaption(data.getDelay().getValue());

        updateKB();
    }

    private void changeDelayUnit(String newInTimeUnits){
        try {
            data.getDelay().setUnit(newInTimeUnits);
            delayUnits.setCaption(data.getDelay().getUnit().getDescription());
        } catch (WrongDataForTimerException e) {
            builder.popupAsync(e.getMessage());
        }
    }

    private void hideAll(){
        for (Button button : getButtonsList())
            button.setVisible(false);
    }

    private void setNoneTimer(){
        hideAll();
        status = Status.UNSPECIFIED;
        addBtn.setVisible(true);
        showTimerBuilder.setCaption("НЕ УСТАНОВЛЕНО");
    }

    private void addTimer(){
        if (!Status.SHOWN.equals(status))
            showEditor();
    }

    private void saveTimer(){
        if (data.isComplete()) {
            status = Status.SPECIFIED;
            hideEditor();
            showTimerBuilder.setCaption(buildTimerCaption());
            editBtn.setVisible(true);
            deleteBtn.setVisible(true);
            SwitcherData sd = showTimerBuilder.getPatternCD().getClone();//todo где-то тут прикол с неклонированным DTO
            sd.setTimer(showTimerBuilder.getDevice(),data.getAction().getValue(), data.getDelay().toSeconds(),data.getDuration().toSeconds());
            AppContextHolder.getApi().setStatusDevice(sd);
        } else
            AppContextHolder.getMessageBuilder().popupAsync("Не все данные для таймера заполнены!");
    }

    private void showEditor(){
        status = Status.SHOWN;
        hideAll();
        for (Button button : timerSettingsButtons)
            button.setVisible(true);

        if (data.getAction() != null)
            actionBtn.setCaption(data.getAction().getDescription());
        if (data.getDuration().isSet())
            durationValue.setCaption(data.getDuration().getValue());
        if (data.getDelay().isSet())
            delayValue.setCaption(data.getDelay().getValue());

        updateKB();
    }

    private void hideEditor(){
        status = Status.HIDDEN;
        hideAll();
        editBtn.setVisible(true);
        deleteBtn.setVisible(true);
    }

    private void deleteTimer(){
        data.delete();
        setNoneTimer();
        AppContextHolder.getApi().setStatusDevice(showTimerBuilder.getPatternCD());
    }

    private String buildTimerCaption(){
        return MD.italic(data.getAction().getDescription()) + "\n" +
                (data.getDuration().isSet() ? ("на " + MD.bold(data.getDuration().getValue()) + " " + data.getDuration().getUnit().getDescription().toLowerCase() + "\n") : "")  +
                (data.getDelay().isSet() ? ("через " + MD.bold(data.getDelay().getValue()) + " " + data.getDelay().getUnit().getDescription().toLowerCase()) : "");
    }

    public TimerSetterKeyBoard(ShowTimerBuilder showTimerBuilder) {
        this.showTimerBuilder = showTimerBuilder;

        int i = 1;

        addButton(i, actionBtn);
        i++;
        addButton(i, prepDurationBtn);
        addButton(i, durationValue);
        addButton(i, durationUnits);

        i++;
        addButton(i, prepDelayBtn);
        addButton(i, delayValue);
        addButton(i, delayUnits);

        i++;
        addButton(i++, addBtn);
        addButton(i, saveBtn);
        addButton(i, editBtn);
        addButton(i, deleteBtn);

        setNoneTimer();//todo выбор между Show, если уже установлен
    }
}