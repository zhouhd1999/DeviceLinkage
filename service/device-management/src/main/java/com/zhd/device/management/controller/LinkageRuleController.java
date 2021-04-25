package com.zhd.device.management.controller;


import com.zhd.common.utils.others.Uuid;
import com.zhd.common.utils.result.R;
import com.zhd.common.utils.result.ResultCodeEnum;
import com.zhd.device.management.component.LinkageCache;
import com.zhd.device.management.entity.LinkageRule;
import com.zhd.device.management.service.LinkageRuleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;


/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author zhd
 * @since 2021-04-14
 */
@RestController
@RequestMapping("/management/linkage-rule")
public class LinkageRuleController {

    @Autowired
    LinkageRuleService linkageRuleService;

    @Autowired
    LinkageCache linkageCache;

    @Autowired
    Uuid uuid;

    @GetMapping("/linkage/list")
    public R getLinkageList() {
        // get rule lists from cache
        List<LinkageRule> list = linkageCache.cacheLinkageListGet();
        return R.ok().data("list", list);
    }

    @PostMapping("/linkage/add")
    public R addLinkage(@RequestBody LinkageRule rule) {
        // check if the rule already exists in the cache
        if (!linkageCache.cacheLinkageCheck(rule, false)){
            return R.error(ResultCodeEnum.LINKAGE_ALREADY_EXIST);
        }

        rule.setId(uuid.getUuid());
        // add rule to cache
        linkageCache.cacheLinkageAdd(rule);
        // add rule to DB
        linkageRuleService.save(rule);
        return R.ok();
    }

    @PostMapping("/linkage/delete")
    public R deleteLinkage(@RequestBody Map<String, String> map){
        String id = map.get("id");
        // delete rule from cache
        linkageCache.cacheLinkageDelete(id);
        // delete rule from DB
        linkageRuleService.removeById(id);
        return R.ok();
    }

    @PostMapping("/linkage/edit")
    public R editLinkage(@RequestBody LinkageRule rule){
        //check if the rule already exists in the cache
        linkageCache.cacheLinkageCheck(rule, true);
        // update rule from the cache
        linkageCache.cacheLinkageUpdate(rule);
        // update rule from the DB
        linkageRuleService.updateById(rule);
        return R.ok();
    }
}
