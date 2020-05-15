package com.yuntongxun.mwork.vo.rsp;

import com.yuntongxun.mwork.vo.support.BaseRsp;
import lombok.Data;

/**
 * @program: mwork
 * @description: login rsp
 * @author: liugang
 * @create: 2020-05-12 16:25
 **/
@Data
public class LoginRsp extends BaseRsp {

    private Long user_id;

    private String phoneNum;

    private Integer userStatus;

    private String countryCode;

    private String appVersion;

    private String completeCode;

}
