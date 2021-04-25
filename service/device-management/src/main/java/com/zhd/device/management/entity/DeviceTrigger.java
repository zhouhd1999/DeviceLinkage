package com.zhd.device.management.entity;

import com.baomidou.mybatisplus.annotation.IdType;
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
 * @since 2021-04-13
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="DeviceTrigger对象", description="")
public class DeviceTrigger implements Serializable {

    private static final long serialVersionUID=1L;

    @ApiModelProperty(value = "32位uuid")
      @TableId(value = "id", type = IdType.ASSIGN_ID)
    private String id;

    @ApiModelProperty(value = "设备序列号")
    private String serial;

    @ApiModelProperty(value = "设备支持的触发源")
    private Integer triggerSource;


}
