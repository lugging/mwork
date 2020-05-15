package com.yuntongxun.mwork.vo.rsp;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.yuntongxun.mwork.vo.support.BaseRsp;
import lombok.Data;

/**
 * @program: mwork
 * @description: info rsp
 * @author: liugang
 * @create: 2020-05-13 16:23
 **/
@Data
public class InfoRsp extends BaseRsp {

    private Integer userId;

    private String phoneNum;

    private Integer userStatus;

    private String countryCode;

    private String appVersion;

    private String completeCode;

}
