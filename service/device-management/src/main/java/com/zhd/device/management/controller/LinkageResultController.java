package com.zhd.device.management.controller;


import com.zhd.common.utils.result.R;
import com.zhd.device.management.entity.LinkageResult;
import com.zhd.device.management.service.LinkageResultService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author zhd
 * @since 2021-04-16
 */
@RestController
@RequestMapping("/management/linkage-result")
public class LinkageResultController {

    @Autowired
    LinkageResultService linkageResultService;

    @GetMapping("/result/get")
    public R getLinkageResult() {
        List<LinkageResult> list = linkageResultService.list();
        return R.ok().data("list", list);
    }
}

