package com.zhd.common.utils.result;

import lombok.Getter;

@Getter
public enum DeviceTriggerEnum {

    //移动侦测
    MOVE_DETECT(10002),

    //报警声音
    OFFLINE_NOTIFY(10010);

    private Integer code;

    DeviceTriggerEnum(Integer code) {
        this.code = code;
    }
}
