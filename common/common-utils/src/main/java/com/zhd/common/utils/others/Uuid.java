package com.zhd.common.utils.others;

import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class Uuid {

    public String getUuid() {
        return UUID.randomUUID().toString().replace("-", "");
    }

}
