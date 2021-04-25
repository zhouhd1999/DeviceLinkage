package com.zhd.device.management.component;

import com.zhd.device.management.entity.LinkageRule;
import com.zhd.device.management.service.LinkageRuleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.List;

@Component
public class Initialize {

    @Autowired
    LinkageRuleService linkageRuleService;

    @Autowired
    LinkageCache linkageCache;

    @PostConstruct
    public void init(){
        System.out.println("loading linkage rule");
        List<LinkageRule> linkageRules = linkageRuleService.list();
        for(LinkageRule rule : linkageRules){
            linkageCache.cacheLinkageAdd(rule);
        }
    }

    @PreDestroy
    public void destroy(){
        System.out.println("process end");
    }
}
