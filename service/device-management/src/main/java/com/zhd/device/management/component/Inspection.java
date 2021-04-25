package com.zhd.device.management.component;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.zhd.common.utils.others.Uuid;
import com.zhd.common.utils.result.DeviceActionEnum;
import com.zhd.common.utils.result.DeviceTriggerEnum;
import com.zhd.common.utils.result.R;
import com.zhd.device.management.entity.DeviceAction;
import com.zhd.device.management.entity.DeviceInfo;
import com.zhd.device.management.entity.DeviceTrigger;
import com.zhd.device.management.service.DeviceActionService;
import com.zhd.device.management.service.DeviceInfoService;
import com.zhd.device.management.service.DeviceTriggerService;
import com.zhd.device.management.service.IsApiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class Inspection {

    @Autowired
    Token token;

    @Autowired
    IsApiService isApiService;

    @Autowired
    DeviceInfoService deviceInfoService;

    @Autowired
    DeviceActionService deviceActionService;

    @Autowired
    DeviceTriggerService deviceTriggerService;

    @Autowired
    Uuid uuid;

    public void deviceListInspection(List<String> SerialList) {
        for (String serial : SerialList){
            //action
            JSONObject object = isApiService.inspectDevice(token.getAccessToken(), serial);
            List<DeviceAction> deviceActions = Obj2DeviceActions(object, serial);
            List<DeviceAction> deviceActionFromDB = deviceActionService.list();
            for (DeviceAction action : deviceActions) {
                if (compareAction(action, deviceActionFromDB)){
                    deviceActionService.save(action);
                }
            }

            //trigger
            List<DeviceTrigger> deviceTriggers = Obj2DeviceTriggers(object, serial);
            List<DeviceTrigger> deviceTriggerFromDB = deviceTriggerService.list();
            for (DeviceTrigger trigger : deviceTriggers) {
                if (compareTrigger(trigger, deviceTriggerFromDB)){
                    deviceTriggerService.save(trigger);
                }
            }

            //online status of device
            object = isApiService.getDeviceSingle(token.getAccessToken(), serial);
            DeviceInfo deviceInfo = new DeviceInfo();
            deviceInfo.setStatus(object.getJSONObject("data").getInteger("status"));
            deviceInfoService.update(deviceInfo, Wrappers.<DeviceInfo>lambdaUpdate().eq(DeviceInfo::getSerial, serial));
        }
    }

    private boolean compareTrigger(DeviceTrigger deviceTrigger, List<DeviceTrigger> deviceTriggerList){
        for (DeviceTrigger trigger : deviceTriggerList) {
            if (trigger.getSerial().equals(deviceTrigger.getSerial()) && trigger.getTriggerSource().equals(deviceTrigger.getTriggerSource())){
                return false;
            }
        }
        return true;
    }

    private boolean compareAction(DeviceAction deviceAction, List<DeviceAction> deviceActionList){
        for (DeviceAction action : deviceActionList) {
            if (action.getSerial().equals(deviceAction.getSerial()) && action.getResponseAction().equals(deviceAction.getResponseAction())){
                return false;
            }
        }
        return true;
    }

    private List<DeviceTrigger> Obj2DeviceTriggers(JSONObject object, String deviceSerial){
        List<DeviceTrigger> list = new ArrayList<>();
        DeviceTrigger trigger = new DeviceTrigger();
        trigger.setSerial(deviceSerial);
        trigger.setId(uuid.getUuid());
        trigger.setTriggerSource(DeviceTriggerEnum.MOVE_DETECT.getCode());
        list.add(trigger);
        return list;
    }

    private List<DeviceAction> Obj2DeviceActions(JSONObject object, String deviceSerial){
        List<DeviceAction> list= new ArrayList<>();
        if ("1".equals(object.getJSONObject("data").getString("support_capture"))){
            DeviceAction action = new DeviceAction();
            action.setSerial(deviceSerial);
            action.setId(uuid.getUuid());
            action.setResponseAction(DeviceActionEnum.SUPPORT_CAPTURE.getCode());
            list.add(action);
        }
        if ("1".equals(object.getJSONObject("data").getString("support_alarm_voice"))) {
            DeviceAction action = new DeviceAction();
            action.setSerial(deviceSerial);
            action.setId(uuid.getUuid());
            action.setResponseAction(DeviceActionEnum.ALARM_VOICE.getCode());
            list.add(action);
        }
        return list;
    }
}
