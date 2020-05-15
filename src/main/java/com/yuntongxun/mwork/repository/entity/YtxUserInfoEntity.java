package com.yuntongxun.mwork.repository.entity;

import com.yuntongxun.mwork.repository.base.BaseEntity;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

/**
 * @author liugang
 * @since 2020-01-16
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("ytx_user_info")
public class YtxUserInfoEntity extends BaseEntity {

    private static final long serialVersionUID=1L;

    /**
     * 自增长ID
     */
    @TableId(value = "user_id", type = IdType.AUTO)
    private Long userId;

    @TableField("phone_num")
    private String phoneNum;

    @TableField("user_status")
    private Integer userStatus;

    @TableField("country_code")
    private String countryCode;

    @TableField("app_version")
    private String appVersion;

    @TableField("complete_code")
    private String completeCode;

    @TableField("device_type")
    private String deviceType;

    @TableField("device_agent")
    private String deviceAgent;

    @TableField("app_id")
    private String appId;

    @TableField("create_time")
    private LocalDateTime createTime;

    @TableField("update_time")
    private LocalDateTime updateTime;

    public static final String PHONE_NUM = "phone_num";

    public static final String USER_STATUS = "user_status";
}
