package com.zhd.device.management.controller;


import com.alibaba.fastjson.JSONObject;
import com.zhd.common.utils.result.R;
import com.zhd.device.management.entity.DeviceAction;
import com.zhd.device.management.entity.DeviceTrigger;
import com.zhd.device.management.service.DeviceActionService;
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
@RequestMapping("/management/device-action")
public class DeviceActionController {

    @Autowired
    DeviceActionService deviceActionService;

    @GetMapping("/action/list")
    public R getDeviceAction() {
        List<DeviceAction> list = deviceActionService.list();
        List<JSONObject> jsonObjects = new ArrayList<>();
        Map<String, ArrayList<Integer>> map= new HashMap<>();
        Set<String> serialSet = new HashSet<>();
        for (DeviceAction action : list) {
            if (serialSet.contains(action.getSerial())){
                continue;
            }
            serialSet.add(action.getSerial());
            map.put(action.getSerial(), new ArrayList<>());
        }
        for (DeviceAction action : list) {
            map.get(action.getSerial()).add(action.getResponseAction());
        }
        for (Map.Entry<String, ArrayList<Integer>> entry : map.entrySet()){
            JSONObject json = new JSONObject();
            json.put("serial", entry.getKey());
            json.put("action", entry.getValue());
            jsonObjects.add(json);
        }
        return R.ok().data("list", jsonObjects);
    }
}

