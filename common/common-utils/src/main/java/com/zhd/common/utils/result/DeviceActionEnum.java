package com.zhd.common.utils.result;

import lombok.Getter;

@Getter
public enum DeviceActionEnum {

    //抓图
    SUPPORT_CAPTURE(10000),

    //报警声音
    ALARM_VOICE(10001);

    private Integer code;

    DeviceActionEnum(Integer code) {
        this.code = code;
    }
}
