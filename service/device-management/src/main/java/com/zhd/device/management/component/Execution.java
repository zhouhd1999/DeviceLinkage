package com.zhd.device.management.component;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.zhd.common.utils.others.Uuid;
import com.zhd.device.management.entity.DeviceAction;
import com.zhd.device.management.entity.LinkageResult;
import com.zhd.device.management.entity.LinkageRule;
import com.zhd.device.management.service.IsApiService;
import com.zhd.device.management.service.LinkageResultService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Component
public class Execution {

    @Autowired
    LinkageCache cache;

    @Autowired
    IsApiService isApiService;

    @Autowired
    Token token;

    @Autowired
    LinkageResultService linkageResultService;

    @Autowired
    Uuid uuid;

    public void doing(JSONArray array) {
        List<LinkageResult> list = parse(array);
        for (LinkageResult linkageResult : list) {
            execute(matchRule(linkageResult), linkageResult);
            System.out.println("即将插入的数据：" + linkageResult);
            linkageResultService.save(linkageResult);
        }
    }

    private void execute(List<DeviceAction> actions, LinkageResult linkageResult){
        for (DeviceAction action : actions) {
            if (action.getResponseAction() == 10000){
                JSONObject object = isApiService.deviceCapture(token.getAccessToken(), action.getSerial(), 1);
                if (object.getString("code").equals("200")) {
                    linkageResult.setActionDevice(action.getSerial());
                    linkageResult.setActionEvent(action.getResponseAction());
                    linkageResult.setResultType(1);
                    linkageResult.setResultContent(object.getJSONObject("data").getString("picUrl"));
                    linkageResult.setId(uuid.getUuid());
                }
            }
        }
    }

    private List<DeviceAction> matchRule(LinkageResult linkageResult) {
        List<LinkageRule> ruleList = cache.cacheLinkageListGet();
        List<DeviceAction> actions = new ArrayList<>();
        DeviceAction action = new DeviceAction();
        for (LinkageRule rule : ruleList) {
            if (rule.getTriggerDevice().equals(linkageResult.getTriggerDevice()) && rule.getTriggerEvent().equals(linkageResult.getTriggerEvent())){
                action.setSerial(rule.getActionDevice());
                action.setResponseAction(rule.getActionEvent());
                actions.add(action);
            }
        }
        return actions;
    }

    private List<LinkageResult> parse(JSONArray array) {
        List<LinkageResult> list = new ArrayList<>();
        for (int i = 0; i < array.size(); i++) {
            LinkageResult linkageResult = new LinkageResult();
            linkageResult.setTriggerDevice(array.getJSONObject(i).getString("deviceSerial"));
            linkageResult.setTriggerEvent(array.getJSONObject(i).getInteger("alarmType"));
            linkageResult.setCreateTime(new Date(array.getJSONObject(i).getLong("alarmTime")));
            list.add(linkageResult);
        }
        return list;
    }
}
