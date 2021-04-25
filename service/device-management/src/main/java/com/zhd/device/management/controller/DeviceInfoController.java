package com.zhd.device.management.controller;


import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.zhd.common.utils.others.Uuid;
import com.zhd.common.utils.result.R;
import com.zhd.common.utils.result.ResultCodeEnum;
import com.zhd.device.management.component.Inspection;
import com.zhd.device.management.component.Token;
import com.zhd.device.management.entity.DeviceInfo;
import com.zhd.device.management.service.DeviceInfoService;
import com.zhd.device.management.service.IsApiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author zhd
 * @since 2021-04-09
 */
@RestController
@RequestMapping("/management/device-info")
public class DeviceInfoController {

    @Autowired
    private DeviceInfoService deviceInfoService;

    @Autowired
    private IsApiService isApiService;

    @Autowired
    private Inspection inspection;

    @Autowired
    private Token token;

    @Autowired
    private Uuid uuid;

    @GetMapping("/device/list/{id}")
    public R getDeviceListByUserId(@PathVariable String id){
        Map<String,Object> columnMap = new HashMap<>();
        columnMap.put("user_id", id);
        List<DeviceInfo> list = deviceInfoService.listByMap(columnMap);
        return R.ok().data("list", list);
    }

    @PostMapping("/device/add")
    public R addDevice(@RequestBody Map<String, String> map){
        JSONObject object = isApiService.addDevice(token.getAccessToken(), map.get("deviceSerial"), map.get("validateCode"));
        if (object.getString("code").equals("200")){
            object = isApiService.getDeviceSingle(token.getAccessToken(), map.get("deviceSerial"));
            DeviceInfo deviceInfo = Obj2DeviceInfo(object, map.get("userId"));
            deviceInfoService.save(deviceInfo);
            return R.ok();
        }else {
            return R.error(ResultCodeEnum.EXCEPTION_ERROR).message(object.getString("msg"));
        }
    }

    @PostMapping("/device/edit")
    public R editDevice(@RequestBody Map<String, String> map){
        DeviceInfo deviceInfo = new DeviceInfo();
        deviceInfo.setName(map.get("deviceName"));
        boolean isSuccess = deviceInfoService.update(deviceInfo, Wrappers.<DeviceInfo>lambdaUpdate().eq(DeviceInfo::getSerial, map.get("deviceSerial")));
        return isSuccess ? R.ok() : R.error();
    }

    @PostMapping("/device/delete")
    public R deleteDevice(@RequestBody Map<String, String> map){
        JSONObject object = isApiService.deleteDevice(token.getAccessToken(), map.get("deviceSerial"));
        if (object.getString("code").equals("200")){
            Map<String,Object> columnMap = new HashMap<>();
            columnMap.put("Serial", map.get("deviceSerial"));
            deviceInfoService.removeByMap(columnMap);
            return R.ok();
        }else {
            return R.error(ResultCodeEnum.EXCEPTION_ERROR).message(object.getString("msg"));
        }
    }

    @PostMapping("/device/inspect")
    public R InspectDevice(@RequestBody Map<String, String> map){
        List<String> serials = new ArrayList<>();
        serials.add(map.get("deviceSerial"));
        inspection.deviceListInspection(serials);
        return R.ok();
    }

    @GetMapping("/token/get")
    public R getAccessToken() {
        String accessToken = token.getAccessToken();
        return R.ok().data("accessToken", accessToken);
    }

    private DeviceInfo Obj2DeviceInfo(JSONObject object, String userId){
        DeviceInfo deviceInfo = new DeviceInfo();
        deviceInfo.setId(uuid.getUuid());
        deviceInfo.setSerial(object.getJSONObject("data").getString("deviceSerial"));
        deviceInfo.setName(object.getJSONObject("data").getString("deviceName"));
        deviceInfo.setStatus(object.getJSONObject("data").getInteger("status"));
        deviceInfo.setUserId(userId);
        return deviceInfo;
    }
}
