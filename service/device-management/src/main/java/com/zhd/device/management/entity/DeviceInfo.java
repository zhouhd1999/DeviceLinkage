package com.zhd.device.management.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="DeviceInfo对象", description="")
public class DeviceInfo implements Serializable {

    private static final long serialVersionUID=1L;

    @ApiModelProperty(value = "32位uuid")
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private String id;

    @ApiModelProperty(value = "设备序列号")
    private String serial;

    @ApiModelProperty(value = "设备名称")
    private String name;

    @ApiModelProperty(value = "是否在线 0-离线，1-在线")
    private Integer status;

    @ApiModelProperty(value = "设备所属用户ID")
    private String userId;

}
