package com.zhd.device.management.service;

import com.alibaba.fastjson.JSONObject;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Service
@FeignClient(url = "https://open.ys7.com/api/lapp", name = "isApiServer")
public interface IsApiService {

    //获取设备列表及信息
    @RequestMapping(value = "/device/list", method = RequestMethod.POST, consumes = "application/x-www-form-urlencoded;charset=UTF-8")
    JSONObject getDeviceList(@RequestParam("accessToken") String accessToken);

    //获取单台设备信息
    @RequestMapping(value = "/device/info", method = RequestMethod.POST, consumes = "application/x-www-form-urlencoded;charset=UTF-8")
    JSONObject getDeviceSingle(@RequestParam("accessToken") String accessToken, @RequestParam("deviceSerial") String deviceSerial);

    //获取accessToken
    @RequestMapping(value = "/token/get", method = RequestMethod.POST, consumes = "application/x-www-form-urlencoded;charset=UTF-8")
    JSONObject getAccessToken(@RequestParam("appKey") String appKey, @RequestParam("appSecret") String appSecret);

    //添加设备
    @RequestMapping(value = "/device/add", method = RequestMethod.POST, consumes = "application/x-www-form-urlencoded;charset=UTF-8")
    JSONObject addDevice(@RequestParam("accessToken") String accessToken, @RequestParam("deviceSerial") String deviceSerial, @RequestParam("validateCode") String validateCode);

    //删除设备
    @RequestMapping(value = "/device/delete", method = RequestMethod.POST, consumes = "application/x-www-form-urlencoded;charset=UTF-8")
    JSONObject deleteDevice(@RequestParam("accessToken") String accessToken, @RequestParam("deviceSerial") String deviceSerial);

    //获取设备能力
    @RequestMapping(value = "/device/capacity", method = RequestMethod.POST, consumes = "application/x-www-form-urlencoded;charset=UTF-8")
    JSONObject inspectDevice(@RequestParam("accessToken") String accessToken, @RequestParam("deviceSerial") String deviceSerial);

    //获取告警
    @RequestMapping(value = "/alarm/list", method = RequestMethod.POST, consumes = "application/x-www-form-urlencoded;charset=UTF-8")
    JSONObject deviceAlarm(@RequestParam("accessToken") String accessToken, @RequestParam("startTime") Long startTime, @RequestParam("endTime") Long endTime);

    //抓图
    @RequestMapping(value = "/device/capture", method = RequestMethod.POST, consumes = "application/x-www-form-urlencoded;charset=UTF-8")
    JSONObject deviceCapture(@RequestParam("accessToken") String accessToken, @RequestParam("deviceSerial") String deviceSerial, @RequestParam("channelNo") Integer channelNo);

}
