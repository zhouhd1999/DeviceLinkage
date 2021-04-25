package com.zhd.device.management.component;

import com.alibaba.fastjson.JSONObject;
import com.zhd.device.management.entity.LinkageRule;
import com.zhd.device.management.service.DeviceActionService;
import com.zhd.device.management.service.DeviceTriggerService;
import com.zhd.device.management.service.LinkageRuleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class LinkageCache {
    private Map<String, LinkageRule> linkageRuleMap = new HashMap<>();

    @Autowired
    DeviceActionService deviceActionService;

    @Autowired
    DeviceTriggerService deviceTriggerService;

    @Autowired
    LinkageRuleService linkageRuleService;

    public void cacheLinkageAdd(LinkageRule rule){
        linkageRuleMap.put(rule.getId(), rule);
    }

    public void cacheLinkageDelete(String id){
        linkageRuleMap.remove(id);
    }

    public void cacheLinkageUpdate(LinkageRule rule){
        linkageRuleMap.replace(rule.getId(), rule);
    }

    public List<LinkageRule> cacheLinkageListGet(){
        List<LinkageRule> list = new ArrayList<>();
        for (Map.Entry<String, LinkageRule> entry : linkageRuleMap.entrySet()){
            list.add(entry.getValue());
        }
        return list;
    }

    public boolean cacheLinkageCheck(LinkageRule rule, boolean isEdit){
        for(Map.Entry<String, LinkageRule> entry : linkageRuleMap.entrySet()){
            if (isEdit && rule.getId().equals(entry.getKey())){
                continue;
            }
            if (compareLinkage(rule, entry.getValue())){
                return false;
            }
        }
        return true;
    }

    private boolean compareLinkage(LinkageRule rule1, LinkageRule rule2){
        return rule1.getActionDevice().equals(rule2.getActionDevice()) && rule1.getActionEvent().equals(rule2.getActionEvent())
                && rule1.getTriggerDevice().equals(rule2.getTriggerDevice()) && rule1.getTriggerEvent().equals(rule2.getTriggerEvent());
    }
}
