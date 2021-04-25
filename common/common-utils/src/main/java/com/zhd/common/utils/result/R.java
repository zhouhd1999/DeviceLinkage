package com.zhd.common.utils.result;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.HashMap;
import java.util.Map;

@Data
public class R {

    @ApiModelProperty(value = "是否成功")
    private Boolean success;
    @ApiModelProperty(value = "返回码")
    private Integer code;
    @ApiModelProperty(value = "返回消息")
    private String message;
    @ApiModelProperty(value = "返回数据")
    private Map<String, Object> data = new HashMap<>();

    public R(){}
    public static R ok() {
        R r = new R();
        r.setCode(ResultCodeEnum.SUCCESS.getCode());
        r.setSuccess(ResultCodeEnum.SUCCESS.getSuccess());
        r.setMessage(ResultCodeEnum.SUCCESS.getMessage());
        return r;
    }
    public static R error() {
        R r = new R();
        r.setSuccess(ResultCodeEnum.EXCEPTION_ERROR.getSuccess());
        r.setSuccess(ResultCodeEnum.EXCEPTION_ERROR.getSuccess());
        r.setMessage(ResultCodeEnum.EXCEPTION_ERROR.getMessage());
        return r;
    }
    public static R error(ResultCodeEnum resultCodeEnum) {
        R r = new R();
        r.setSuccess(resultCodeEnum.getSuccess());
        r.setMessage(resultCodeEnum.getMessage());
        r.setCode(resultCodeEnum.getCode());
        return r;
    }

    public static R setResult(ResultCodeEnum resultCodeEnum) {
        R r = new R();
        r.setSuccess(resultCodeEnum.getSuccess());
        r.setMessage(resultCodeEnum.getMessage());
        r.setCode(resultCodeEnum.getCode());
        return r;
    }



    public R data(String key, Object value) {
        this.data.put(key, value);
        return this;
    }
    public R message(String message) {
        this.message = message;
        return this;
    }

}
