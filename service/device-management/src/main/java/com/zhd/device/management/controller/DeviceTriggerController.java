package com.zhd.device.management.controller;


import com.alibaba.fastjson.JSONObject;
import com.zhd.common.utils.result.R;
import com.zhd.device.management.component.LinkageCache;
import com.zhd.device.management.entity.DeviceTrigger;
import com.zhd.device.management.service.DeviceTriggerService;
import io.swagger.models.auth.In;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import java.util.*;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author zhd
 * @since 2021-04-13
 */
@RestController
@RequestMapping("/management/device-trigger")
public class DeviceTriggerController {

    @Autowired
    DeviceTriggerService deviceTriggerService;

    @GetMapping("/trigger/list")
    public R getDeviceTrigger() {
        List<DeviceTrigger> list = deviceTriggerService.list();
        List<JSONObject> jsonObjects = new ArrayList<>();
        Map<String, ArrayList<Integer>> map= new HashMap<>();
        Set<String> serialSet = new HashSet<>();
        for (DeviceTrigger trigger : list) {
            if (serialSet.contains(trigger.getSerial())){
                continue;
            }
            serialSet.add(trigger.getSerial());
            map.put(trigger.getSerial(), new ArrayList<>());
        }
        for (DeviceTrigger trigger : list) {
            map.get(trigger.getSerial()).add(trigger.getTriggerSource());
        }
        for (Map.Entry<String, ArrayList<Integer>> entry : map.entrySet()){
            JSONObject json = new JSONObject();
            json.put("serial", entry.getKey());
            json.put("trigger", entry.getValue());
            jsonObjects.add(json);
        }
        return R.ok().data("list", jsonObjects);
    }

}

