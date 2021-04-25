package com.zhd.common.utils.result;

import lombok.Getter;

@Getter
public enum ResultCodeEnum {
    /**
     * 2开头都为成功
     * 其他都为失败
     */
    // 执行成功
    SUCCESS(true, 20000, "成功"),

    // 数据库相关
    // 数据库插入失败
    DB_INSERT_ERROR(false,33001, "数据库插入失败"),
    // 数据库更新失败
    DB_UPDATE_ERROR(false, 33002, "数据库更新失败"),

    // 传入参数错误
    PARAM_ERROR(false, 40001, "传入参数错误"),

    // 缓存相关
    LINKAGE_ALREADY_EXIST(false, 50001, "联动规则已存在"),

    // 异常错误
    EXCEPTION_ERROR(false, 60000, "出现异常");

    private Boolean success;

    private Integer code;

    private String message;

    ResultCodeEnum(Boolean success, Integer code, String message) {
        this.success = success;
        this.code = code;
        this.message = message;
    }
}
