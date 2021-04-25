package com.zhd.device.management.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 
 * </p>
 *
 * @author zhd
 * @since 2021-04-16
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="LinkageResult对象", description="")
public class LinkageResult implements Serializable {

    private static final long serialVersionUID=1L;

      @TableId(value = "id", type = IdType.ASSIGN_ID)
    private String id;

    @ApiModelProperty(value = "触发源设备")
    private String triggerDevice;

    @ApiModelProperty(value = "触发源事件")
    private Integer triggerEvent;

    @ApiModelProperty(value = "响应方设备")
    private String actionDevice;

    @ApiModelProperty(value = "响应方事件")
    private Integer actionEvent;

    @ApiModelProperty(value = "结果类型，1-抓图")
    private Integer resultType;

    @ApiModelProperty(value = "内容，根据结果类型展示")
    private String resultContent;

    @ApiModelProperty(value = "发生时间")
    private Date createTime;


}
