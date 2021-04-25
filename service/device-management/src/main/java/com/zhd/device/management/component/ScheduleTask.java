package com.zhd.device.management.component;

import com.alibaba.fastjson.JSONObject;
import com.zhd.device.management.entity.DeviceInfo;
import com.zhd.device.management.service.DeviceInfoService;
import com.zhd.device.management.service.IsApiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@EnableScheduling
@EnableAsync
public class ScheduleTask {

    @Autowired
    DeviceInfoService deviceInfoService;

    @Autowired
    IsApiService isApiService;

    @Autowired
    Inspection inspection;

    @Autowired
    Token token;

    @Autowired
    Execution execution;

    private Long lastTime;

    @Async
    @Scheduled(fixedDelay = 1000 * 60 * 10)
    public void inspection() {
        // inspect all device every 10 minutes
        List<DeviceInfo> deviceInfos = deviceInfoService.list();
        List<String> serials = new ArrayList<>();
        for (DeviceInfo deviceInfo : deviceInfos) {
            serials.add(deviceInfo.getSerial());
        }

        inspection.deviceListInspection(serials);
    }

    @Async
    @Scheduled(fixedDelay = 5000)
    public void execution() {
        if (lastTime == null) {
            lastTime = System.currentTimeMillis();
            return;
        }
        Long nowTime = System.currentTimeMillis();
        JSONObject object = isApiService.deviceAlarm(token.getAccessToken(), lastTime, nowTime);
        if (!object.getJSONArray("data").isEmpty()) {
            // 执行联动规则
            System.out.println("execution" + object.getJSONArray("data"));
            execution.doing(object.getJSONArray("data"));
        }
        lastTime = nowTime;
    }
}
